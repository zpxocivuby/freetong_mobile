package itaf.framework.order.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;

import java.math.BigDecimal;

/**
 * 
 * 订单支付
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class BzOrderPaymentDto extends BaseDto {

	private static final long serialVersionUID = 3657404715738797111L;
	// 已支付
	public static final long STATUS_PAY = 1L;
	// 等待支付
	public static final long STATUS_WAIT_PAY = -1L;

	private BzPaymentTypeDto bzPaymentTypeDto;
	private BzOrderDto bzOrderDto;
	private Long payStatus;
	private BigDecimal payAmount;

	public BzOrderPaymentDto() {
		//
	}

	public BzPaymentTypeDto getBzPaymentTypeDto() {
		return bzPaymentTypeDto;
	}

	public void setBzPaymentTypeDto(BzPaymentTypeDto bzPaymentTypeDto) {
		this.bzPaymentTypeDto = bzPaymentTypeDto;
	}

	public BzOrderDto getBzOrderDto() {
		return bzOrderDto;
	}

	public void setBzOrderDto(BzOrderDto bzOrderDto) {
		this.bzOrderDto = bzOrderDto;
	}

	public Long getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Long payStatus) {
		this.payStatus = payStatus;
	}

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

}
