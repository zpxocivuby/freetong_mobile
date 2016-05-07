/**
 * 
 */
package itaf.mobile.core.utils;


/**
 * @author
 * 
 */
public class DataObjectHelper {

	public static IDataObjectHelper getInstance() {
		return new CglibDataObjectHelper();

	}
}
