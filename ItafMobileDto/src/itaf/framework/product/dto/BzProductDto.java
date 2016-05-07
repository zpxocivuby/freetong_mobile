package itaf.framework.product.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;
import itaf.framework.merchant.dto.BzMerchantDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 商品
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class BzProductDto extends BaseDto {

	private static final long serialVersionUID = -2648689287494518131L;

	public static final Long SHELF_ON = 1L;
	public static final Long SHELF_DOWN = 0L;

	private Long bzMerchantId;
	private BzMerchantDto bzMerchantDto;
	private String productName;
	private BigDecimal productPrice;
	private BigDecimal productPurchasePrice;
	private String productColor;
	private String productDescription;
	private Long productOnSale;
	private Long isStockLimitless;
	private Long isShelfLimitless;
	private Long isStockSupport;
	private Long isShelfSupport;
	private Long shelfNum;
	private Long stockNum;
	private Boolean isFavorited;
	private List<Long> bzProductCategoryIds = new ArrayList<Long>();
	private List<Long> bzProductAttachmentIds = new ArrayList<Long>();

	public BzProductDto() {
		//
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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}

	public BigDecimal getProductPurchasePrice() {
		return productPurchasePrice;
	}

	public void setProductPurchasePrice(BigDecimal productPurchasePrice) {
		this.productPurchasePrice = productPurchasePrice;
	}

	public String getProductColor() {
		return productColor;
	}

	public void setProductColor(String productColor) {
		this.productColor = productColor;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public Long getProductOnSale() {
		return productOnSale;
	}

	public void setProductOnSale(Long productOnSale) {
		this.productOnSale = productOnSale;
	}

	public Long getIsStockLimitless() {
		return isStockLimitless;
	}

	public void setIsStockLimitless(Long isStockLimitless) {
		this.isStockLimitless = isStockLimitless;
	}

	public Long getIsShelfLimitless() {
		return isShelfLimitless;
	}

	public void setIsShelfLimitless(Long isShelfLimitless) {
		this.isShelfLimitless = isShelfLimitless;
	}

	public Long getIsStockSupport() {
		return isStockSupport;
	}

	public void setIsStockSupport(Long isStockSupport) {
		this.isStockSupport = isStockSupport;
	}

	public Long getIsShelfSupport() {
		return isShelfSupport;
	}

	public void setIsShelfSupport(Long isShelfSupport) {
		this.isShelfSupport = isShelfSupport;
	}

	public Long getShelfNum() {
		return shelfNum;
	}

	public void setShelfNum(Long shelfNum) {
		this.shelfNum = shelfNum;
	}

	public Long getStockNum() {
		return stockNum;
	}

	public void setStockNum(Long stockNum) {
		this.stockNum = stockNum;
	}

	public Boolean getIsFavorited() {
		return isFavorited;
	}

	public void setIsFavorited(Boolean isFavorited) {
		this.isFavorited = isFavorited;
	}

	public List<Long> getBzProductCategoryIds() {
		return bzProductCategoryIds;
	}

	public void setBzProductCategoryIds(List<Long> bzProductCategoryIds) {
		this.bzProductCategoryIds = bzProductCategoryIds;
	}

	public List<Long> getBzProductAttachmentIds() {
		return bzProductAttachmentIds;
	}

	public void setBzProductAttachmentIds(List<Long> bzProductAttachmentIds) {
		this.bzProductAttachmentIds = bzProductAttachmentIds;
	}

}
