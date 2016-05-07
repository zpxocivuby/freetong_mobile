package itaf.mobile.app.util;

import itaf.mobile.app.R;
import itaf.mobile.app.bean.CustomMultipartEntity;
import itaf.mobile.app.bean.CustomMultipartEntity.ProgressListener;

import java.io.File;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 文件上传，带进度条的文件上传
 * 
 * 
 * @author
 * 
 * @update 2013年11月12日
 */
public class UploadHelper extends AsyncTask<String, Integer, String> {

	private Activity mContext;
	private String servletPath;
	private String filePath;
	private long totalSize;
	private Map<String, String> paramsMap;

	public static final Long MAX_UPLOAD_SIZ = 50 * 1024 * 1024L;

	// 进度条
	private ProgressBar mProgress;
	// 显示上传数值
	private TextView mProgressText;
	// 下载对话框
	private Dialog uploadDialog;
	// 上传文件总大小
	private String uploadTotalSize;
	// 已上传文件大小
	private String uploadSize;

	private CallbackListener callbackListener;

	public void setCallbackListener(CallbackListener callbackListener) {
		this.callbackListener = callbackListener;
	}

	public UploadHelper(Activity context, String servletPath, String filePath,
			Map<String, String> paramsMap) {
		this.mContext = context;
		this.servletPath = servletPath;
		this.filePath = filePath;
		this.paramsMap = paramsMap;
	}

	@Override
	protected void onPreExecute() {
		showDownloadDialog();
	}

	private void showDownloadDialog() {
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle("正在上传");
		LinearLayout parentView = new LinearLayout(mContext);
		final LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.app_progress, parentView);
		mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
		mProgressText = (TextView) v.findViewById(R.id.update_progress_text);
		builder.setView(v);
		uploadDialog = builder.create();
		uploadDialog.setCanceledOnTouchOutside(false);
		uploadDialog.show();
	}

	@Override
	protected String doInBackground(String... params) {
		String result = "error";
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(
				CoreProtocolPNames.HTTP_CONTENT_CHARSET,
				Charset.forName("UTF-8"));
		HttpContext httpContext = new BasicHttpContext();
		HttpPost httpPost = new HttpPost(servletPath);
		// 显示文件大小格式：2个小数点显示
		final DecimalFormat df = new DecimalFormat("0.00");
		try {
			CustomMultipartEntity multipartEntity = new CustomMultipartEntity(
					new ProgressListener() {
						public void transferred(long num) {
							publishProgress((int) ((num / (float) totalSize) * 100));
							// 进度条下面显示的当前下载文件大小
							uploadSize = df.format((float) num / 1024 / 1024)
									+ "MB";
						}
					});

			multipartEntity.addPart("attachMentFiles", new FileBody(new File(
					filePath)));
			if (paramsMap != null && paramsMap.size() > 0) {
				for (Entry<String, String> param : paramsMap.entrySet()) {
					StringBody comment = new StringBody(param.getValue(),
							"application/octet-stream",
							Charset.defaultCharset());
					multipartEntity.addPart(param.getKey(), comment);
				}
			}
			totalSize = multipartEntity.getContentLength();
			// 进度条下面显示的总文件大小
			uploadTotalSize = df.format((float) totalSize / 1024 / 1024) + "MB";
			// Send it
			httpPost.setEntity(multipartEntity);
			HttpResponse response = httpClient.execute(httpPost, httpContext);
			result = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			result = "error";
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return result;
	}

	@Override
	protected void onProgressUpdate(Integer... progress) {
		mProgress.setProgress((int) (progress[0]));
		mProgressText.setText(uploadSize + "/" + uploadTotalSize);
	}

	@Override
	protected void onPostExecute(String result) {
		uploadDialog.dismiss();
		if ("error".equals(result)) {
			ToastHelper.showToast(mContext, "操作失败", Toast.LENGTH_LONG);
		} else {
			callbackListener.callback(result);
		}
	}

	@Override
	protected void onCancelled() {
		System.out.println("cancle");
	}

	public static interface CallbackListener {
		void callback(String dataId);
	}

}
