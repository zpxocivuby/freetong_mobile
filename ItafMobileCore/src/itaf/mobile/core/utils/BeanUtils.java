package itaf.mobile.core.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

public class BeanUtils {
	// Need cache for reflection info later
	public static Object getProperty(Object bean, String name)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		Method getter = findGetter(bean, name);
		Object v = getter.invoke(bean, (Object[]) null);
		return v;
	}

	public static void setProperty(Object bean, String name, Object value)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		Method setter = findSetter(bean, name);
		Class<?> paraType = setter.getParameterTypes()[0];
		Object av = value;
		if (paraType.isInstance(value)) {
			av = value;
		} else {
			String text = value.toString();
			if (paraType.equals(Double.class) || paraType.equals(double.class)) {
				av = Double.parseDouble(text);
			} else if (paraType.equals(Integer.class)
					|| paraType.equals(int.class)) {
				av = Integer.parseInt(text);
			} else if (paraType.equals(Long.class)
					|| paraType.equals(long.class)) {
				av = Long.parseLong(text);
			} else if (Number.class.isAssignableFrom(paraType)) {
				av = Integer.parseInt(text);
			} else if (Boolean.class.isAssignableFrom(paraType)
					|| boolean.class.isAssignableFrom(paraType)) {
				if (value.getClass().equals(Boolean.class)
						|| value.getClass().equals(boolean.class)) {
					av = value;
				} else {
					av = Boolean.parseBoolean(text);
				}
			} else if(paraType.equals(Date.class)){
				av = DateUtil.parse(text, DateUtil.FORMAT_DATETIME_YYYYMMDDTHHMMSSZ);
			}else {
				if("anyType{}".equals(text)){
					text = "";
				}
				av = text;
			}
		}
		setter.invoke(bean, av);
	}

	private static Method findGetter(Object bean, String name)
			throws SecurityException, NoSuchMethodException {
		String upperName = StringHelper.capitalize(name);
		try {
			return bean.getClass().getMethod("get" + upperName, (Class[]) null);
		} catch (NoSuchMethodException e) {
			return bean.getClass().getMethod("is" + upperName, (Class[]) null);
		}
	}

	private static Method findSetter(Object bean, String name)
			throws SecurityException, NoSuchMethodException {
		String setterName = "set" + StringHelper.capitalize(name);
		for (Method m : bean.getClass().getMethods()) {
			if (setterName.equals(m.getName())) {
				return m;
			}
		}
		throw new NoSuchMethodException("Method " + name
				+ " not found in class " + bean.getClass());
	}
}
