/**
 * Copyright 2015 Freetong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package itaf.mobile.app.im.xmpp;

import java.io.File;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smackx.filetransfer.FileTransfer;
import org.jivesoftware.smackx.filetransfer.FileTransfer.Status;
import org.jivesoftware.smackx.filetransfer.FileTransferListener;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.filetransfer.FileTransferRequest;
import org.jivesoftware.smackx.filetransfer.IncomingFileTransfer;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

/**
 * @author zhanghaitao
 * @date 2011-7-7
 * @version 1.0
 */
public class XmppFileManager implements FileTransferListener {

	private XMPPConnection _connection;
	private FileTransferManager _fileTransferManager = null;
	// private String answerTo;
	private static File externalFileDir;
	private static File landingDir;

	private String TAG = "filetransfer";

	private static final String gtalksmsDir = "GTalkSMS";

	private static class SingletonHolder {
		static final XmppFileManager INSTANCE = new XmppFileManager();
	}

	public static XmppFileManager getInstance() {
		return SingletonHolder.INSTANCE;
	}

	private XmppFileManager() {

	}

	public void initialize(Context context, XMPPConnection connection) {

		// api level >=8
		externalFileDir = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
		landingDir = new File(externalFileDir, gtalksmsDir);

		Log.d("XmppFileManager", "-----dir:" + landingDir.getAbsolutePath());

		if (!landingDir.exists()) {
			landingDir.mkdirs();
		}

		_connection = connection;
		// important: you have to make a dummy service discovery manager.
		// new ServiceDiscoveryManager(ActivityLoginAndChat.connection);
		// now this line does not cause any problems.
		_fileTransferManager = new FileTransferManager(_connection);
		_fileTransferManager.addFileTransferListener(this);
	}

	public FileTransferManager getFileTransferManager() {
		return _fileTransferManager;
	}

	private void send(String msg) {
		Log.i(TAG, msg);
	}

	public void fileTransferRequest(FileTransferRequest request) {
		File saveTo;
		// set answerTo for replies and send()
		// answerTo = request.getRequestor();
		request.getRequestor();
		if (!Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			send("External Media not mounted read/write");
			return;
		} else if (!landingDir.isDirectory()) {
			send("The directory " + landingDir.getAbsolutePath()
					+ " is not a directory");
			return;
		}
		saveTo = new File(landingDir, request.getFileName());
		if (saveTo.exists()) {
			send("The file " + saveTo.getAbsolutePath() + " already exists");
			// delete
			saveTo.delete();
			// return;
		}
		IncomingFileTransfer transfer = request.accept();
		send("File transfer: " + saveTo.getName() + " - "
				+ request.getFileSize() / 1024 + " KB");
		try {
			transfer.recieveFile(saveTo);
			send("File transfer: " + saveTo.getName() + " - "
					+ transfer.getStatus());
			double percents = 0.0;
			while (!transfer.isDone()) {
				if (transfer.getStatus().equals(Status.in_progress)) {
					percents = ((int) (transfer.getProgress() * 10000)) / 100.0;
					send("File transfer: " + saveTo.getName() + " - "
							+ percents + "%");
				} else if (transfer.getStatus().equals(Status.error)) {
					send(returnAndLogError(transfer));
					return;
				}
				Thread.sleep(1000);
			}
			if (transfer.getStatus().equals(Status.complete)) {
				send("File transfer complete. File saved as "
						+ saveTo.getAbsolutePath());
			} else {
				send(returnAndLogError(transfer));
			}
		} catch (Exception ex) {
			String message = "Cannot receive the file because an error occured during the process."
					+ ex;
			Log.e(TAG, message, ex);
			send(message);
		}

	}

	public File getLandingDir() {
		return landingDir;
	}

	public String returnAndLogError(FileTransfer transfer) {
		String message = "Cannot process the file because an error occured during the process.";

		if (transfer.getError() != null) {
			message += transfer.getError();
		}
		if (transfer.getException() != null) {
			message += transfer.getException();
		}
		return message;
	}
}
