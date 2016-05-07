package itaf.mobile.app.ui.adapter;

import itaf.framework.merchant.dto.BzServiceProviderTypeDto;
import itaf.mobile.app.R;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 分类适配器
 *
 *
 *
 * @author XINXIN
 *
 * @UpdateDate 2014年9月22日
 */
public class PopServiceProviderTypeRightAdapter extends BaseAdapter {

	private LayoutInflater layoutInflater;
	private List<BzServiceProviderTypeDto> content;

	static class ViewHolder {
		public TextView tv_pcra_content;
	}

	public PopServiceProviderTypeRightAdapter(Context context,
			List<BzServiceProviderTypeDto> content) {
		this.content = content;
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return content.size();
	}

	@Override
	public Object getItem(int position) {
		return getItem(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = layoutInflater.inflate(
					R.layout.pop_category_right_adapter, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.tv_pcra_content = (TextView) convertView
					.findViewById(R.id.tv_pcra_content);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tv_pcra_content.setText(content.get(position).getTypeName());
		viewHolder.tv_pcra_content.setTextColor(Color.BLACK);

		return convertView;
	}

}