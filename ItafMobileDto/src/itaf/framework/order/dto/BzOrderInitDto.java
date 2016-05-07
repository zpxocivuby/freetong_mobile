package itaf.framework.order.dto;

import itaf.framework.base.dto.BaseDto;
import itaf.framework.consumer.dto.BzUserAddressDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 初始化订单
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class BzOrderInitDto extends BaseDto {

	private static final long serialVersionUID = -5094507081925360405L;
	// 默认的收货地址
	private BzUserAddressDto bzUserAddressDto;
	// 配送费
	private BigDecimal distPrice;
	// 支付类型
	private List<BzPaymentTypeDto> bzPaymentTypeDtos = new ArrayList<BzPaymentTypeDto>();

	public BzOrderInitDto() {
		//
	}

	public BzUserAddressDto getBzUserAddressDto() {
		return bzUserAddressDto;
	}

	public void setBzUserAddressDto(BzUserAddressDto bzUserAddressDto) {
		this.bzUserAddressDto = bzUserAddressDto;
	}

	public BigDecimal getDistPrice() {
		return distPrice;
	}

	public void setDistPrice(BigDecimal distPrice) {
		this.distPrice = distPrice;
	}

	public List<BzPaymentTypeDto> getBzPaymentTypeDtos() {
		return bzPaymentTypeDtos;
	}

	public void setBzPaymentTypeDtos(List<BzPaymentTypeDto> bzPaymentTypeDtos) {
		this.bzPaymentTypeDtos = bzPaymentTypeDtos;
	}

}
