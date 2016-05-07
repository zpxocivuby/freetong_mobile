package itaf.framework.im.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;

/**
 * 
 * IM聊天日志
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class BzChatLogDto extends BaseDto {


	/**
	 * 
	 */
	private static final long serialVersionUID = -5534111454901039064L;
	private String sessionJid;
	private String sender;
	private String receiver;
	private Long state;
	private String content;
	private String keywords;
	private String detail;

	public BzChatLogDto() {
	}

	public String getSessionJid() {
		return sessionJid;
	}

	public void setSessionJid(String sessionJid) {
		this.sessionJid = sessionJid;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public Long getState() {
		return state;
	}

	public void setState(Long state) {
		this.state = state;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}
