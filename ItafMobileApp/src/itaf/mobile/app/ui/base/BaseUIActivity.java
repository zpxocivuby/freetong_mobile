package itaf.mobile.app.ui.base;

import itaf.mobile.app.ui.custom.AppProgressDialog;
import itaf.mobile.app.util.GetpathFromURI4kitkat;
import itaf.mobile.core.app.AppActivityManager;
import itaf.mobile.core.app.AppApplication;
import itaf.mobile.core.app.AppServiceManager;
import itaf.mobile.core.base.BaseActivity;
import itaf.mobile.core.constant.AppConstants;
import itaf.mobile.core.utils.DateUtil;
import itaf.mobile.core.utils.StringHelper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TimePicker;

public class BaseUIActivity extends BaseActivity {

	// 加载中
	public static final String PD_LOADING = "加载中...";
	// 提交中
	public static final String PD_SUBMITTING = "提交中...";
	// 登陆中
	public static final String PD_LOGINING = "正在登录...";
	// 模态窗口
	private AppProgressDialog progressDialog;
	private GetpathFromURI4kitkat fromURI4kitkat;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		fromURI4kitkat = new GetpathFromURI4kitkat();
	}
	
	protected void showProgressDialog(String msg) {
		if (progressDialog == null) {
			progressDialog = new AppProgressDialog(this);
		}
		progressDialog.setMessage(StringHelper.trimToEmpty(msg));
		if (progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
		progressDialog.show();
	}

	protected void dismissProgressDialog() {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
	}

	protected String getImagePathFromAndroid(Uri selectedImage) {
		String[] filePathColumns = { MediaStore.Images.Media.DATA };
		Cursor cursor = this.getContentResolver().query(selectedImage,
				filePathColumns, null, null, null);
		cursor.moveToFirst();
		int columnIndex = cursor.getColumnIndex(filePathColumns[0]);
		String result = cursor.getString(columnIndex);
		cursor.close();
		return result;
	}
	protected String getImagePathFromUri4kitkat(final Context context,Uri selectedImage){
		return fromURI4kitkat.getPath(context, selectedImage);
	}

	// 获取窗口的宽度
	protected int getWidowWidth() {
		return getDisplayMetrics().widthPixels;
	}

	// 获取窗口的高度
	protected int getWidowHeight() {
		return getDisplayMetrics().heightPixels;
	}

	protected DisplayMetrics getDisplayMetrics() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm;
	}

	public int getPageSize() {
		return AppApplication.getInstance().getPageSize();
	}

	public AppApplication getAppApplication() {
		return (AppApplication) this.getApplication();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	protected String getTextViewToString(TextView target) {
		return target.getText() == null ? "" : target.getText().toString()
				.trim();
	}

	protected Long getTextViewToLong(TextView target, Long defaultValue) {
		String result = this.getTextViewToString(target);
		return "".equals(result) ? defaultValue : Long.valueOf(result);
	}

	protected Long getRatingBarToLong(RatingBar target) {
		int result = target.getProgress();
		return Long.valueOf(result);
	}

	protected Long getTextViewToLong(TextView target) {
		String result = this.getTextViewToString(target);
		return "".equals(result) ? null : Long.valueOf(result);
	}

	protected BigDecimal getTextViewToBigDecimal(TextView target) {
		return new BigDecimal(this.getTextViewToString(target).replaceAll(
				StringHelper.RMB, ""));
	}

	protected Integer getTextViewToInteger(TextView target) {
		String result = this.getTextViewToString(target);
		return "".equals(result) ? null : Integer.valueOf(result);
	}

	protected String getCheckBoxToString(CheckBox target) {
		return target.isChecked() ? AppConstants.SET_ON : AppConstants.SET_OFF;
	}

	protected int getCheckBoxToInteger(CheckBox target) {
		return target.isChecked() ? 1 : 0;
	}

	protected Long getCheckBoxToLong(CheckBox target) {
		return target.isChecked() ? 1L : 0L;
	}

	protected Long getRadioButtonToLong(RadioButton target) {
		return target.isChecked() ? 1L : 0L;
	}

	protected Date getTextViewToDate(TextView target) {
		return getTextViewToDate(target, DateUtil.FORMAT_DATE_DEFAULT);
	}

	protected Date getTextViewToDate(TextView target, String patten) {
		if (patten == null || patten.length() <= 0) {
			patten = DateUtil.FORMAT_DATE_DEFAULT;
		}
		return getTextViewToString(target).length() <= 0 ? null : DateUtil
				.parse(getTextViewToString(target), patten);
	}

	protected String processString(String target) {
		return StringHelper.isEmpty(target) ? "" : target;
	}

	protected String processDate(Date target) {
		return processDate(target, DateUtil.FORMAT_DATE_DEFAULT);
	}

	protected String processDate(Date target, String patten) {
		if (target == null) {
			return "";
		}
		return DateUtil.formatDate(target, patten);
	}

	protected static String processDateToHightLightTime(Date target) {
		if (target == null) {
			return "";
		}
		String targetDate = DateUtil.formatDate(target,
				DateUtil.FORMAT_DATE_YYYY_MM_DD);
		String now = DateUtil.formatDate(new Date(),
				DateUtil.FORMAT_DATE_YYYY_MM_DD);
		if (now.equals(targetDate)) {
			return DateUtil.formatDate(target, DateUtil.FORMAT_TIME_HH_MM_SS);
		}
		return targetDate;
	}

	protected OnClickListener showDatePickerDialog(final Context context,
			final TextView target) {
		return showDatePickerDialog(context, target,
				DateUtil.FORMAT_DATE_DEFAULT);
	}

	protected OnClickListener showDatePickerDialog(final Context context,
			final TextView target, final String patten) {
		return new OnClickListener() {
			public void onClick(View v) {
				Date date = getTextViewToDate(target, patten);
				if (date == null) {
					date = new Date();
				}
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				new DatePickerDialog(context, new OnDateSetListener() {
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						target.setText(dateFormat(year, monthOfYear + 1,
								dayOfMonth));
					}
				}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
						calendar.get(Calendar.DAY_OF_MONTH)).show();
			}
		};
	}

	protected OnClickListener showTimePickerDialog(final Context context,
			final TextView target) {
		return showTimePickerDialog(context, target, DateUtil.FORMAT_TIME_HH_MM);
	}

	protected OnClickListener showTimePickerDialog(final Context context,
			final TextView target, final String patten) {
		return new OnClickListener() {
			public void onClick(View v) {
				Date date = getTextViewToDate(target, patten);
				if (date == null) {
					date = new Date();
				}
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);

				new TimePickerDialog(context, new OnTimeSetListener() {
					public void onTimeSet(TimePicker view, int hourOfDay,
							int minute) {
						target.setText(timeFormat(hourOfDay, minute));
					}
				}, calendar.get(Calendar.HOUR_OF_DAY),
						calendar.get(Calendar.MINUTE), true).show();
			}
		};
	}

	private String timeFormat(int hour, int minute) {
		String result = "";
		if (hour < 10 && minute < 10) {
			result = "0" + hour + ":" + "0" + minute;
		} else if (hour < 10 && minute >= 10) {
			result = "0" + hour + ":" + minute;
		} else if (hour >= 10 && minute < 10) {
			result = hour + ":" + "0" + minute;
		} else {
			result = hour + ":" + minute;
		}
		return result;
	}

	private String dateFormat(int year, int month, int day) {
		String result = "";
		if (month < 10 && day < 10) {
			result = year + "-" + "0" + month + "-" + "0" + day;
		} else if (month < 10 && day >= 10) {
			result = year + "-" + "0" + month + "-" + day;
		} else if (month >= 10 && day < 10) {
			result = year + "-" + month + "-" + "0" + day;
		} else {
			result = year + "-" + month + "-" + day;
		}
		return result;
	}

	// 配合ScrollView展示ListView的全部内容
	protected void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		params.height += 5;// if without this statement,the listview will be a
							// little short
		listView.setLayoutParams(params);
	}

	// path转化为Bitmap给图片
	protected Bitmap getLoacalBitmap(String path) {
		try {
			FileInputStream fis = new FileInputStream(path);
			return BitmapFactory.decodeStream(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

}
