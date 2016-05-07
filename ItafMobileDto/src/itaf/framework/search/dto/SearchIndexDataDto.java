package itaf.framework.search.dto;

import itaf.framework.base.dto.BaseDto;

import java.util.Date;

/**
 * 
 * 
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class SearchIndexDataDto extends BaseDto {


	/**
	 * 
	 */
	private static final long serialVersionUID = 2028123630848850178L;
	// ID
	// 标题
	private String title;
	// 内容
	private String text;
	// URL
	private String url;
	// 发布时间
	private Date releaseTime;
	// 索引时间
	private Date indexTime;
	// 摘要
	private String summary;
	// 索引类型
	private String indexType;
	// 状态
	private Long status;

	public SearchIndexDataDto() {
		//
	}


	public String getTitle() {
		return title;
	}

	public Date getReleaseTime() {
		return releaseTime;
	}

	public String getSummary() {
		return summary;
	}

	public String getText() {
		return text;
	}

	public String getUrl() {
		return url;
	}

	public Long getStatus() {
		return status;
	}

	public Date getIndexTime() {
		return indexTime;
	}

	public String getIndexType() {
		return indexType;
	}


	public void setTitle(String title) {
		this.title = title;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}

	public void setIndexTime(Date indexTime) {
		this.indexTime = indexTime;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public void setIndexType(String indexType) {
		this.indexType = indexType;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

}
