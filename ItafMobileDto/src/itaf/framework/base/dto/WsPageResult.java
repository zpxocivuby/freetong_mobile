package itaf.framework.base.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * 
 * WS返回结果统一类
 *
 * @author
 *
 * @UpdateDate 2014年10月28日
 */
public class WsPageResult<T> implements Serializable {

	private static final long serialVersionUID = 6303037874842719163L;

	public static final String STATUS_SUCCESS = "1";
	public static final String STATUS_ERROR = "-1";

	// WS调用是否成功 1为成功 -1为失败
	private String status;
	// 错误信息（抛给用户的信息）
	private String errorMsg;
	// 状态代码
	private String statusCode;
	// 当前记录索引
	private int currentIndex = 0;
	// 每页显示的记录数，默认为20条记录 如果要显示全部记录数，可以设定每页记录数为 0
	private int pageSize = 20;
	// 总记录数
	private int totalCount = 0;
	// 当前页面
	private int currentPage = 1;
	// 总记录的页面数
	private int totalPage = 0;
	// 内容
	private Collection<T> content = new ArrayList<T>();

	private HashMap<String, Object> params = new HashMap<String, Object>();

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public int getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public Collection<T> getContent() {
		return content;
	}

	public void setContent(Collection<T> content) {
		this.content = content;
	}

	public HashMap<String, Object> getParams() {
		return params;
	}

	public void setParams(HashMap<String, Object> params) {
		this.params = params;
	}

}
