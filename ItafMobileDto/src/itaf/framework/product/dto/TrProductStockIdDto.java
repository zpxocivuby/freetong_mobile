package itaf.framework.product.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;

/**
 * 
 * 商品库存ID
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class TrProductStockIdDto extends BaseDto {


	/**
	 * 
	 */
	private static final long serialVersionUID = -8910749752073215125L;
	private Long bzProductId;
	private Long bzStockId;
	private Long stockNum;

	public TrProductStockIdDto() {
	}

	public Long getBzProductId() {
		return bzProductId;
	}

	public void setBzProductId(Long bzProductId) {
		this.bzProductId = bzProductId;
	}

	public Long getBzStockId() {
		return bzStockId;
	}

	public void setBzStockId(Long bzStockId) {
		this.bzStockId = bzStockId;
	}

	public Long getStockNum() {
		return stockNum;
	}

	public void setStockNum(Long stockNum) {
		this.stockNum = stockNum;
	}

}
