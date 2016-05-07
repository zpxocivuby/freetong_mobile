package itaf.mobile.test.db;

import itaf.framework.product.dto.BzProductCategoryDto;
import itaf.mobile.ds.db.mobile.ProductCategoryDb;

import java.util.Date;

import android.test.AndroidTestCase;

/**
 * 商品品类测试
 *
 *
 *
 * @author XINXIN
 *
 * @UpdateDate 2014年9月22日
 */
public class TestProductCategoryDb extends AndroidTestCase {

	public void testSave1() {
		ProductCategoryDb db = new ProductCategoryDb(this.mContext);
		BzProductCategoryDto target = new BzProductCategoryDto();
		target.setId(1001L);
		target.setCategoryName("美食");
		target.setCategoryCode("1001");
		target.setMarkForDelete(false);
		target.setParentId(null);
		target.setOrderNo(1L);
		target.setIsLeaf(false);
		target.setCreatedBy(1L);
		target.setCreatedDate(new Date());
		db.save(target);
		BzProductCategoryDto target1 = new BzProductCategoryDto();
		target1.setId(10010001L);
		target1.setCategoryName("全部美食");
		target1.setCategoryCode("10010001");
		target1.setMarkForDelete(false);
		target1.setParentId(1001L);
		target1.setOrderNo(1L);
		target1.setIsLeaf(true);
		target1.setCreatedBy(1L);
		target1.setCreatedDate(new Date());
		db.save(target1);
		BzProductCategoryDto target2 = new BzProductCategoryDto();
		target2.setId(10010002L);
		target2.setCategoryName("本帮江浙菜");
		target2.setCategoryCode("10010002");
		target2.setMarkForDelete(false);
		target2.setParentId(1001L);
		target2.setOrderNo(2L);
		target2.setIsLeaf(true);
		target2.setCreatedBy(1L);
		target2.setCreatedDate(new Date());
		db.save(target2);
		BzProductCategoryDto target3 = new BzProductCategoryDto();
		target3.setId(10010003L);
		target3.setCategoryName("川菜");
		target3.setCategoryCode("10010003");
		target3.setMarkForDelete(false);
		target3.setParentId(1001L);
		target3.setOrderNo(3L);
		target3.setIsLeaf(true);
		target3.setCreatedBy(1L);
		target3.setCreatedDate(new Date());
		db.save(target3);
		BzProductCategoryDto target4 = new BzProductCategoryDto();
		target4.setId(10010004L);
		target4.setCategoryName("粤菜");
		target4.setCategoryCode("10010004");
		target4.setMarkForDelete(false);
		target4.setParentId(1001L);
		target4.setOrderNo(4L);
		target4.setIsLeaf(false);
		target4.setCreatedBy(1L);
		target4.setCreatedDate(new Date());
		BzProductCategoryDto target41 = new BzProductCategoryDto();
		target41.setId(100100040001L);
		target41.setCategoryName("鱼香肉丝");
		target41.setCategoryCode("100100040001");
		target41.setMarkForDelete(false);
		target41.setParentId(10010004L);
		target41.setOrderNo(1L);
		target41.setIsLeaf(true);
		target41.setCreatedBy(1L);
		target41.setCreatedDate(new Date());
		BzProductCategoryDto target42 = new BzProductCategoryDto();
		target42.setId(100100040002L);
		target42.setCategoryName("干煸豆角");
		target42.setCategoryCode("100100040002");
		target42.setMarkForDelete(false);
		target42.setParentId(10010004L);
		target42.setOrderNo(2L);
		target42.setIsLeaf(true);
		target42.setCreatedBy(1L);
		target42.setCreatedDate(new Date());
		db.save(target42);
	}

