package itaf.framework.platform.dto;

import itaf.framework.base.dto.BaseTreeDto;

import java.util.HashSet;
import java.util.Set;

public class SysResourceDto extends BaseTreeDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4332604520634968923L;
	private SysResourceDto parentDto;
	private String type;
	private String path;
	private String nameZh;
	private String nameEn;
	private String description;
	private Boolean isNav;
	private Set<SysResourceDto> childrenDtos = new HashSet<SysResourceDto>(0);
	private Set<SysRoleDto> sysRoleDtos = new HashSet<SysRoleDto>(0);

	public SysResourceDto getParentDto() {
		return parentDto;
	}

	public void setParentDto(SysResourceDto parentDto) {
		this.parentDto = parentDto;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getNameZh() {
		return nameZh;
	}

	public void setNameZh(String nameZh) {
		this.nameZh = nameZh;
	}

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getIsNav() {
		return isNav;
	}

	public void setIsNav(Boolean isNav) {
		this.isNav = isNav;
	}

	public Set<SysResourceDto> getChildrenDtos() {
		return childrenDtos;
	}

	public void setChildrenDtos(Set<SysResourceDto> childrenDtos) {
		this.childrenDtos = childrenDtos;
	}

	public Set<SysRoleDto> getSysRoleDtos() {
		return sysRoleDtos;
	}

	public void setSysRoleDtos(Set<SysRoleDto> sysRoleDtos) {
		this.sysRoleDtos = sysRoleDtos;
	}

}
