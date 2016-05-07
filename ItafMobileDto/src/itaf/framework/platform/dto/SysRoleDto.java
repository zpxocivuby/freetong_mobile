package itaf.framework.platform.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * 系统角色
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class SysRoleDto extends BaseDto {


	/**
	 * 
	 */
	private static final long serialVersionUID = 6829853725394822594L;
	private String nameZh;
	private String nameEn;
	private String description;
	private Set<SysResourceDto> sysResourceDtos = new HashSet<SysResourceDto>(0);
	private Set<SysUserDto> sysUserDtos = new HashSet<SysUserDto>(0);

	public SysRoleDto() {
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

	public Set<SysResourceDto> getSysResourceDtos() {
		return sysResourceDtos;
	}

	public void setSysResourceDtos(Set<SysResourceDto> sysResourceDtos) {
		this.sysResourceDtos = sysResourceDtos;
	}

	public Set<SysUserDto> getSysUserDtos() {
		return sysUserDtos;
	}

	public void setSysUserDtos(Set<SysUserDto> sysUserDtos) {
		this.sysUserDtos = sysUserDtos;
	}

}
