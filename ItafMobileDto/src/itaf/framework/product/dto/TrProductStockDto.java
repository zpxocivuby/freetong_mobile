package itaf.framework.product.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;
import itaf.framework.merchant.dto.BzStockDto;

/**
 * 
 * 商品库存
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class TrProductStockDto extends BaseDto {


	/**
	 * 
	 */
	private static final long serialVersionUID = -4988017284066998002L;
	private TrProductStockIdDto idDto;
	private BzStockDto bzStockDto;
	private BzProductDto bzProductDto;

	public TrProductStockDto() {
	}

	public TrProductStockIdDto getIdDto() {
		return idDto;
	}

	public void setIdDto(TrProductStockIdDto idDto) {
		this.idDto = idDto;
	}

	public BzStockDto getBzStockDto() {
		return bzStockDto;
	}

	public void setBzStockDto(BzStockDto bzStockDto) {
		this.bzStockDto = bzStockDto;
	}

	public BzProductDto getBzProductDto() {
		return bzProductDto;
	}

	public void setBzProductDto(BzProductDto bzProductDto) {
		this.bzProductDto = bzProductDto;
	}


}
