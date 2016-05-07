package itaf.framework.order.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;
import itaf.framework.consumer.dto.BzConsumerDto;
import itaf.framework.merchant.dto.BzMerchantDto;

import java.math.BigDecimal;

/**
 * 
 * 订单
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class BzOrderRefundDto extends BaseDto implements java.io.Serializable {

	private static final long serialVersionUID = -858852241947035877L;

	// 申请退款
	public static final Long STATUS_AYYLY = 1L;
	// 退款结果接收退款
	public static final Long STATUS_ACCEPT = 2L;
	// 退款结果 拒绝退款
	public static final Long STATUS_REJECT = 3L;
	// 退款结果
	public static final Long STATUS_ACCEPT_REJECT = 5L;

	private BzOrderDto bzOrderDto;
	private BzConsumerDto bzConsumerDto;
	private BzMerchantDto bzMerchantDto;
	private String refundSerialNo;
	private Long refundStatus;
	private BigDecimal refundAmount;
	private String refundDesc;

	public BzOrderRefundDto() {
		//
	}

	public BzOrderDto getBzOrderDto() {
		return bzOrderDto;
	}

	public void setBzOrderDto(BzOrderDto bzOrderDto) {
		this.bzOrderDto = bzOrderDto;
	}

	public BzConsumerDto getBzConsumerDto() {
		return bzConsumerDto;
	}

	public void setBzConsumerDto(BzConsumerDto bzConsumerDto) {
		this.bzConsumerDto = bzConsumerDto;
	}

	public BzMerchantDto getBzMerchantDto() {
		return bzMerchantDto;
	}

	public void setBzMerchantDto(BzMerchantDto bzMerchantDto) {
		this.bzMerchantDto = bzMerchantDto;
	}

	public String getRefundSerialNo() {
		return refundSerialNo;
	}

	public void setRefundSerialNo(String refundSerialNo) {
		this.refundSerialNo = refundSerialNo;
	}

	public Long getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(Long refundStatus) {
		this.refundStatus = refundStatus;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getRefundDesc() {
		return refundDesc;
	}

	public void setRefundDesc(String refundDesc) {
		this.refundDesc = refundDesc;
	}

}
