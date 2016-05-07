package itaf.framework.order.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 历史订单
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class BzOrderHistoryDto extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3656479211661289297L;
	private BzOrderDto bzOrderDto;
	private Date archiveDate;
	private Long bzConsumerId;
	private Long bzConsumerAddressId;
	private Long bzMerchantId;
	private String orderCode;
	private Long orderStatus;
	private BigDecimal orderDiscount;
	private BigDecimal orderPreferential;
	private BigDecimal orderPrice;

	public BzOrderHistoryDto() {
	}

	public BzOrderHistoryDto(Date archiveDate) {
		this.archiveDate = archiveDate;
	}

	public BzOrderDto getBzOrderDto() {
		return bzOrderDto;
	}

	public void setBzOrderDto(BzOrderDto bzOrderDto) {
		this.bzOrderDto = bzOrderDto;
	}

	public Date getArchiveDate() {
		return archiveDate;
	}

	public void setArchiveDate(Date archiveDate) {
		this.archiveDate = archiveDate;
	}

	public Long getBzConsumerId() {
		return bzConsumerId;
	}

	public void setBzConsumerId(Long bzConsumerId) {
		this.bzConsumerId = bzConsumerId;
	}

	public Long getBzConsumerAddressId() {
		return bzConsumerAddressId;
	}

	public void setBzConsumerAddressId(Long bzConsumerAddressId) {
		this.bzConsumerAddressId = bzConsumerAddressId;
	}

	public Long getBzMerchantId() {
		return bzMerchantId;
	}

	public void setBzMerchantId(Long bzMerchantId) {
		this.bzMerchantId = bzMerchantId;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
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

	public BigDecimal getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(BigDecimal orderPrice) {
		this.orderPrice = orderPrice;
	}

}
