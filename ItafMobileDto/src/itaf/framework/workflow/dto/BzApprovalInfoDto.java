package itaf.framework.workflow.dto;

import itaf.framework.base.dto.BaseDto;

import java.util.Date;

public class BzApprovalInfoDto extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1871761954193231399L;
	// 流程实例ID
	private String processInstanceId;
	// 任务ID
	private String taskId;
	// 业务主键
	private String bussinessKey;
	// 节点名称
	private String activityName;
	// 审批用户名称
	private String approvalName;
	// 审批状态
	private String approvalState;
	// 审批意见
	private String approvalInfo;
	// 审批时间
	private Date approvalTime;

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getBussinessKey() {
		return bussinessKey;
	}

	public void setBussinessKey(String bussinessKey) {
		this.bussinessKey = bussinessKey;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getApprovalName() {
		return approvalName;
	}

	public void setApprovalName(String approvalName) {
		this.approvalName = approvalName;
	}

	public String getApprovalState() {
		return approvalState;
	}

	public void setApprovalState(String approvalState) {
		this.approvalState = approvalState;
	}

	public String getApprovalInfo() {
		return approvalInfo;
	}

	public void setApprovalInfo(String approvalInfo) {
		this.approvalInfo = approvalInfo;
	}

	public Date getApprovalTime() {
		return approvalTime;
	}

	public void setApprovalTime(Date approvalTime) {
		this.approvalTime = approvalTime;
	}

}
