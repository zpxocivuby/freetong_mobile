package itaf.framework.common.dto;

import itaf.framework.base.dto.BaseDto;

public class BzAttachmentDto extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2209737161543852717L;
	private String srcFileName;
	private String fileName;
	private Long fileType;
	private String fileExt;

	public String getSrcFileName() {
		return srcFileName;
	}

	public String getFileName() {
		return fileName;
	}

	public Long getFileType() {
		return fileType;
	}

	public String getFileExt() {
		return fileExt;
	}

	public void setSrcFileName(String srcFileName) {
		this.srcFileName = srcFileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFileType(Long fileType) {
		this.fileType = fileType;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

}
