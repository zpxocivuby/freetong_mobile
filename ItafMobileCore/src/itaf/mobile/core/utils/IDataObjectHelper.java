package itaf.mobile.core.utils;

import java.util.Collection;

import org.ksoap2.serialization.SoapObject;

/**
 * 数据属性复制辅助类.
 * 
 * @author Danne
 * 
 */
public interface IDataObjectHelper {
	/**
	 * 从传入数据对象中复制属性值到目标数据对象中,复制时只复制两对象中属性名，属性数据类型相同的属性值, 属性名相同而属性数据类型相异属性均不进行复制。
	 * 
	 * @param <T>
	 *            目标数据类型Class
	 * @param toClazz
	 *            目标数据Class名
	 * @param froms
	 *            原数据对象集合
	 * @return 参数toClazz指定的数据类型的新实例的集合
	 */
	public <T> Collection<T> copyProperties(Class<T> toClazz,
			Collection<?> froms);

	/**
	 * 从传入数据对象中复制属性值到目标数据对象中,复制时只复制两对象中属性名，属性数据类型相同的属性值, 属性名相同而属性数据类型相异属性均不进行复制。
	 * 
	 * @param <T>
	 *            目标数据类型Class
	 * @param toClazz
	 *            目标数据Class名
	 * @param from
	 *            原数据对象
	 * @return 参数toClazz指定的数据类型的新实例
	 */
	public <T> T copyProperties(Class<T> toClazz, Object from);

	/**
	 * 从传入数据对象中复制属性值到目标数据对象中,复制时只复制两对象中属性名，属性数据类型相同的属性值, 属性名相同而属性数据类型相异属性均不进行复制。
	 * 
	 * @param <T>
	 *            目标数据类型Class
	 * @param toClazz
	 *            目标数据Class名
	 * @param froms
	 *            原数据对象数组
	 * @return 参数toClazz指定的数据类型的新实例的数组
	 */
	public <T> Collection<T> copyProperties(Class<T> toClazz, Object[] froms);

	/**
	 * 从传入数据对象中复制属性值到目标数据对象中,复制时只复制两对象中属性名，属性数据类型相同的属性值, 属性名相同而属性数据类型相异属性均不进行复制。
	 * 
	 * @param to
	 *            目标数据对象
	 * @param from
	 *            原数据对象
	 * @throws Exception
	 */
	public void copyProperties(Object to, Object from);

	/**
	 * 从传入数据对象中复制属性值到目标数据对象中,复制时只复制两对象中属性名，属性数据类型相同的属性值, 属性名相同而属性数据类型相异属性均不进行复制。
	 * 
	 * @param <T>
	 *            目标数据类型Class
	 * @param toClazz
	 *            目标数据Class名
	 * @param from
	 *            原数据对象
	 * @return 参数toClazz指定的数据类型的新实例
	 */

	public <T> T copyPropertiesFromSoapObject(Class<T> class1,
			SoapObject iteratorObject);

	/**
	 * 从传入数据对象中复制属性值到目标数据对象中,复制时只复制两对象中属性名，属性数据类型相同的属性值, 属性名相同而属性数据类型相异属性均不进行复制。
	 * 
	 * @param <T>
	 *            目标数据类型Class
	 * @param toClazz
	 *            目标数据Class名
	 * @param from
	 *            原数据对象
	 * @return 参数toClazz指定的数据类型的新实例
	 */

	public void copyPropertiesFromSoapObject(Object to,
			SoapObject iteratorObject);

}
