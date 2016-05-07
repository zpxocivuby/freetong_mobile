package itaf.mobile.app.ui.custom;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 带文字的ImageButton
 * 
 * @author
 * 
 */
public class QuickMenuButton extends LinearLayout {

	private ImageView mButtonImage;
	private TextView mButtonText;

	public QuickMenuButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public QuickMenuButton(Context context, int imageResId, CharSequence text) {
		super(context);
		mButtonImage = new ImageView(context);
		mButtonText = new TextView(context);

		setImageResource(imageResId);
		// 获取壁纸返回值是Drawable
		// Drawable drawable = getResources().getDrawable(imageResId);
		// 将Drawable转化为Bitmap
		// Bitmap bitmap = ImageUtil.drawableToBitmap(drawable);
		// 获取圆角图片
		// Bitmap roundBitmap = ImageUtil.getRoundedCornerBitmap(bitmap, 20.0f);
		// mButtonImage.setImageBitmap(roundBitmap);
		setText(text);
		setTextColor(Color.parseColor("#FFFFFF"));
		// mButtonText.setTextAppearance(context, R.style.QuickMenuButtonText);
		// mButtonText.setPadding(0, 0, 0, 0);
		mButtonText.setGravity(Gravity.CENTER);
		// 设置本布局的属性
		setClickable(true); // 可点击
		setFocusable(true); // 可聚焦
		setOrientation(LinearLayout.VERTICAL); // 垂直布局
		// 首先添加Image，然后才添加Text
		// 添加顺序将会影响布局效果
		addView(mButtonImage);
		addView(mButtonText);
	}

	// ----------------public method-----------------------------
	/*
	 * setImageResource方法
	 */
	public void setImageResource(int resId) {
		mButtonImage.setImageResource(resId);
	}

	public void setText(CharSequence buttonText) {
		mButtonText.setText(buttonText);
	}

	/*
	 * setTextColor方法
	 */
	public void setTextColor(int color) {
		mButtonText.setTextColor(color);
	}

}
