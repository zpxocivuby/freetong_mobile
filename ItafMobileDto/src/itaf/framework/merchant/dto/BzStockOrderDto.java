package itaf.framework.merchant.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * 备货单
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class BzStockOrderDto extends BaseDto {

	private static final long serialVersionUID = 4260356491553587133L;

	private BzMerchantDto bzMerchantDto;
	private String orderSerialNo;
	private Long orderStatus;
	private Date orderEdc;

	private List<BzStockOrderItemDto> bzStockOrderItemDtos = new ArrayList<BzStockOrderItemDto>();

	public BzStockOrderDto() {
		//
	}

	public BzMerchantDto getBzMerchantDto() {
		return this.bzMerchantDto;
	}

	public void setBzMerchantDto(BzMerchantDto bzMerchantDto) {
		this.bzMerchantDto = bzMerchantDto;
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

	public Date getOrderEdc() {
		return orderEdc;
	}

	public void setOrderEdc(Date orderEdc) {
		this.orderEdc = orderEdc;
	}

	public List<BzStockOrderItemDto> getBzStockOrderItemDtos() {
		return bzStockOrderItemDtos;
	}

	public void setBzStockOrderItemDtos(
			List<BzStockOrderItemDto> bzStockOrderItemDtos) {
		this.bzStockOrderItemDtos = bzStockOrderItemDtos;
	}

}
