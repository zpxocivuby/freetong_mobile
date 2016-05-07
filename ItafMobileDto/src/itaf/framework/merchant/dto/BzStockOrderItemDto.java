package itaf.framework.merchant.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;
import itaf.framework.product.dto.BzProductDto;

/**
 * 
 * 备货单条目
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class BzStockOrderItemDto extends BaseDto {

	private static final long serialVersionUID = -1311849789675027600L;

	private BzStockOrderDto bzStockOrderDto;
	private BzProductDto bzProductDto;
	private Long itemStatus;
	private Long itemNum;

	public BzStockOrderItemDto() {
	}

	public BzStockOrderDto getBzStockOrderDto() {
		return bzStockOrderDto;
	}

	public void setBzStockOrderDto(BzStockOrderDto bzStockOrderDto) {
		this.bzStockOrderDto = bzStockOrderDto;
	}

	public BzProductDto getBzProductDto() {
		return bzProductDto;
	}

	public void setBzProductDto(BzProductDto bzProductDto) {
		this.bzProductDto = bzProductDto;
	}

	public Long getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(Long itemStatus) {
		this.itemStatus = itemStatus;
	}

	public Long getItemNum() {
		return itemNum;
	}

	public void setItemNum(Long itemNum) {
		this.itemNum = itemNum;
	}

}
