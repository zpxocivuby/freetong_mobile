package itaf.mobile.ds.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 用于传递List 传递的方法
 * <p>
 * Bundle bundle = new Bundle(); List<T> targetList = new ArrayList<T>();
 * ........ bundle.putSerializable(AppConstants.TRANSMIT_LIST, new
 * SerializableList<T>(targetList)); Intent intent = new Intent();
 * intent.putExtras(bundle); intent.setClass(a, b);
 * startActivityForResult(intent, 1);
 * </p>
 * 
 * 接收的方法
 * <p>
 * SerializableList<T> sList = (SerializableList<T>) getIntent()
 * .getSerializableExtra(AppConstants.TRANSMIT_LIST); List<T> target =
 * sList.getTarget();
 * </p>
 * 
 * @author
 * 
 * @update 2013年10月15日
 */
public class SerializableList<T> implements Serializable {

	private static final long serialVersionUID = -7367723579845645434L;

	public SerializableList(T obj) {
		this.target = new ArrayList<T>();
		this.target.add(obj);
	}

	public SerializableList(Collection<T> target) {
		this.target = target;
	}

	private Collection<T> target;

	public Collection<T> getTarget() {
		return target;
	}

	public void setTarget(Collection<T> target) {
		this.target = target;
	}

}
