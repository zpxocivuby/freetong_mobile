package itaf.mobile.app.ui;

import itaf.mobile.app.R;
import itaf.mobile.app.ui.adapter.AbstractBaseAdapter;
import itaf.mobile.app.ui.base.BaseUIActivity;
import itaf.mobile.app.util.FileHelper;
import itaf.mobile.core.app.AppActivityManager;
import itaf.mobile.core.utils.StringHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 选择上传的文件
 * 
 * @author
 * 
 * @update 2013年11月06日
 */
public class SysFileBrowser extends BaseUIActivity {

	private LinearLayout linear_sufb_layout;
	private Button btn_sufb_back;
	private ListView lv_sufb_uploadfile;
	private FileBrowserAdapter fileBrowserAdapter;

	private List<Map<String, Object>> fileListViews;
	private String currFilePath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sys_file_browser);

		linear_sufb_layout = (LinearLayout) findViewById(R.id.linear_sufb_layout);
		linear_sufb_layout.setLayoutParams(new LinearLayout.LayoutParams(
				(int) (getWidowWidth() * 0.9), (int) (getWidowHeight() * 0.8)));

		btn_sufb_back = (Button) findViewById(R.id.btn_sufb_back);
		btn_sufb_back.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				setResult(RESULT_CANCELED, intent);
				AppActivityManager.getInstance().finishActivity(
						SysFileBrowser.this);
			}
		});

		Intent intent = this.getIntent();
		currFilePath = intent.getData().getPath();
		if (StringHelper.isEmpty(currFilePath)) {
			currFilePath = Environment.getExternalStorageDirectory().getPath();
		}
		lv_sufb_uploadfile = (ListView) findViewById(R.id.lv_sufb_uploadfile);
		fileListViews = loadFileList();
		fileBrowserAdapter = new FileBrowserAdapter(this, fileListViews);
		lv_sufb_uploadfile.setAdapter(fileBrowserAdapter);
		lv_sufb_uploadfile
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						listItemClick(position);
					}
				});
	}

	private List<Map<String, Object>> loadFileList() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		File f = new File(currFilePath);
		File[] files = f.listFiles();

		if (!currFilePath.equals(Environment.getExternalStorageDirectory()
				.getPath())) {
			map = new HashMap<String, Object>();
			map.put("title", "Back to ../");
			map.put("info", f.getParent());
			map.put("img", R.drawable.file_folder);
			list.add(map);
		}
		if (files == null || files.length <= 0) {
			return list;
		}
		for (File file : files) {
			map = new HashMap<String, Object>();
			map.put("title", file.getName());
			map.put("info", file.getPath());
			if (file.isDirectory() && !file.isHidden()
					&& file.listFiles() != null) {
				map.put("img", R.drawable.file_folder);
				list.add(map);
			} else if (file.isFile()) {
				if (FileHelper.isSupportFileExt(FileHelper.processFileExt(file
						.getName()))) {
					map.put("img", R.drawable.file_doc);
					list.add(map);
				}
			}
		}
		return list;
	}

	protected void listItemClick(int position) {
		if ((Integer) fileListViews.get(position).get("img") == R.drawable.file_folder) {
			currFilePath = (String) fileListViews.get(position).get("info");
			fileListViews = loadFileList();
			FileBrowserAdapter newAdapter = new FileBrowserAdapter(this,
					fileListViews);
			lv_sufb_uploadfile.setAdapter(newAdapter);
		} else {
			finishWithResult((String) fileListViews.get(position).get("info"));
		}
	}

	private void finishWithResult(String path) {
		Intent intent = new Intent();
		Uri startDir = Uri.fromFile(new File(path));
		intent.setDataAndType(startDir, "*/*");
		setResult(RESULT_OK, intent);
		AppActivityManager.getInstance().finishActivity(SysFileBrowser.this);
	}

	public class FileBrowserAdapter extends AbstractBaseAdapter {
		private List<Map<String, Object>> listItems;
		private LayoutInflater mInflater;

		public FileBrowserAdapter(Context context,
				List<Map<String, Object>> listItems) {
			this.mInflater = LayoutInflater.from(context);
			this.listItems = listItems;
		}

		public final class ViewHolder {
			public ImageView img;
			public TextView title;
			public TextView info;
		}

		public int getCount() {
			return listItems.size();
		}

		public Object getItem(int position) {
			return this.listItems.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = mInflater.inflate(
						R.layout.sys_file_browser_adapter, parent, false);
				holder.img = (ImageView) convertView.findViewById(R.id.img);
				holder.title = (TextView) convertView.findViewById(R.id.title);
				holder.info = (TextView) convertView.findViewById(R.id.info);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.img.setBackgroundResource((Integer) fileListViews.get(
					position).get("img"));
			holder.title.setText((String) fileListViews.get(position).get(
					"title"));
			holder.info.setText((String) fileListViews.get(position)
					.get("info"));
			return convertView;
		}
	}

}
