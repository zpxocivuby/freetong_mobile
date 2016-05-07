package itaf.mobile.ds.domain;

/**
 * 系统的内部版本控制
 * 
 * 
 * @author
 * 
 * @updateDate 2014年3月6日
 */
public class AppVersion {

	public static final String TYPE_FIRST_LOGIN = "FIRST_LOGIN";

	private String type;

	private String status;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
