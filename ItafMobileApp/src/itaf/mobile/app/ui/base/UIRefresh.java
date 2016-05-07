package itaf.mobile.app.ui.base;

/**
 * UI刷新接口：UI重新渲染
 * 
 * @note 实现该接口的Activity需要到TaskManageService#
 *       mappingTaskActivity方法进行添加TaskId和Activity名称的映射
 * @author XININ
 * 
 * @updateDate 2013-5-30
 */
public interface UIRefresh {

	/**
	 * 刷新UI，通过args[0]判断需要刷新那个UI，通过args[1]获取新数据
	 * 
	 * @param args
	 *            args[0] taskId, args[1] list
	 * 
	 * 
	 */
	public void refresh(Object... args);

}
