package itaf.mobile.ds.db.mobile;

import itaf.framework.product.dto.BzProductCategoryDto;
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
public class ProductCategoryDb extends
		BaseTempleteDaoImpl<BzProductCategoryDto> {

	private static final String TABLE_NAME = " [PRODUCT_CATEGORY_DTO] ";
	private static final String ATTRS = "[ID],[CATEGORY_NAME],[CATEGORY_CODE],[PERENT_ID],[IS_LEAF],[ORDER_NO],[MARK_FOR_DELETE],[CREATED_BY],[CREATED_DATE],[UPDATED_BY],[UPDATED_DATE]";

	public ProductCategoryDb(Context context) {
		super(context);
	}

	public void clearData() {
		this.execSql("delete from [PRODUCT_CATEGORY_DTO]");
	}

	public void save(BzProductCategoryDto target) {
		Object[] params = new Object[] { target.getId(),
				target.getCategoryName(), target.getCategoryCode(),
				target.getParentId(), target.getIsLeaf(), target.getOrderNo(),
				target.getMarkForDelete(), target.getCreatedBy(),
				target.getCreatedDate(), target.getUpdatedBy(),
				target.getUpdatedDate() };
		this.execSql(SqlHelper.processInsertSql(TABLE_NAME, ATTRS), params);
	}

	public void saveList(Collection<BzProductCategoryDto> target) {
		List<String> sqls = new ArrayList<String>();
		List<Object[]> params = new ArrayList<Object[]>();
		String sql = SqlHelper.processInsertSql(TABLE_NAME, ATTRS);
		for (BzProductCategoryDto dto : target) {
			Object[] param = new Object[] { dto.getId(), dto.getCategoryName(),
					dto.getCategoryCode(), dto.getParentId(), dto.getIsLeaf(),
					dto.getOrderNo(), dto.getMarkForDelete(),
					dto.getCreatedBy(), dto.getCreatedDate(),
					dto.getUpdatedBy(), dto.getUpdatedDate() };
			sqls.add(sql);
			params.add(param);
		}
		this.execSqls(sqls, params);
	}

	public BzProductCategoryDto findById(Long id) {
		String whereBody = "[ID]=" + id;
		Object obj = this.query(
				SqlHelper.processSelectSql(ATTRS, TABLE_NAME, whereBody), 1);
		return obj == null ? null : (BzProductCategoryDto) obj;
	}

	@SuppressWarnings("unchecked")
	public List<BzProductCategoryDto> findByPid(Long pid) {
		String whereBody = "[PERENT_ID]=" + pid;
		if (pid == null) {
			whereBody = "[PERENT_ID] is null";
		}
		Object obj = this.query(
				SqlHelper.processSelectSql(ATTRS, TABLE_NAME, whereBody), 2);
		return obj == null ? null : (List<BzProductCategoryDto>) obj;
	}

	@Override
	public Object processCursor(Cursor cursor, int cursorKey) {
		Object result = null;
		switch (cursorKey) {
		case 1:
			BzProductCategoryDto dto = new BzProductCategoryDto();
			if (cursor.moveToNext()) {
				setBzProductCategoryDto(cursor, dto);
			}
			result = dto;
			break;
		case 2:
			List<BzProductCategoryDto> dtos = new ArrayList<BzProductCategoryDto>();
			while (cursor.moveToNext()) {
				BzProductCategoryDto target = new BzProductCategoryDto();
				setBzProductCategoryDto(cursor, target);
				dtos.add(target);
			}
			result = dtos;
			break;
		}
		return result;
	}

	private void setBzProductCategoryDto(Cursor cursor, BzProductCategoryDto dto) {
		dto.setId(getLongFromCursor(cursor, "ID"));
		dto.setCategoryName(getStringFromCursor(cursor, "CATEGORY_NAME"));
		dto.setCategoryCode(getStringFromCursor(cursor, "CATEGORY_CODE"));
		dto.setParentId(getLongFromCursor(cursor, "PERENT_ID"));
		dto.setIsLeaf(getBooleanFromCursor(cursor, "IS_LEAF"));
		dto.setOrderNo(getLongFromCursor(cursor, "ORDER_NO"));
		dto.setMarkForDelete(getBooleanFromCursor(cursor, "MARK_FOR_DELETE"));
		dto.setCreatedBy(getLongFromCursor(cursor, "CREATED_BY"));
		dto.setCreatedDate(getDateFromCursor(cursor, "CREATED_DATE"));
	}

}
