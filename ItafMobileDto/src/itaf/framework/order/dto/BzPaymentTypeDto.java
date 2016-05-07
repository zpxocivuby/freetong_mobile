package itaf.framework.order.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * 订单支付类型
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class BzPaymentTypeDto extends BaseDto {


	/**
	 * 
	 */
	private static final long serialVersionUID = -4920275610769949986L;
	private String typeName;
	private String typeDescription;
	private Set<BzOrderPaymentDto> bzOrderPaymentDtos = new HashSet<BzOrderPaymentDto>(
			0);

	public BzPaymentTypeDto() {
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeDescription() {
		return typeDescription;
	}

	public void setTypeDescription(String typeDescription) {
		this.typeDescription = typeDescription;
	}

	public Set<BzOrderPaymentDto> getBzOrderPaymentDtos() {
		return bzOrderPaymentDtos;
	}

	public void setBzOrderPaymentDtos(Set<BzOrderPaymentDto> bzOrderPaymentDtos) {
		this.bzOrderPaymentDtos = bzOrderPaymentDtos;
	}

}
