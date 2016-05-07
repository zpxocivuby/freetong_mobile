package itaf.framework.platform.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;

/**
 * 
 * 系统参数配置
 *
 * @author
 *
 * @UpdateDate 2014年9月4日
 */
public class SysParameterDto extends BaseDto {


	/**
	 * 
	 */
	private static final long serialVersionUID = -6521438676719352886L;
	private String category;
	private String nameen;
	private String namezh;
	private String value;

	public SysParameterDto() {
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getNameen() {
		return nameen;
	}

	public void setNameen(String nameen) {
		this.nameen = nameen;
	}

	public String getNamezh() {
		return namezh;
	}

	public void setNamezh(String namezh) {
		this.namezh = namezh;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}


}
