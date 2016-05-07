package itaf.mobile.core.constant;

/**
 * 系统常量
 * 
 * @author
 * 
 * @update 2013年10月11日
 */
public class AppConstants {

	// requestCode 打开Android系统的相册
	public static final int RC_ANDROID_PHOTO = 99;
	// 下载图片尺寸：原图
	public static final int IMAGE_SIZE = 0;
	// 下载图片尺寸：64x64
	public static final int IMAGE_SIZE_32X32 = 32;
	// 下载图片尺寸：64x64
	public static final int IMAGE_SIZE_64X64 = 64;
	// 下载图片尺寸：96x96
	public static final int IMAGE_SIZE_96X96 = 96;

	/** 操作状态 */

	/** (操作成功) */
	public static final String SUCCESS = "success";
	/** (操作失败) */
	public static final String ERROR = "error";
	/** (选中) */
	public static final Long CHECKED = 1L;
	/** (未选中) */
	public static final Long UNCHECKED = -1L;
	/** (开关状态为ON) */
	public static final String SET_ON = "on";
	/** (开关状态为OFF) */
	public static final String SET_OFF = "off";

	/** (同步开关) */
	public static final String SYNC_ON_OFF = "SYNC_ON_OFF";
	/** (仅在WIFI下传下载) */
	public static final String SYNC_WIFI_ON_OFF = "SYNC_WIFI_ON_OFF";

	/** (好友Im名称) */
	public static final String PARAM_IM_IMUSERNAME = "FRIEND_IMUSERNAME";
	/** (聊天室名称) */
	public static final String PARAM_IM_ROOMJID = "PARAM_IM_ROOMJID";
	/** (邀请者) */
	public static final String PARAM_IM_INVITER = "PARAM_IM_INVITER";
	/** (理由) */
	public static final String PARAM_IM_REASON = "PARAM_IM_REASON";

}
