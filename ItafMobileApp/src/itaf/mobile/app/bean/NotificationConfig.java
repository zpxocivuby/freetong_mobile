package itaf.mobile.app.bean;

import itaf.mobile.app.R;
import android.net.Uri;

/**
 * 通知配置信息
 * 
 * 
 * @author
 * 
 * @update 2013年12月13日
 */
public class NotificationConfig {

	// 通知图标
	private int icon;
	// 通知概要
	private String tickerText;
	// 通知时间
	private long when;
	// 自定义声音
	private Uri sound;
	// 自定义震动
	private long[] vibrate;
	// 自定义闪灯颜色
	private int ledARGB;
	// 自定义闪灯打开时间
	private int ledOnMS;
	// 自定义闪灯关闭时间
	private int ledOffMS;

	// 使用默认声音DefaultValue:true
	private boolean useDefaultSound;
	// 使用默认震动DefaultValue:true
	private boolean useDefaultVibrate;
	// 使用默认闪灯DefaultValue:false
	private boolean useDefaultLights;

	// 在通知栏上点击此通知后自动清除此通知DefaultValue:true
	private boolean useFlagAutoCancel;
	// 将此通知放到通知栏的"Ongoing"即"正在运行"组中DefaultValue:true
	private boolean useFlagOngoingEvent;
	// 表明在点击了通知栏中的"清除通知"后，此通知不清除DefaultValue:false
	private boolean useFlagNoClear;
	// 表示某个正在启动后台服务DefaultValue:false
	private boolean useFlagForegroundService;
	// 是否一直进行，比如音乐一直播放，知道用户响应DefaultValue:false
	private boolean useFlagInsistent;
	// 每次提示通知的时候会发出提示音或振动DefaultValue:false
	private boolean useFlagOnlyAlertOnce;
	// 每次提示通知的时候打开LED提示灯DefaultValue:false
	private boolean useFlagShowLights;

	public NotificationConfig() {
		this.icon = R.drawable.ic_launcher;
		this.tickerText = "CCTV通知";
		this.when = System.currentTimeMillis();
		this.useDefaultSound = true;
		this.useDefaultVibrate = true;
		this.useDefaultLights = false;
		this.useFlagAutoCancel = true;
		this.useFlagOngoingEvent = true;
		this.useFlagNoClear = false;
		this.useFlagForegroundService = false;
		this.useFlagInsistent = false;
		this.useFlagOnlyAlertOnce = false;
		this.useFlagShowLights = false;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public String getTickerText() {
		return tickerText;
	}

	public void setTickerText(String tickerText) {
		this.tickerText = tickerText;
	}

	public long getWhen() {
		return when;
	}

	public void setWhen(long when) {
		this.when = when;
	}

	public Uri getSound() {
		return sound;
	}

	public void setSound(Uri sound) {
		this.sound = sound;
	}

	public long[] getVibrate() {
		return vibrate;
	}

	public void setVibrate(long[] vibrate) {
		this.vibrate = vibrate;
	}

	public int getLedARGB() {
		return ledARGB;
	}

	public void setLedARGB(int ledARGB) {
		this.ledARGB = ledARGB;
	}

	public int getLedOnMS() {
		return ledOnMS;
	}

	public void setLedOnMS(int ledOnMS) {
		this.ledOnMS = ledOnMS;
	}

	public int getLedOffMS() {
		return ledOffMS;
	}

	public void setLedOffMS(int ledOffMS) {
		this.ledOffMS = ledOffMS;
	}

	public boolean isUseDefaultSound() {
		return useDefaultSound;
	}

	public void setUseDefaultSound(boolean useDefaultSound) {
		this.useDefaultSound = useDefaultSound;
	}

	public boolean isUseDefaultVibrate() {
		return useDefaultVibrate;
	}

	public void setUseDefaultVibrate(boolean useDefaultVibrate) {
		this.useDefaultVibrate = useDefaultVibrate;
	}

	public boolean isUseDefaultLights() {
		return useDefaultLights;
	}

	public void setUseDefaultLights(boolean useDefaultLights) {
		this.useDefaultLights = useDefaultLights;
	}

	public boolean isUseFlagAutoCancel() {
		return useFlagAutoCancel;
	}

	public void setUseFlagAutoCancel(boolean useFlagAutoCancel) {
		this.useFlagAutoCancel = useFlagAutoCancel;
	}

	public boolean isUseFlagOngoingEvent() {
		return useFlagOngoingEvent;
	}

	public void setUseFlagOngoingEvent(boolean useFlagOngoingEvent) {
		this.useFlagOngoingEvent = useFlagOngoingEvent;
	}

	public boolean isUseFlagNoClear() {
		return useFlagNoClear;
	}

	public void setUseFlagNoClear(boolean useFlagNoClear) {
		this.useFlagNoClear = useFlagNoClear;
	}

	public boolean isUseFlagForegroundService() {
		return useFlagForegroundService;
	}

	public void setUseFlagForegroundService(boolean useFlagForegroundService) {
		this.useFlagForegroundService = useFlagForegroundService;
	}

	public boolean isUseFlagInsistent() {
		return useFlagInsistent;
	}

	public void setUseFlagInsistent(boolean useFlagInsistent) {
		this.useFlagInsistent = useFlagInsistent;
	}

	public boolean isUseFlagOnlyAlertOnce() {
		return useFlagOnlyAlertOnce;
	}

	public void setUseFlagOnlyAlertOnce(boolean useFlagOnlyAlertOnce) {
		this.useFlagOnlyAlertOnce = useFlagOnlyAlertOnce;
	}

	public boolean isUseFlagShowLights() {
		return useFlagShowLights;
	}

	public void setUseFlagShowLights(boolean useFlagShowLights) {
		this.useFlagShowLights = useFlagShowLights;
	}

}
