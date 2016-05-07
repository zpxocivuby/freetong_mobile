package itaf.mobile.app.util;

import itaf.mobile.app.R;
import itaf.mobile.core.app.AppConfig;
import itaf.mobile.core.utils.StringHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DownLoadHelper {

	private Activity mContext;

	public static final int TYPE_PRODUCT = 1;
	public static final int TYPE_FILE = 2;

	private static final int DOWN_NOSDCARD = 0;
	private static final int DOWN_UPDATE = 1;
	private static final int DOWN_OVER = 2;
	private static final int DOWN_FILE_ERROR = 3;

	// 下载对话框
	private Dialog downloadDialog;
	// 下载对话框
	private Dialog openFileDialog;
	// 进度条
	private ProgressBar mProgress;
	// 进度值
	private int progress;
	// 显示下载数值
	private TextView mProgressText;
	// 通知对话框
	private Dialog noticeDialog;
	// 终止标记
	private boolean interceptFlag;
	// 下载线程
	private Thread downLoadThread;
	// 服务器URL
	private String serverUrl = "";
	// URL参数
	private Map<String, String> urlParams;
	// 下载包保存路径
	private String localSavePath = "";
	// 下载文件名
	private String fileName = "";
	// 下载文件名
	private String filePath = "";
	// 下载文件大小
	private String fileSize;
	// 已下载文件大小
	private String tmpFileSize;

	public static String getDownloadProductPath(Long fileId, int fileSize) {
		return AppConfig.downloadProductUrl + "?fileId=" + fileId
				+ "&fileSize=" + fileSize;
	}

	public static String getHeadIcoPath(String fileId, int fileSize) {
		return AppConfig.downloadHeadIcoUrl + "?fileId=" + fileId
				+ "&fileSize=" + fileSize;
	}

	public DownLoadHelper(Activity mContext, String serverUrl, String fileName,
			Map<String, String> urlParams) {
		this.mContext = mContext;
		this.serverUrl = serverUrl;
		this.fileName = fileName;
		this.urlParams = urlParams;
		this.localSavePath = FileHelper.generateDownloadPath(mContext);
	}

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DOWN_UPDATE:
				mProgress.setProgress(progress);
				mProgressText.setText(tmpFileSize + "/" + fileSize);
				break;
			case DOWN_OVER:
				downloadDialog.dismiss();
				showOpenFileDialog();
				break;
			case DOWN_NOSDCARD:
				downloadDialog.dismiss();
				ToastHelper.showToast(mContext, "无法下载文件，请检查SD卡是否挂载");
				break;
			case DOWN_FILE_ERROR:
				downloadDialog.dismiss();
				ToastHelper.showToast(mContext, "下载失败");
				break;
			}
		};
	};

	public void showOpenFileDialog() {
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle("打开文件");
		builder.setMessage("要打开文件:" + fileName + "吗？");
		builder.setPositiveButton("打开", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				try {
					Uri uri = Uri.fromFile(new File(localSavePath + fileName));
					String type = FileHelper.getMimeType(FileHelper
							.processFileExt(fileName));
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setDataAndType(uri, type);
					mContext.startActivity(intent);
				} catch (Exception e) {
					ToastHelper.showToast(mContext, "文件打开失败");
					Log.e(DownLoadHelper.this.getClass().getName(),
							e.getMessage());
				}
			}
		});
		builder.setNegativeButton("关闭", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		openFileDialog = builder.create();
		openFileDialog.show();
	}

	public void showNoticeDialog() {
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle("文件下载");
		builder.setMessage(fileName);
		builder.setPositiveButton("立即下载", new OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				showDownloadDialog();
			}
		});
		builder.setNegativeButton("以后再说", new OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		noticeDialog = builder.create();
		noticeDialog.show();
	}

	/**
	 * 显示下载对话框
	 */
	private void showDownloadDialog() {
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle("正在下载");
		LinearLayout parentView = new LinearLayout(mContext);
		final LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.app_progress, parentView);
		mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
		mProgressText = (TextView) v.findViewById(R.id.update_progress_text);

		builder.setView(v);
		builder.setNegativeButton("取消", new OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				interceptFlag = true;
			}
		});
		builder.setOnCancelListener(new OnCancelListener() {

			public void onCancel(DialogInterface dialog) {
				dialog.dismiss();
				interceptFlag = true;
			}
		});
		downloadDialog = builder.create();
		downloadDialog.setCanceledOnTouchOutside(false);
		downloadDialog.show();

		downloadFile();
	}

	public Map<String, String> getUrlParams() {
		return urlParams;
	}

	public void setUrlParams(Map<String, String> urlParams) {
		this.urlParams = urlParams;
	}

	private String processFileUrl() {
		if (this.urlParams == null || this.urlParams.size() <= 0) {
			return serverUrl;
		}

		for (String key : this.urlParams.keySet()) {
			if (serverUrl.indexOf("?") == -1) {
				serverUrl += "?";
			} else {
				serverUrl += "&";
			}
			try {
				serverUrl = serverUrl
						+ key
						+ "="
						+ java.net.URLEncoder.encode(this.urlParams.get(key),
								"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return serverUrl;
	}

	private void downloadFile() {
		downLoadThread = new Thread(mdownRunnable);
		downLoadThread.start();
	}

	private Runnable mdownRunnable = new Runnable() {

		public void run() {
			FileOutputStream fos = null;
			InputStream is = null;
			try {
				// 判断是否挂载了SD卡
				String storageState = Environment.getExternalStorageState();
				if (storageState.equals(Environment.MEDIA_MOUNTED)) {
					File file = new File(localSavePath);
					if (!file.exists()) {
						file.mkdirs();
					}
					filePath = localSavePath + fileName;
				}

				// 没有挂载SD卡，无法下载文件
				if (StringHelper.isEmpty(filePath)) {
					mHandler.sendEmptyMessage(DOWN_NOSDCARD);
					return;
				}

				URL url = new URL(processFileUrl());
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.connect();
				int fileLength = conn.getContentLength();
				if (fileLength <= 0) {
					mHandler.sendEmptyMessage(DOWN_FILE_ERROR);
					return;
				}

				// 显示文件大小格式：2个小数点显示
				DecimalFormat df = new DecimalFormat("0.00");
				// 进度条下面显示的总文件大小
				fileSize = df.format((float) fileLength / 1024 / 1024) + "MB";

				File downloadFile = new File(filePath);
				fos = new FileOutputStream(downloadFile);
				is = conn.getInputStream();
				int count = 0;
				byte buf[] = new byte[1024];

				do {
					int numread = is.read(buf);
					count += numread;
					// 进度条下面显示的当前下载文件大小
					tmpFileSize = df.format((float) count / 1024 / 1024) + "MB";
					// 当前进度值
					progress = (int) (((float) count / fileLength) * 100);
					// 更新进度
					mHandler.sendEmptyMessage(DOWN_UPDATE);
					if (numread <= 0) {
						// 下载完成 - 将临时下载文件转成APK文件
						mHandler.sendEmptyMessage(DOWN_OVER);
						break;
					}
					fos.write(buf, 0, numread);
				} while (!interceptFlag);// 点击取消就停止下载

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (fos != null) {
						fos.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (is != null) {
						try {
							is.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}

		}

	};

}
