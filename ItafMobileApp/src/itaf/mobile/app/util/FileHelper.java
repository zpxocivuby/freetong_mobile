package itaf.mobile.app.util;

import itaf.mobile.core.app.AppApplication;
import itaf.mobile.core.utils.DateUtil;
import itaf.mobile.core.utils.StringHelper;

import java.io.File;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;

/**
 * 文件操作帮助类
 * 
 * @author
 * 
 * @update 2013年11月6日
 */
public class FileHelper {

	private static Set<String> supportFileExts;
	// 视频格式
	private static Set<String> supportVideoExts;
	// 图片格式
	private static Set<String> supportImageExts;
	// 音频格式
	private static Set<String> supportAudioExts;

	/**
	 * 存储长时间保存的数据
	 * 
	 * @param type
	 *            存储文件的类型会加载路径上
	 * @return 存储路径
	 */
	public static String getAppFileDir(Context mContext, String type) {
		File result = null;
		if (hasSdCard()) {
			result = mContext.getExternalFilesDir(type);
		} else {
			result = mContext.getFilesDir();
		}
		if (!result.exists()) {
			result.mkdirs();
		}
		return result.getPath() + "/";
	}

	/**
	 * 存储临时缓存数据
	 * 
	 * @return 存储路径
	 */
	public static String getAppCacheDir(Context mContext) {
		File result = null;
		if (hasSdCard()) {
			result = mContext.getExternalCacheDir();
		} else {
			result = mContext.getCacheDir();
		}
		if (!result.exists()) {
			result.mkdirs();
		}
		return result.getPath() + "/";
	}

	/**
	 * 是否有SdCard
	 * 
	 * @return true：有， false：否
	 */
	public static boolean hasSdCard() {
		return Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState());
	}

	public static String getSrcFileName(String path) {
		if (StringHelper.isEmpty(path)) {
			return "";
		}
		return path.substring(path.lastIndexOf("/") + 1);
	}

	public static void deleteByPath(String path) {
		if (StringHelper.isEmpty(path)) {
			return;
		}
		delete(new File(path));
	}

	public static boolean delete(File file) {
		if (file == null || !file.exists()) {
			return false;
		}
		if (file.isFile()) {
			file.delete();
			return true;
		}
		if (file.isDirectory()) {
			File[] childFiles = file.listFiles();
			if (childFiles == null || childFiles.length == 0) {
				file.delete();
				return true;
			}
			for (int i = 0; i < childFiles.length; i++) {
				delete(childFiles[i]);
			}
			file.delete();
		}
		return true;
	}

	// 生成下载文件目录
	public static String generateDownloadPath(Context mContext) {
		StringBuilder result = new StringBuilder();
		result.append(getAppCacheDir(mContext));
		result.append(File.separator);
		result.append(AppApplication.getInstance().getSessionUser()
				.getUsername());
		result.append(File.separator);
		result.append(DateUtil.formatDate(new Date(),
				DateUtil.FORMAT_DATE_YYYYMMDD));
		result.append(File.separator);
		return result.toString();
	}

	@SuppressLint("DefaultLocale")
	public static String processFileExt(String filename) {
		if (StringHelper.isEmpty(filename)) {
			return "";
		}
		int pos = filename.lastIndexOf(".");
		if (pos == -1) {
			return "";
		}
		return filename.substring(pos + 1).toLowerCase();
	}

	public static String processSrcFileName(String filename) {
		if (StringHelper.isEmpty(filename)) {
			return "";
		}
		int pos = filename.lastIndexOf(".");
		if (pos == -1) {
			return "";
		}
		return filename.substring(0, pos);
	}

	public static String getMimeType(String fileExt) {
		if (getSupportVideoExts().contains(fileExt)) {
			return "video/" + fileExt;
		}
		if (getSupportImageExts().contains(fileExt)) {
			return "image/" + fileExt;
		}
		if (getSupportAudioExts().contains(fileExt)) {
			return "audio/" + fileExt;
		}
		return "*/*";
	}

	public static Set<String> getSupportVideoExts() {
		if (supportVideoExts == null) {
			supportVideoExts = new HashSet<String>();
			supportVideoExts.add("avi");
			supportVideoExts.add("rm");
			supportVideoExts.add("rmvb");
			supportVideoExts.add("mp4");
			supportVideoExts.add("mkv");
			supportVideoExts.add("mov");
			supportVideoExts.add("asf");
			supportVideoExts.add("wmv");
			supportVideoExts.add("flv");
			supportVideoExts.add("3gp");
		}
		return supportVideoExts;
	}

	public static Set<String> getSupportImageExts() {
		if (supportImageExts == null) {
			supportImageExts = new HashSet<String>();
			supportImageExts.add("jpg");
			supportImageExts.add("jpeg");
			supportImageExts.add("gif");
			supportImageExts.add("png");
			supportImageExts.add("bmp");
			supportImageExts.add("tiff");
			supportImageExts.add("psd");
			supportImageExts.add("swf");
			supportImageExts.add("svg");
		}
		return supportImageExts;
	}

	public static Set<String> getSupportAudioExts() {
		if (supportAudioExts == null) {
			supportAudioExts = new HashSet<String>();
			supportAudioExts.add("mp3");
			supportAudioExts.add("ape");
			supportAudioExts.add("flac");
			supportAudioExts.add("wav");
			supportAudioExts.add("wave");
			supportAudioExts.add("wma");
			supportAudioExts.add("aiff");
			supportAudioExts.add("mpeg");
			supportAudioExts.add("midi");
			supportAudioExts.add("ogg");

		}
		return supportAudioExts;
	}

	public static Set<String> getSupportFileExts() {
		if (supportFileExts == null) {
			supportFileExts = new HashSet<String>();
			supportFileExts.addAll(getSupportVideoExts());
			supportFileExts.addAll(getSupportImageExts());
			supportFileExts.addAll(getSupportAudioExts());
		}
		return supportFileExts;
	}

	public static boolean isSupportFileExt(String fileExt) {
		if (supportFileExts == null) {
			getSupportFileExts();
		}
		return supportFileExts.contains(fileExt);
	}

}
