/**
 * Copyright 2015 Freetong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
