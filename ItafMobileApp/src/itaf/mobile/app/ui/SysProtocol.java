package itaf.mobile.app.ui;

import itaf.mobile.app.R;
import itaf.mobile.app.ui.base.BaseUIActivity;
import itaf.mobile.app.util.OnClickListenerHelper;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

/**
 * 系统协议
 *
 *
 * @author
 *
 * @UpdateDate 2014年9月5日
 */
public class SysProtocol extends BaseUIActivity {

	// 返回
	private Button btn_sp_back;
	// 协议信息
	private TextView tv_sp_protocol;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sys_protocol);

		initPageAttribute();
	}

	private void initPageAttribute() {
		btn_sp_back = (Button) this.findViewById(R.id.btn_sp_back);
		btn_sp_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(SysProtocol.this));
		tv_sp_protocol = (TextView) this.findViewById(R.id.tv_sp_protocol);
		tv_sp_protocol
				.setText("您在用户注册页面点击“同意以下协议并注册”按钮后，即视为您已阅读、理解并同意本协议的全部内容，本协议即在您与移动身边之间产生法律效力，成为对双方均具有约束力的法律文件。");
	}
}
