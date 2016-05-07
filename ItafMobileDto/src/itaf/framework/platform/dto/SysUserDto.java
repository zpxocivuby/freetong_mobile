package itaf.framework.platform.dto;

import itaf.framework.base.dto.BaseDto;

import java.math.BigDecimal;

/**
 * 用户表
 * 
 * @author
 * 
 * @update 2013年8月27日
 */
public class SysUserDto extends BaseDto {

	private static final long serialVersionUID = -2806203926751967619L;

	// 匿名static final
	public static final Long TYPE_ANONYMOUS = -1L;
	// 消费static final
	public static final Long TYPE_CONSUMER = 10L;
	// 商户static final
	public static final Long TYPE_MERCHANT = 20L;
	// 配送static final
	public static final Long TYPE_DIST = 30L;
	// 商户static final 用户
	public static final Long TYPE_MERCHANT_DIST = 2030L;
	// 未认static final
	public static final Long STATUS_NONE = -1L;
	// 认证static final
	public static final Long STATUS_CERTIFICATING = 1L;
	// 认证static final
	public static final Long STATUS_CERTIFICATED = 2L;
	// 认证static final
	public static final Long STATUS_FAILED = -2L;
	// 用户名
	private String username;
	// 密码
	private String password;
	// -1，匿名用户， 10，消费者用户， 20，商户用户，30，配送商用户， 2030，商户用户和配送商用户
	private Long type = -1L;
	// 头像文件名
	private String headIco;
	// -1，未认证，1，实名认证中用户， 2，实名认证用户，-2，实名认证失败用户，
	private Long realnameStatus = -1L;
	// -1，未认证， 1，商户认证中用户，2，商户认证用户，-2，商户认证失败用户，
	private Long sellingStatus = -1L;
	// -1，未认证， 1，配送商认证中用户，2，配送商认证用户，-2，配送商认证失败用户，
	private Long distStatus = -1L;
	// 真实名称
	private String realname;
	// 昵称
	private String nickname;
	// email地址
	private String email;
	// 手机号码
	private String mobile;
	// 余额
	private BigDecimal accountBalance;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public String getHeadIco() {
		return headIco;
	}

	public void setHeadIco(String headIco) {
		this.headIco = headIco;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getRealnameStatus() {
		return realnameStatus;
	}

	public void setRealnameStatus(Long applyRealnameStatus) {
		this.realnameStatus = applyRealnameStatus;
	}

	public Long getSellingStatus() {
		return sellingStatus;
	}

	public void setSellingStatus(Long applySellingStatus) {
		this.sellingStatus = applySellingStatus;
	}

	public Long getDistStatus() {
		return distStatus;
	}

	public void setDistStatus(Long applyDistStatus) {
		this.distStatus = applyDistStatus;
	}

	public BigDecimal getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}

}