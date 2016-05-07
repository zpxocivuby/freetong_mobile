package itaf.framework.im.dto;

import itaf.framework.base.dto.BaseDto;

/**
 * 
 *
 *
 * @author
 *
 * @UpdateDate 2014年9月4日
 */
public class OfUserDto extends BaseDto {


	/**
	 * 
	 */
	private static final long serialVersionUID = 8861574558797894130L;
	private String username;
	private String plainpassword;
	private String encryptedpassword;
	private String name;
	private String email;

	public OfUserDto() {
		//
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPlainpassword() {
		return plainpassword;
	}

	public void setPlainpassword(String plainpassword) {
		this.plainpassword = plainpassword;
	}

	public String getEncryptedpassword() {
		return encryptedpassword;
	}

	public void setEncryptedpassword(String encryptedpassword) {
		this.encryptedpassword = encryptedpassword;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
