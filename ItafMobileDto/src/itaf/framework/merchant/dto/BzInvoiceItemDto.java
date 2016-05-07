package itaf.framework.merchant.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;
import itaf.framework.product.dto.BzProductDto;

/**
 * 
 * 发货单条目
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class BzInvoiceItemDto extends BaseDto {

	private static final long serialVersionUID = 1460987970212625324L;

	private BzProductDto bzProductDto;
	private BzInvoiceDto bzInvoiceDto;
	private Long itemNum;

	public BzInvoiceItemDto() {
		//
	}

	public BzProductDto getBzProductDto() {
		return bzProductDto;
	}

	public void setBzProductDto(BzProductDto bzProductDto) {
		this.bzProductDto = bzProductDto;
	}

	public BzInvoiceDto getBzInvoiceDto() {
		return bzInvoiceDto;
	}

	public void setBzInvoiceDto(BzInvoiceDto bzInvoiceDto) {
		this.bzInvoiceDto = bzInvoiceDto;
	}

	public Long getItemNum() {
		return itemNum;
	}

	public void setItemNum(Long itemNum) {
		this.itemNum = itemNum;
	}

}
