package itaf.mobile.core.app;

/**
 * 
 * 系统配置信息
 *
 * @author
 *
 * @UpdateDate 2014年9月10日
 */
public class AppConfig {

	// WS相关配置
	// public static String wsServerUrl =
	// "http://192.168.0.112:8080/itaf-web-side";
	public static String wsServerUrl = "http://192.168.244.1:8080/itaf-web-side";
	// 系统文件上传
	public static String uploadFileUrl = wsServerUrl
			+ "/wsServlet/UploadFileServlet";
	// 系统文件下载
	public static String downloadFileUrl = wsServerUrl
			+ "/wsServlet/DownloadFileServlet";
	// 系统文件上传
	public static String uploadHeadIcoUrl = wsServerUrl
			+ "/wsServlet/UploadHeadIcoServlet";
	// 系统文件下载
	public static String downloadHeadIcoUrl = wsServerUrl
			+ "/wsServlet/DownloadHeadIcoServlet";
	// 商品文件上传
	public static String uploadProductUrl = wsServerUrl
			+ "/wsServlet/UploadProductServlet";
	// 商品文件下载
	public static String downloadProductUrl = wsServerUrl
			+ "/wsServlet/DownloadProductServlet";
	// IM相关配置
	public static String imServerIp = "192.168.244.1";
	public static String imServerDomainName = "win-icnimh4vny3";
	public static String imServerName = "conference." + imServerDomainName;
	public static int imServerPort = 5223;

}
