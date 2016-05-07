package itaf.framework.product.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;

/**
 * 
 * 商品附件
 *
 * @author
 *
 * @UpdateDate 2014年9月4日
 */
public class BzProductAttachmentDto extends BaseDto {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1492336901514133332L;
	private BzProductDto bzProductDto;
	private String srcFileName;
	private String fileName;
	private Long fileType;
	private String fileExt;

	public BzProductAttachmentDto() {
	}

	public BzProductDto getBzProductDto() {
		return bzProductDto;
	}

	public void setBzProductDto(BzProductDto bzProductDto) {
		this.bzProductDto = bzProductDto;
	}

	public String getSrcFileName() {
		return srcFileName;
	}

	public void setSrcFileName(String srcFileName) {
		this.srcFileName = srcFileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Long getFileType() {
		return fileType;
	}

	public void setFileType(Long fileType) {
		this.fileType = fileType;
	}

	public String getFileExt() {
		return fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

}
