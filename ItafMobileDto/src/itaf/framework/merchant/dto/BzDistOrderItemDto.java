package itaf.framework.merchant.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;
import itaf.framework.product.dto.BzProductDto;

/**
 * 
 * 配送单条目
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class BzDistOrderItemDto extends BaseDto {

	private static final long serialVersionUID = -2541734478776319405L;

	private Long id;
	private BzProductDto bzProductDto;
	private BzDistOrderDto bzDistOrderDto;
	private Long itemNum;

	public BzDistOrderItemDto() {
		//
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BzProductDto getBzProductDto() {
		return this.bzProductDto;
	}

	public void setBzProductDto(BzProductDto bzProductDto) {
		this.bzProductDto = bzProductDto;
	}

	public BzDistOrderDto getBzDistOrderDto() {
		return this.bzDistOrderDto;
	}

	public void setBzDistOrderDto(BzDistOrderDto bzDistOrderDto) {
		this.bzDistOrderDto = bzDistOrderDto;
	}

	public Long getItemNum() {
		return this.itemNum;
	}

	public void setItemNum(Long itemNum) {
		this.itemNum = itemNum;
	}

}
