package itaf.framework.product.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseTreeDto;

/**
 * 
 * 商品品类
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class BzProductCategoryDto extends BaseTreeDto {

	private static final long serialVersionUID = 7521806404966833242L;

	private String categoryName;
	private String categoryCode;

	public BzProductCategoryDto() {
		//
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

}
