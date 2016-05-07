package itaf.framework.merchant.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;
import itaf.framework.platform.dto.SysUserDto;
import itaf.framework.product.dto.TrProductStockDto;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * 库存
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class BzStockDto extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3394921778544442242L;
	private SysUserDto sysUserDto;
	private String stockLocation;
	private Long stockNum;
	private Long stockType;
	private Long deductionNum;
	private Long inTransitNum;
	private Set<TrProductStockDto> trProductStockDtos = new HashSet<TrProductStockDto>(
			0);

	public BzStockDto() {
	}

	public SysUserDto getSysUserDto() {
		return sysUserDto;
	}

	public void setSysUserDto(SysUserDto sysUserDto) {
		this.sysUserDto = sysUserDto;
	}

	public String getStockLocation() {
		return stockLocation;
	}

	public void setStockLocation(String stockLocation) {
		this.stockLocation = stockLocation;
	}

	public Long getStockNum() {
		return stockNum;
	}

	public void setStockNum(Long stockNum) {
		this.stockNum = stockNum;
	}

	public Long getStockType() {
		return stockType;
	}

	public void setStockType(Long stockType) {
		this.stockType = stockType;
	}

	public Long getDeductionNum() {
		return deductionNum;
	}

	public void setDeductionNum(Long deductionNum) {
		this.deductionNum = deductionNum;
	}

	public Long getInTransitNum() {
		return inTransitNum;
	}

	public void setInTransitNum(Long inTransitNum) {
		this.inTransitNum = inTransitNum;
	}

	public Set<TrProductStockDto> getTrProductStockDtos() {
		return trProductStockDtos;
	}

	public void setTrProductStockDtos(Set<TrProductStockDto> trProductStockDtos) {
		this.trProductStockDtos = trProductStockDtos;
	}

}
