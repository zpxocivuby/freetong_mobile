package itaf.mobile.app.ui;

import itaf.mobile.app.R;
import itaf.mobile.app.ui.base.BaseUIActivity;
import itaf.mobile.app.util.OnClickListenerHelper;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

/**
 * 关于
 * 
 * @author
 * 
 * @update 2013年9月5日
 */
public class SysAbout extends BaseUIActivity {

	private Button id_seta_back;
	private TextView tv_seta_version;
	private TextView tv_seta_update_content;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sys_about);
		id_seta_back = (Button) findViewById(R.id.btn_seta_back);
		id_seta_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(this));
		tv_seta_version = (TextView) findViewById(R.id.tv_seta_version);
		try {
			PackageInfo info = this.getPackageManager().getPackageInfo(
					this.getPackageName(), 0);
			if (info != null) {
				tv_seta_version.setText("当前版本：" + info.versionCode);
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		tv_seta_update_content = (TextView) findViewById(R.id.tv_seta_update_content);
		tv_seta_update_content.setText("1,修改了新建选题的样式！");
	}
}
