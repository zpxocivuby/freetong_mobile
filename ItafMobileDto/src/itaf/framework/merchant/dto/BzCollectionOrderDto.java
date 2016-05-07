package itaf.framework.merchant.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;

import java.math.BigDecimal;

/**
 * 
 * 收款单
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class BzCollectionOrderDto extends BaseDto {

	private static final long serialVersionUID = 5971331367414444240L;

	private BzDistOrderDto bzDistOrderDto;
	private BigDecimal receivableAmount;
	private BigDecimal receivedAmount;
	private BigDecimal distAmount;

	public BzDistOrderDto getBzDistOrderDto() {
		return bzDistOrderDto;
	}

	public void setBzDistOrderDto(BzDistOrderDto bzDistOrderDto) {
		this.bzDistOrderDto = bzDistOrderDto;
	}

	public BigDecimal getReceivableAmount() {
		return receivableAmount;
	}

	public void setReceivableAmount(BigDecimal receivableAmount) {
		this.receivableAmount = receivableAmount;
	}

	public BigDecimal getReceivedAmount() {
		return this.receivedAmount;
	}

	public void setReceivedAmount(BigDecimal receivedAmount) {
		this.receivedAmount = receivedAmount;
	}

	public BigDecimal getDistAmount() {
		return distAmount;
	}

	public void setDistAmount(BigDecimal distAmount) {
		this.distAmount = distAmount;
	}

}
