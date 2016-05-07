package itaf.framework.product.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * 商品品牌
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class BzProductBrandDto extends BaseDto {


	/**
	 * 
	 */
	private static final long serialVersionUID = -3073601141845810522L;
	private String brandCode;
	private String brandName;
	private String producer;
	private Set<BzProductDto> bzProductDtos = new HashSet<BzProductDto>(0);

	public BzProductBrandDto() {
	}

	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public Set<BzProductDto> getBzProductDtos() {
		return bzProductDtos;
	}

	public void setBzProductDtos(Set<BzProductDto> bzProductDtos) {
		this.bzProductDtos = bzProductDtos;
	}

}
