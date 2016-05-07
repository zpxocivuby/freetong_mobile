package itaf.mobile.core.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Session用户（系统唯一存在）
 * 
 * @author
 * 
 * @update 2013年10月30日
 */
public class SessionUser implements Serializable {

	private static final long serialVersionUID = 5352711535417259562L;
	// 匿名用户
	public static final Long TYPE_ANONYMOUS = -1L;
	// 消费者用户
	public static final Long TYPE_CONSUMER = 10L;
	// 商户用户
	public static final Long TYPE_MERCHANT = 20L;
	// 配送商用户
	public static final Long TYPE_DIST = 30L;
	// 商户用户和配送商用户
	public static final Long TYPE_MERCHANT_DIST = 2030L;
	// 未认证
	public static final Long STATUS_NONE = -1L;
	// 认证中
	public static final Long STATUS_CERTIFICATING = 1L;
	// 认证成功
	public static final Long STATUS_CERTIFICATED = 2L;
	// 认证失败
	public static final Long STATUS_FAILED = -2L;

	// 用户ID
	private Long id = -1L;
	// 用户名
	private String username;
	// 密码
	private String password;
	// -1，匿名用户， 10，消费者用户， 20，商户用户，30，配送商用户， 2030，商户用户和配送商用户
	private Long type = -1L;
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
	// 手机号码
	private String mobile;
	// email地址
	private String email;
	// 状态
	private String imStatus = "离线";
	// 是否选中
	private Boolean checked;
	// 账号余额
	private BigDecimal accountBalance;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Long getRealnameStatus() {
		return realnameStatus;
	}

	public void setRealnameStatus(Long realnameStatus) {
		this.realnameStatus = realnameStatus;
	}

	public Long getSellingStatus() {
		return sellingStatus;
	}

	public void setSellingStatus(Long sellingStatus) {
		this.sellingStatus = sellingStatus;
	}

	public Long getDistStatus() {
		return distStatus;
	}

	public void setDistStatus(Long distStatus) {
		this.distStatus = distStatus;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getImStatus() {
		return imStatus;
	}

	public void setImStatus(String imStatus) {
		this.imStatus = imStatus;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public BigDecimal getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}

}
