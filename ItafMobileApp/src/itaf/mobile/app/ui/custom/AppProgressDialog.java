package itaf.mobile.app.ui.custom;

import itaf.mobile.app.R;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

public class AppProgressDialog extends ProgressDialog {

	private Context mContent;

	public AppProgressDialog(Context context) {
		super(context, R.style.app_progress_dialog_theme);
		mContent = context;
	}

	public AppProgressDialog(Context context, int theme) {
		super(context, theme);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setIndeterminateDrawable(mContent.getResources().getDrawable(
				R.drawable.progress_loader));
		this.setCanceledOnTouchOutside(false);
	}

	public static AppProgressDialog show(Context context, String message) {
		AppProgressDialog dialog = new AppProgressDialog(context);
		dialog.setMessage(message);
		dialog.show();
		return dialog;
	}
}
