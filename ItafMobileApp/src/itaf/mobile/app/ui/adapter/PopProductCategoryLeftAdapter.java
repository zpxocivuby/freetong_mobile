package itaf.mobile.app.ui.adapter;

import itaf.framework.product.dto.BzProductCategoryDto;
import itaf.mobile.app.R;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
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
public class PopProductCategoryLeftAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<BzProductCategoryDto> content;
	private int selectedPosition = -1;

	static class ViewHolder {
		public TextView tv_pcla_content;
		public LinearLayout linear_pcla_layout;
	}

	public PopProductCategoryLeftAdapter(Context context,
			List<BzProductCategoryDto> content) {
		this.content = content;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return content.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.pop_category_left_adapter,
					parent, false);
			holder = new ViewHolder();
			holder.tv_pcla_content = (TextView) convertView
					.findViewById(R.id.tv_pcla_content);
			holder.linear_pcla_layout = (LinearLayout) convertView
					.findViewById(R.id.linear_pcla_layout);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// 设置选中效果
		if (selectedPosition == position) {
			holder.tv_pcla_content.setTextColor(Color.BLUE);
			holder.linear_pcla_layout.setBackgroundColor(Color.LTGRAY);
		} else {
			holder.tv_pcla_content.setTextColor(Color.WHITE);
			holder.linear_pcla_layout.setBackgroundColor(Color.TRANSPARENT);
		}

		holder.tv_pcla_content.setText(content.get(position).getCategoryName());
		holder.tv_pcla_content.setTextColor(Color.BLACK);
		return convertView;
	}

	public void setSelectedPosition(int position) {
		selectedPosition = position;
	}

}