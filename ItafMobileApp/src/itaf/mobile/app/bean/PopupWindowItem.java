package itaf.mobile.app.bean;

import android.view.View.OnClickListener;

public class PopupWindowItem {

	private int postion;

	private String value;

	private String text;

	private OnClickListener onClickListener;

	public PopupWindowItem(String text, OnClickListener onClickListener) {
		this.text = text;
		this.onClickListener = onClickListener;
	}

	public PopupWindowItem(int postion, String text,
			OnClickListener onClickListener) {
		this.postion = postion;
		this.text = text;
		this.onClickListener = onClickListener;
	}

	public PopupWindowItem(int postion, String value, String text,
			OnClickListener onClickListener) {
		this.postion = postion;
		this.value = value;
		this.text = text;
		this.onClickListener = onClickListener;
	}

	public int getPostion() {
		return postion;
	}

	public void setPostion(int postion) {
		this.postion = postion;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public OnClickListener getOnClickListener() {
		return onClickListener;
	}

	public void setOnClickListener(OnClickListener onClickListener) {
		this.onClickListener = onClickListener;
	}

}
