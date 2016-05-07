package itaf.mobile.app.ui;

import itaf.mobile.app.R;
import itaf.mobile.app.ui.base.BaseUIActivity;
import itaf.mobile.app.ui.widget.ClipImageView;
import itaf.mobile.app.util.FileHelper;
import itaf.mobile.core.app.AppActivityManager;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SysClipPhoto extends BaseUIActivity {

	private ClipImageView imageView;

	private Button btn_scp_clip;

	private String filePath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sys_clip_photo);

		imageView = (ClipImageView) findViewById(R.id.src_pic);

		filePath = this.getIntent().getStringExtra("filePath");
		// 设置需要裁剪的图片
		imageView.setImageBitmap(getLoacalBitmap(filePath));

		btn_scp_clip = (Button) findViewById(R.id.btn_scp_clip);
		btn_scp_clip.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 此处获取剪裁后的bitmap
				Bitmap bitmap = imageView.clip();
				FileOutputStream fop = null;
				try {
					filePath = FileHelper.getAppCacheDir(SysClipPhoto.this)
							+ FileHelper.getSrcFileName(filePath);
					fop = new FileOutputStream(filePath);
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fop);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} finally {
					try {
						if (fop != null) {
							fop.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				Intent intent = new Intent(SysClipPhoto.this,
						SysPhotoPreview.class);
				intent.putExtra("filePath", filePath);
				setResult(Activity.RESULT_OK, intent);
				AppActivityManager.getInstance().finishActivity(
						SysClipPhoto.this);
			}
		});
	}
}
