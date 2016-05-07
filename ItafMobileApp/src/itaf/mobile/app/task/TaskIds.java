package itaf.mobile.app.task;

/**
 * 任务Id
 * 
 * @author
 * 
 * @update 2013年9月2日
 */
public class TaskIds {

	// ----写任务:-1（往数据库写的任务）---//

	// ----本地读:1000-1999（只读取本地数据库，比如日历，联系人，部门）---//
	// 缓存清理
	public static final int TASK_CLEAR_CACHE = 1000;
	// 账号管理
	public static final int TASK_SETTING_ACCOUNT_MANAGE = 1001;
	// 商品分类
	public static final int TASK_PRODUCT_CATEGORY = 1002;

	// ---本地读&网络预读:2000-2999（读取本地数据库，并从网络中进行预读，分页使用）---//
	// 加载板块

	// ---网络读:3000+（只从网络中获取，不缓存）----//
	// 启动service
	public static final int TASK_START_SERVICE = 3001;
	// 注册
	public static final int TASK_SYS_REGISTER = 3002;
	// 用户登录
	public static final int TASK_LOGIN = 3003;
	// 搜索商品
	public static final int TASK_SEARCH_PRODUCTS = 3004;
	// 商品详情
	public static final int TASK_PRODUCT_DETAIL = 3006;
	// 商家详情
	public static final int TASK_MERCHANT_DETAIL = 3007;
	// 搜索商家
	public static final int TASK_SEARCH_MERCHANTS = 3008;

	// IM登陆
	public static final int TASK_IM_LOGIN = 3140;
	//
	public static final int PRE_TASK_USER_DELIVERY_ADDRESS = 3118;

	// ---网络读:5000+（只从网络中获取，需要登录）----//
	// 收货地址
	public static final int TASK_USER_ADDRESS_LIST = 5004;
	// 收货地址添加或者修改
	public static final int TASK_USER_ADDRESS_CREATE_OR_EDIT = 5005;
	// 收货地址详情
	public static final int TASK_USER_ADDRESS_DETAIL = 5006;
	// 收货地址删除
	public static final int TASK_USER_ADDRESS_DELETE = 5007;
	// 实名认证
	public static final int TASK_APPLY_REALNAME_CERTIFICATE = 5008;
	// 实名认证信息
	public static final int TASK_APPLY_REALNAME_CERTIFICATE_DETAIL = 5009;
	// 实名认证信息
	public static final int TASK_APPLY_SELLING_SERVICE_DETAIL = 5010;
	// 申请售卖服务
	public static final int TASK_APPLY_SELLING_SERVICE = 5011;
	// 商品添加
	public static final int TASK_PRODUCT_CREATE_OR_EDIT = 5012;
	// 商品删除
	public static final int TASK_PRODUCT_DELETE = 5014;
	// 商品上架
	public static final int TASK_PRODUCT_PUT_ON_SHELF = 5015;
	// 商品下架
	public static final int TASK_PRODUCT_REMOVE_FROM_SHELF = 5016;
	// 发货地址
	public static final int TASK_USER_DELIVERY_ADDRESS_LIST = 5018;
	// 发货地址添加或者修改
	public static final int TASK_USER_DELIVERY_ADDRESS_CREATE_OR_EDIT = 5019;
	// 发货地址详情
	public static final int TASK_USER_DELIVERY_ADDRESS_DETAIL = 5020;
	// 发货地址删除
	public static final int TASK_USER_DELIVERY_ADDRESS_DELETE = 5021;
	// 申请配送服务详情
	public static final int TASK_APPLY_DIST_SERVICE_DETAIL = 5022;
	// 购物车添加
	public static final int TASK_PUT_PRODUCT_IN_CART = 5023;
	// 购物车
	public static final int TASK_MENU_CART_LIST = 5024;
	// 订单创建初始化
	public static final int TASK_ORDER_CREATE_INIT = 5025;
	// 订单创建
	public static final int TASK_ORDER_CREATE = 5026;
	// 全部订单列表
	public static final int TASK_ORDER_PAGER = 5027;
	// 商品收藏
	public static final int TASK_PRODUCT_FAVORITE = 5028;
	// 商品收藏取消
	public static final int TASK_PRODUCT_FAVORITE_CANCEL = 5029;
	// 购物车商品删除
	public static final int TASK_MENU_CART_DELETE = 5031;
	// 商家发货
	public static final int TASK_MERCHANT_INVOICE = 5032;
	// 商家收藏
	public static final int TASK_MERCHANT_FAVORITE = 5033;
	// 商家收藏取消
	public static final int TASK_MERCHANT_FAVORITE_CANCEL = 5034;
	// 生成备货单
	public static final int TASK_STOCK_ORDER_CREATE = 5035;
	// 备货单列表
	public static final int TASK_STOCK_ORDER_LIST = 5036;
	// 备货单列表
	public static final int TASK_STOCK_ORDER_FINISHED = 5037;
	// 开始配送
	public static final int TASK_DIST_START = 5038;
	// 确认收货
	public static final int TASK_ORDER_CONFIRM_RECEIVED = 5039;
	// 用户详情
	public static final int TASK_SYS_USER_DETAIL = 5039;
	// 配送商结算汇总
	public static final int TASK_DIST_STATEMENT_SUMMARY = 5040;
	// 配送商结算单分页
	public static final int TASK_DIST_STATEMENT_PAGER = 5041;
	// 商家配送结算
	public static final int TASK_MERCHANT_DIST_STATEMENT = 5042;
	// 配送商结算单条目分页
	public static final int TASK_MERCHANT_DIST_STATEMENT_ITEM_PAGER = 5043;
	// 申请配送服务
	public static final int TASK_APPLY_DIST_SERVICE = 5044;
	// 收款
	public static final int TASK_DIST_COLLECTION = 5046;
	// 配送商分页
	public static final int TASK_DIST_COMPANY_PAGER = 5047;
	// 订单详情
	public static final int TASK_ORDER_DETAIL = 5048;
	// 配送商结算单条目分页
	public static final int TASK_DIST_STATEMENT_ITEM_PAGER = 5049;
	// 配送商结算单条目分页
	public static final int TASK_MERCHANT_DIST_STATEMENT_PAGER = 5050;
	// 结算确认
	public static final int TASK_DIST_STATEMENT_ACCEPT = 5051;
	// 结算拒绝
	public static final int TASK_DIST_STATEMENT_REJECT = 5052;
	// 退货分页
	public static final int TASK_ORDER_REFUND_PAGER = 5053;
	// 申请退货
	public static final int TASK_REFUND_APPLY = 5054;
	// 申请退货
	public static final int TASK_REFUND_CANCEL = 5055;
	// 申请退货
	public static final int TASK_REFUND_ACCEPT = 5056;
	// 申请退货
	public static final int TASK_REFUND_REJECT = 5057;
	// 订单评价
	public static final int TASK_ORDER_EVALUATION = 5058;
	// 更新购买数量
	public static final int TASK_UPDATE_BUY_NUM = 5059;

}
