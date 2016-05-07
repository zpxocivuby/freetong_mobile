package itaf.framework.order.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;
import itaf.framework.consumer.dto.BzConsumerDto;
import itaf.framework.consumer.dto.BzUserAddressDto;
import itaf.framework.merchant.dto.BzInvoiceDto;
import itaf.framework.merchant.dto.BzMerchantDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 订单
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class BzOrderDto extends BaseDto {

	private static final long serialVersionUID = 4750727067541835834L;

	// 查询全部
	public static final Long STATUS_ALL = -1L;
	// 待付款
	public static final Long STATUS_WAIT_PAY = 1L;
	// 待发货
	public static final Long STATUS_WAIT_DELIVERY = 2L;
	// 待收货
	public static final Long STATUS_WAIT_RECEIPT = 3L;
	// 待评价
	public static final Long STATUS_WAIT_EVALUATION = 4L;
	// 待退款
	public static final Long STATUS_WAIT_REFUND = 5L;
	// 已完成
	public static final Long STATUS_PROCESSED = 6L;
	// 已取消
	public static final Long STATUS_CANCEL = 7L;
	// 已退款
	public static final Long STATUS_REFUND = 8L;
	// 待付款
	public static final String STATUS_WAIT_PAY_STR = "待付款";
	// 待发货
	public static final String STATUS_WAIT_DELIVERY_STR = "待发货";
	// 待收货
	public static final String STATUS_WAIT_RECEIPT_STR = "待收货";
	// 待评价
	public static final String STATUS_WAIT_EVALUATION_STR = "待评价";
	// 待退款
	public static final String STATUS_WAIT_REFUND_STR = "待退款";
	// 已完成
	public static final String STATUS_PROCESSED_STR = "已完成";
	// 已取消
	public static final String STATUS_CANCEL_STR = "已取消";

	private Long bzConsumerId;
	private BzConsumerDto bzConsumerDto;
	private Long bzMerchantId;
	private BzMerchantDto bzMerchantDto;
	private BzUserAddressDto bzUserAddressDto;
	private String orderSerialNo;
	private Long orderStatus;
	private BigDecimal orderDiscount;
	private BigDecimal orderPreferential;
	private BigDecimal orderAmount;
	private List<BzOrderItemDto> bzOrderItemDtos = new ArrayList<BzOrderItemDto>();
	private BzOrderPaymentDto bzOrderPaymentDto;
	private String orderDesc;
	private List<Long> bzCartItemIds = new ArrayList<Long>();
	private BzInvoiceDto bzInvoiceDto;
	// server no attribute
	private String orderStatusString;

	public BzOrderDto() {
		//
	}

	public Long getBzConsumerId() {
		return bzConsumerId;
	}

	public void setBzConsumerId(Long bzConsumerId) {
		this.bzConsumerId = bzConsumerId;
	}

	public BzConsumerDto getBzConsumerDto() {
		return bzConsumerDto;
	}

	public void setBzConsumerDto(BzConsumerDto bzConsumerDto) {
		this.bzConsumerDto = bzConsumerDto;
	}

	public Long getBzMerchantId() {
		return bzMerchantId;
	}

	public void setBzMerchantId(Long bzMerchantId) {
		this.bzMerchantId = bzMerchantId;
	}

	public BzMerchantDto getBzMerchantDto() {
		return bzMerchantDto;
	}

	public void setBzMerchantDto(BzMerchantDto bzMerchantDto) {
		this.bzMerchantDto = bzMerchantDto;
	}

	public BzUserAddressDto getBzUserAddressDto() {
		return bzUserAddressDto;
	}

	public void setBzUserAddressDto(BzUserAddressDto bzUserAddressDto) {
		this.bzUserAddressDto = bzUserAddressDto;
	}

	public String getOrderSerialNo() {
		return orderSerialNo;
	}

	public void setOrderSerialNo(String orderSerialNo) {
		this.orderSerialNo = orderSerialNo;
	}

	public Long getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Long orderStatus) {
		this.orderStatus = orderStatus;
	}

	public BigDecimal getOrderDiscount() {
		return orderDiscount;
	}

	public void setOrderDiscount(BigDecimal orderDiscount) {
		this.orderDiscount = orderDiscount;
	}

	public BigDecimal getOrderPreferential() {
		return orderPreferential;
	}

	public void setOrderPreferential(BigDecimal orderPreferential) {
		this.orderPreferential = orderPreferential;
	}

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	public List<BzOrderItemDto> getBzOrderItemDtos() {
		return bzOrderItemDtos;
	}

	public void setBzOrderItemDtos(List<BzOrderItemDto> bzOrderItemDtos) {
		this.bzOrderItemDtos = bzOrderItemDtos;
	}

	public BzOrderPaymentDto getBzOrderPaymentDto() {
		return bzOrderPaymentDto;
	}

	public void setBzOrderPaymentDto(BzOrderPaymentDto bzOrderPaymentDto) {
		this.bzOrderPaymentDto = bzOrderPaymentDto;
	}

	public String getOrderDesc() {
		return orderDesc;
	}

	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}

	public List<Long> getBzCartItemIds() {
		return bzCartItemIds;
	}

	public void setBzCartItemIds(List<Long> bzCartItemIds) {
		this.bzCartItemIds = bzCartItemIds;
	}

	public BzInvoiceDto getBzInvoiceDto() {
		return bzInvoiceDto;
	}

	public void setBzInvoiceDto(BzInvoiceDto bzInvoiceDto) {
		this.bzInvoiceDto = bzInvoiceDto;
	}

	public String getOrderStatusString() {
		if (orderStatus.equals(STATUS_WAIT_PAY)) {
			orderStatusString = STATUS_WAIT_PAY_STR;
		} else if (orderStatus.equals(STATUS_WAIT_DELIVERY)) {
			orderStatusString = STATUS_WAIT_DELIVERY_STR;
		} else if (orderStatus.equals(STATUS_WAIT_RECEIPT)) {
			orderStatusString = STATUS_WAIT_RECEIPT_STR;
		} else if (orderStatus.equals(STATUS_WAIT_EVALUATION)) {
			orderStatusString = STATUS_WAIT_EVALUATION_STR;
		} else if (orderStatus.equals(STATUS_WAIT_REFUND)) {
			orderStatusString = STATUS_WAIT_REFUND_STR;
		} else if (orderStatus.equals(STATUS_PROCESSED)) {
			orderStatusString = STATUS_PROCESSED_STR;
		} else if (orderStatus.equals(STATUS_CANCEL)) {
			orderStatusString = STATUS_CANCEL_STR;
		}
		return orderStatusString;
	}

	public void setOrderStatusString(String orderStatusString) {
		this.orderStatusString = orderStatusString;
	}

}
