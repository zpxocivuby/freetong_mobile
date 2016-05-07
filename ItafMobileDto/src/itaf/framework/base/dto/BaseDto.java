package itaf.framework.base.dto;

import java.io.Serializable;
import java.util.Date;

public abstract class BaseDto implements Serializable {

	private static final long serialVersionUID = 677069556104941115L;

	private Long id;
	private Long objId;
	private Long createdBy;
	private Date createdDate;
	private Long updatedBy;
	private Date updatedDate;
	private Boolean markForDelete = false;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
		this.objId = id;
	}

	public Long getObjId() {
		return objId;
	}

	public void setObjId(Long objId) {
		this.objId = objId;
	}

	public Long getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Long createBy) {
		this.createdBy = createBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createDate) {
		this.createdDate = createDate;
	}

	public Long getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(Long updateBy) {
		this.updatedBy = updateBy;
	}

	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Date updateDate) {
		this.updatedDate = updateDate;
	}

	public Boolean getMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(Boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseDto other = (BaseDto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
}
