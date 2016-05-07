package itaf.mobile.app.ui;

import itaf.mobile.app.R;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

public class SysPhotoPreview extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.sys_photo_preview);
		setTitle("预览");

		ImageView imageView = (ImageView) findViewById(R.id.preview);
		byte[] bis = getIntent().getByteArrayExtra("bitmap");
		Bitmap bitmap = BitmapFactory.decodeByteArray(bis, 0, bis.length);
		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
		}
	}
}
