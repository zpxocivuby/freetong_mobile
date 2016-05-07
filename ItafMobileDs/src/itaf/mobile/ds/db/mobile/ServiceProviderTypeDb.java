package itaf.mobile.ds.db.mobile;

import itaf.framework.merchant.dto.BzServiceProviderTypeDto;
import itaf.mobile.ds.db.base.BaseTempleteDaoImpl;
import itaf.mobile.ds.db.util.SqlHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

/**
 * 商品品类
 *
 *
 *
 * @author XINXIN
 *
 * @UpdateDate 2014年9月22日
 */
public class ServiceProviderTypeDb extends
		BaseTempleteDaoImpl<BzServiceProviderTypeDto> {

	private static final String TABLE_NAME = " [SERVICE_PROVIDER_TYPE_DTO] ";
	private static final String ATTRS = "[ID],[TYPE_NAME],[TYPE_CODE],[PERENT_ID],[IS_LEAF],[ORDER_NO],[MARK_FOR_DELETE],[CREATED_BY],[CREATED_DATE],[UPDATED_BY],[UPDATED_DATE]";

	public ServiceProviderTypeDb(Context context) {
		super(context);
	}

	public void clearData() {
		this.execSql("delete from [SERVICE_PROVIDER_TYPE_DTO]");
	}

	public void save(BzServiceProviderTypeDto target) {
		Object[] params = new Object[] { target.getId(), target.getTypeName(),
				target.getTypeCode(), target.getParentId(), target.getIsLeaf(),
				target.getOrderNo(), target.getMarkForDelete(),
				target.getCreatedBy(), target.getCreatedDate(),
				target.getUpdatedBy(), target.getUpdatedDate() };
		this.execSql(SqlHelper.processInsertSql(TABLE_NAME, ATTRS), params);
	}

	public void saveList(Collection<BzServiceProviderTypeDto> target) {
		List<String> sqls = new ArrayList<String>();
		List<Object[]> params = new ArrayList<Object[]>();
		String sql = SqlHelper.processInsertSql(TABLE_NAME, ATTRS);
		for (BzServiceProviderTypeDto dto : target) {
			Object[] param = new Object[] { dto.getId(), dto.getTypeName(),
					dto.getTypeCode(), dto.getParentId(), dto.getIsLeaf(),
					dto.getOrderNo(), dto.getMarkForDelete(),
					dto.getCreatedBy(), dto.getCreatedDate(),
					dto.getUpdatedBy(), dto.getUpdatedDate() };
			sqls.add(sql);
			params.add(param);
		}
		this.execSqls(sqls, params);
	}

	public BzServiceProviderTypeDto findById(Long id) {
		String whereBody = "[ID]=" + id;
		Object obj = this.query(
				SqlHelper.processSelectSql(ATTRS, TABLE_NAME, whereBody), 1);
		return obj == null ? null : (BzServiceProviderTypeDto) obj;
	}

	@SuppressWarnings("unchecked")
	public List<BzServiceProviderTypeDto> findByPid(Long pid) {
		String whereBody = "[PERENT_ID]=" + pid;
		if (pid == null) {
			whereBody = "[PERENT_ID] is null";
		}
		Object obj = this.query(
				SqlHelper.processSelectSql(ATTRS, TABLE_NAME, whereBody), 2);
		return obj == null ? null : (List<BzServiceProviderTypeDto>) obj;
	}

	@Override
	public Object processCursor(Cursor cursor, int cursorKey) {
		Object result = null;
		switch (cursorKey) {
		case 1:
			BzServiceProviderTypeDto dto = new BzServiceProviderTypeDto();
			while (cursor.moveToNext()) {
				setBzServiceProviderTypeDto(cursor, dto);
			}
			result = dto;
			break;
		case 2:
			List<BzServiceProviderTypeDto> dtos = new ArrayList<BzServiceProviderTypeDto>();
			while (cursor.moveToNext()) {
				BzServiceProviderTypeDto target = new BzServiceProviderTypeDto();
				setBzServiceProviderTypeDto(cursor, target);
				dtos.add(target);
			}
			result = dtos;
			break;
		}
		return result;
	}

	private void setBzServiceProviderTypeDto(Cursor cursor,
			BzServiceProviderTypeDto dto) {
		dto.setId(getLongFromCursor(cursor, "ID"));
		dto.setTypeName(getStringFromCursor(cursor, "TYPE_NAME"));
		dto.setTypeCode(getStringFromCursor(cursor, "TYPE_CODE"));
		dto.setParentId(getLongFromCursor(cursor, "PERENT_ID"));
		dto.setIsLeaf(getBooleanFromCursor(cursor, "IS_LEAF"));
		dto.setOrderNo(getLongFromCursor(cursor, "ORDER_NO"));
		dto.setMarkForDelete(getBooleanFromCursor(cursor, "MARK_FOR_DELETE"));
		dto.setCreatedBy(getLongFromCursor(cursor, "CREATED_BY"));
		dto.setCreatedDate(getDateFromCursor(cursor, "CREATED_DATE"));
	}

}
