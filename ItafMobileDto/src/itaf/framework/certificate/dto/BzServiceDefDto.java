package itaf.framework.certificate.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * 服务定义
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */

public class BzServiceDefDto extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4142161609291738769L;
	/**
	 * 
	 */
	private Long type;
	private String name;
	private String description;
	private Date startDate;
	private Date endDate;
	private Date registerDate;
	private String provider;
	private Set<BzServiceParameterDefDto> bzServiceParameterDefDtos = new HashSet<BzServiceParameterDefDto>(
			0);

	public BzServiceDefDto() {
	}

	public Long getType() {
		return this.type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getRegisterDate() {
		return this.registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public String getProvider() {
		return this.provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public Set<BzServiceParameterDefDto> getBzServiceParameterDefDtos() {
		return bzServiceParameterDefDtos;
	}

	public void setBzServiceParameterDefDtos(
			Set<BzServiceParameterDefDto> bzServiceParameterDefDtos) {
		this.bzServiceParameterDefDtos = bzServiceParameterDefDtos;
	}


}
