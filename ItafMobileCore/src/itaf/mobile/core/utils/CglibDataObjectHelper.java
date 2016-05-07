package itaf.mobile.core.utils;

import itaf.mobile.core.exception.AppException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.ksoap2.serialization.SoapObject;

import android.util.Log;

/**
 * A default {@link IDataObjectHelper} implementation class that uses CGLib as
 * bean properties copy utility.
 * <p>
 * User may sub-class from this to extends his own
 * {@link #copyProperties(Object, Object)} logic.
 * 
 * @author
 * 
 */

public class CglibDataObjectHelper implements IDataObjectHelper {

	@SuppressWarnings("rawtypes")
	private void cgLibBeanCopyier(Object target, Object source) {
		Class class1 = target.getClass();

		Field[] fields = class1.getDeclaredFields();

		for (int i = 0; i < fields.length; i++) {
			fields[i].setAccessible(true);
			try {
				BeanUtils.setProperty(target, fields[i].getName(),
						BeanUtils.getProperty(source, fields[i].getName()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private <T> T cgLibSoapBeanCopyier(T target, SoapObject source) {

		Method[] methods = target.getClass().getMethods();

		if (source != null && source.getPropertyCount() > 0) {

			for (Method method : methods) {
				if (method.getName().startsWith("set")) {
					String propertiseName = method.getName().substring(3,
							method.getName().length());
					propertiseName = StringHelper.uncapitalize(propertiseName);
					try {
						BeanUtils.setProperty(target, propertiseName,
								source.getProperty(propertiseName));
					} catch (Exception e) {
						Log.d("BaseWSClient", "java bean属性 设置异常 ，目标对象可能没有该属性信息"
								+ e.getMessage());
					}
				}
			}

		}
		return target;

	}

	@SuppressWarnings("unchecked")
	public <T> Collection<T> copyProperties(Class<T> toClazz,
			Collection<?> froms) {
		if (froms == null) {
			return null;
		}
		Collection<T> toList = null;
		try {
			toList = froms.getClass().newInstance();
			if (froms instanceof List) {
				toList = new ArrayList<T>();
			} else if (froms instanceof Set) {
				toList = new HashSet<T>();
			}

			for (Object obj : froms) {
				T to = copyProperties(toClazz, obj);
				toList.add(to);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return toList;
	}

	public <T> T copyProperties(Class<T> toClazz, Object from) {
		if (from == null) {
			return null;
		}
		T to = null;
		try {
			to = toClazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppException("SE0015");
		}
		copyProperties(to, from);
		return to;
	}

	public <T> Collection<T> copyProperties(Class<T> toClazz, Object[] froms) {
		if (froms == null)
			return null;
		ArrayList<T> toList = new ArrayList<T>();
		for (Object obj : froms) {
			T to = copyProperties(toClazz, obj);
			toList.add(to);
		}
		return toList;
	}

	public void copyProperties(Object to, Object from) {
		if (from == null) {
			return;
		}
		cgLibBeanCopyier(from, to);
	}

	public <T> T copyPropertiesFromSoapObject(Class<T> toClazz,
			SoapObject iteratorObject) {
		if (iteratorObject == null) {
			return null;
		}
		T to = null;
		try {
			to = toClazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppException("SE0015");
		}
		cgLibSoapBeanCopyier(to, iteratorObject);
		return to;
	}

	public void copyPropertiesFromSoapObject(Object to,
			SoapObject iteratorObject) {
		if (iteratorObject == null) {
			return;
		}
		cgLibSoapBeanCopyier(to, iteratorObject);

	}

}
