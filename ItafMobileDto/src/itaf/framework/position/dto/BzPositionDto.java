package itaf.framework.position.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;

import java.math.BigDecimal;

/**
 * 
 * 位置信息
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class BzPositionDto extends BaseDto {

	private static final long serialVersionUID = 2413867781863835817L;

	private BigDecimal x;
	private BigDecimal y;
	private BigDecimal z;

	public BzPositionDto() {
	}

	public BigDecimal getX() {
		return x;
	}

	public void setX(BigDecimal x) {
		this.x = x;
	}

	public BigDecimal getY() {
		return y;
	}

	public void setY(BigDecimal y) {
		this.y = y;
	}

	public BigDecimal getZ() {
		return z;
	}

	public void setZ(BigDecimal z) {
		this.z = z;
	}

}
