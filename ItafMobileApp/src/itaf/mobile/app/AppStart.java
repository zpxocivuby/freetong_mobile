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
package itaf.mobile.app;

import itaf.mobile.app.ui.AppMain;
import itaf.mobile.app.ui.base.BaseUIActivity;
import itaf.mobile.app.ui.base.UIRefresh;
import itaf.mobile.core.app.AppActivityManager;
import itaf.mobile.core.constant.AppConstants;
import itaf.mobile.core.utils.NetWorkHelper;
import itaf.mobile.core.utils.StringHelper;
import itaf.mobile.ds.db.mobile.AppVersionDb;
import itaf.mobile.ds.domain.AppVersion;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

/**
 * APP启动Activity
 *
 *
 * @author
 *
 * @UpdateDate 2014年9月3日
 */
public class AppStart extends BaseUIActivity implements UIRefresh {

	private AppVersionDb appVersionDb = new AppVersionDb(AppStart.this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 取消标题
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.app_start);
		// 取消状态栏
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		ImageView iv_app_welcome = (ImageView) findViewById(R.id.iv_app_welcome);
		AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
		alphaAnimation.setDuration(1000);
		iv_app_welcome.startAnimation(alphaAnimation);
		alphaAnimation.setAnimationListener(new AnimationListener() {
			public void onAnimationStart(Animation animation) {
				checkNetwork();
			}

			public void onAnimationRepeat(Animation animation) {
				// do nothing
			}

			public void onAnimationEnd(Animation animation) {
				// do nothing
			}

		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 1:
			checkNetwork();
		}
	}

	private void checkNetwork() {
		// 没有网络退出
		if (NetWorkHelper.isNetworkConnected(AppStart.this)) {
			if (isFirstOpenApp()) {
				// 设置桌面快捷图标
				createDeskIco();
				AppVersion appVersion = new AppVersion();
				appVersion.setStatus(AppConstants.SET_ON);
				appVersion.setType(AppVersion.TYPE_FIRST_LOGIN);
				appVersionDb.saveOrUpdate(appVersion);
			}
			startActivity(new Intent(AppStart.this, AppMain.class));
			AppActivityManager.getInstance().finishActivity(AppStart.this);
		} else {
			AlertDialog.Builder dialog = new AlertDialog.Builder(AppStart.this);
			dialog.setTitle("当前没有连接网络").setMessage("是否对网络进行设置？");
			dialog.setPositiveButton("设置",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							Intent intent = null;
							if (android.os.Build.VERSION.SDK_INT > 10) {
								intent = new Intent(
										android.provider.Settings.ACTION_WIRELESS_SETTINGS);
							} else {
								intent = new Intent();
								ComponentName name = new ComponentName(
										"com.android.settings",
										"com.android.settings.WirelessSettings");
								intent.setComponent(name);
								intent.setAction("android.intent.action.VIEW");
							}
							startActivityForResult(intent, 1);
						}
					});
			dialog.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
							AppActivityManager.getInstance().finishActivity(
									AppStart.this);
						}
					}).show();
			return;
		}
	}

	// 是否第一次打开APP
	private boolean isFirstOpenApp() {
		// 调用数据库的时候同时会检测数据库版本是否更新，如果有更新会执行更新
		AppVersion result = appVersionDb
				.findFirstLogin(AppVersion.TYPE_FIRST_LOGIN);
		return result == null || StringHelper.isEmpty(result.getStatus());
	}

	/**
	 * 创建桌面快捷图标
	 */
	private void createDeskIco() {
		Intent sIntent = new Intent(this, this.getClass())
				.setAction(Intent.ACTION_MAIN);
		sIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		Intent installer = new Intent();
		// 不允许重复创建
		installer.putExtra("duplicate", false);
		// 需要现实的名称
		installer.putExtra(Intent.EXTRA_SHORTCUT_NAME,
				this.getString(R.string.str_am_logo_title));
		// 快捷图片
		installer.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
				Intent.ShortcutIconResource.fromContext(this,
						R.drawable.app_ico));
		// 点击快捷图片，运行的程序主入口
		installer.putExtra(Intent.EXTRA_SHORTCUT_INTENT, sIntent);
		installer.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
		// 发送广播
		this.sendBroadcast(installer);
	}

	@Override
	public void refresh(Object... args) {
		// do nothing, but can't delete.
	}

}
