package itaf.framework.cart.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;
import itaf.framework.consumer.dto.BzConsumerDto;
import itaf.framework.merchant.dto.BzMerchantDto;
import itaf.framework.product.dto.BzProductDto;

import java.math.BigDecimal;

/**
 * 
 * 购物车条目
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */

public class BzCartItemDto extends BaseDto {

	private static final long serialVersionUID = 6577184095175751153L;

	private Long bzConsumerId;
	private BzConsumerDto bzConsumerDto;
	private Long bzMerchantId;
	private BzMerchantDto bzMerchantDto;
	private Long bzProductId;
	private BzProductDto bzProductDto;
	private Long itemNum;
	private BigDecimal itemUnitPrice;
	private BigDecimal itemDiscount;
	private BigDecimal itemPreferential;
	private BigDecimal itemPrice;
	private Long itemStatus;

	public BzCartItemDto() {
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
		return this.bzMerchantId;
	}

	public void setBzMerchantId(Long bzMerchantId) {
		this.bzMerchantId = bzMerchantId;
	}

	public BzMerchantDto getBzMerchantDto() {
		return this.bzMerchantDto;
	}

	public void setBzMerchantDto(BzMerchantDto bzMerchantDto) {
		this.bzMerchantDto = bzMerchantDto;
	}

	public Long getBzProductId() {
		return bzProductId;
	}

	public void setBzProductId(Long bzProductId) {
		this.bzProductId = bzProductId;
	}

	public BzProductDto getBzProductDto() {
		return bzProductDto;
	}

	public void setBzProductDto(BzProductDto bzProductDto) {
		this.bzProductDto = bzProductDto;
	}

	public Long getItemNum() {
		return this.itemNum;
	}

	public void setItemNum(Long itemNum) {
		this.itemNum = itemNum;
	}

	public BigDecimal getItemUnitPrice() {
		return this.itemUnitPrice;
	}

	public void setItemUnitPrice(BigDecimal itemUnitPrice) {
		this.itemUnitPrice = itemUnitPrice;
	}

	public BigDecimal getItemDiscount() {
		return this.itemDiscount;
	}

	public void setItemDiscount(BigDecimal itemDiscount) {
		this.itemDiscount = itemDiscount;
	}

	public BigDecimal getItemPreferential() {
		return this.itemPreferential;
	}

	public void setItemPreferential(BigDecimal itemPreferential) {
		this.itemPreferential = itemPreferential;
	}

	public BigDecimal getItemPrice() {
		return this.itemPrice;
	}

	public void setItemPrice(BigDecimal itemPrice) {
		this.itemPrice = itemPrice;
	}

	public Long getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(Long itemStatus) {
		this.itemStatus = itemStatus;
	}

}