	public void testSave2() {
		ProductCategoryDb db = new ProductCategoryDb(this.mContext);
		BzProductCategoryDto target = new BzProductCategoryDto();
		target.setId(1002L);
		target.setCategoryName("休闲娱乐");
		target.setCategoryCode("1002");
		target.setMarkForDelete(false);
		target.setParentId(null);
		target.setOrderNo(2L);
		target.setIsLeaf(false);
		target.setCreatedBy(1L);
		target.setCreatedDate(new Date());
		db.save(target);
		BzProductCategoryDto target1 = new BzProductCategoryDto();
		target1.setId(10020001L);
		target1.setCategoryName("全部休闲娱乐");
		target1.setCategoryCode("10020001");
		target1.setMarkForDelete(false);
		target1.setParentId(1002L);
		target1.setOrderNo(1L);
		target1.setIsLeaf(true);
		target1.setCreatedBy(1L);
		target1.setCreatedDate(new Date());
		db.save(target1);
		BzProductCategoryDto target2 = new BzProductCategoryDto();
		target2.setId(10020002L);
		target2.setCategoryName("咖啡厅");
		target2.setCategoryCode("10020002");
		target2.setMarkForDelete(false);
		target2.setParentId(1002L);
		target2.setOrderNo(2L);
		target2.setIsLeaf(true);
		target2.setCreatedBy(1L);
		target2.setCreatedDate(new Date());
		db.save(target2);
		BzProductCategoryDto target3 = new BzProductCategoryDto();
		target3.setId(10020003L);
		target3.setCategoryName("酒吧");
		target3.setCategoryCode("10020003");
		target3.setMarkForDelete(false);
		target3.setParentId(1002L);
		target3.setOrderNo(2L);
		target3.setIsLeaf(true);
		target3.setCreatedBy(1L);
		target3.setCreatedDate(new Date());
		db.save(target3);
		BzProductCategoryDto target4 = new BzProductCategoryDto();
		target4.setId(10020004L);
		target4.setCategoryName("电影院");
		target4.setCategoryCode("10020004");
		target4.setMarkForDelete(false);
		target4.setParentId(1002L);
		target4.setOrderNo(4L);
		target4.setIsLeaf(true);
		target4.setCreatedBy(1L);
		target4.setCreatedDate(new Date());
		db.save(target4);
	}

	public void testSave3() {
		ProductCategoryDb db = new ProductCategoryDb(this.mContext);
		BzProductCategoryDto target = new BzProductCategoryDto();
		target.setId(1003L);
		target.setCategoryName("购物");
		target.setCategoryCode("1003");
		target.setMarkForDelete(false);
		target.setParentId(null);
		target.setOrderNo(3L);
		target.setIsLeaf(false);
		target.setCreatedBy(1L);
		target.setCreatedDate(new Date());
		db.save(target);
		BzProductCategoryDto target1 = new BzProductCategoryDto();
		target1.setId(10030001L);
		target1.setCategoryName("全部购物");
		target1.setCategoryCode("10030001");
		target1.setMarkForDelete(false);
		target1.setParentId(1003L);
		target1.setOrderNo(1L);
		target1.setIsLeaf(true);
		target1.setCreatedBy(1L);
		target1.setCreatedDate(new Date());
		db.save(target1);
		BzProductCategoryDto target2 = new BzProductCategoryDto();
		target2.setId(10030002L);
		target2.setCategoryName("综合商场");
		target2.setCategoryCode("10030002");
		target2.setMarkForDelete(false);
		target2.setParentId(1003L);
		target2.setOrderNo(2L);
		target2.setIsLeaf(true);
		target2.setCreatedBy(1L);
		target2.setCreatedDate(new Date());
		db.save(target2);
		BzProductCategoryDto target3 = new BzProductCategoryDto();
		target3.setId(10030003L);
		target3.setCategoryName("运动户外");
		target3.setCategoryCode("10030003");
		target3.setMarkForDelete(false);
		target3.setParentId(1003L);
		target3.setOrderNo(3L);
		target3.setIsLeaf(true);
		target3.setCreatedBy(1L);
		target3.setCreatedDate(new Date());
		db.save(target3);
		BzProductCategoryDto target4 = new BzProductCategoryDto();
		target4.setId(10030004L);
		target4.setCategoryName("化妆品");
		target4.setCategoryCode("10030004");
		target4.setMarkForDelete(false);
		target4.setParentId(1003L);
		target4.setOrderNo(4L);
		target4.setIsLeaf(true);
		target4.setCreatedBy(1L);
		target4.setCreatedDate(new Date());
		db.save(target4);
	}

}
