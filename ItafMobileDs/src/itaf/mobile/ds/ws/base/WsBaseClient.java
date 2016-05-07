package itaf.mobile.ds.ws.base;

import itaf.framework.base.dto.WsPageResult;
import itaf.mobile.core.app.AppApplication;
import itaf.mobile.core.app.AppConfig;
import itaf.mobile.core.base._FakeX509TrustManager;
import itaf.mobile.core.exception.AppException;
import itaf.mobile.core.utils.StringHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.transport.HttpsTransportSE;

import android.util.Log;

public abstract class WsBaseClient<T> {

	// 连接超时毫秒数
	private static final int TIMEOUT = 10000;

	protected static final String NAMESPACE_BASE = "itaf.framework.ws.server.base";
	protected static final String NAMESPACE_SEARCH = "itaf.framework.ws.server.search";
	protected static final String NAMESPACE_PLATFORM = "itaf.framework.ws.server.platform";
	protected static final String NAMESPACE_PRODUCT = "itaf.framework.ws.server.product";
	protected static final String NAMESPACE_MERCHANT = "itaf.framework.ws.server.merchant";
	protected static final String NAMESPACE_CONSUMER = "itaf.framework.ws.server.consumer";
	protected static final String NAMESPACE_CART = "itaf.framework.ws.server.cart";
	protected static final String NAMESPACE_ORDER = "itaf.framework.ws.server.order";
	protected static final String NAMESPACE_IM = "itaf.framework.ws.server.im";
	protected static final String NAMESPACE_SECURITY = "itaf.framework.ws.server.security";
	protected static final String NAMESPACE_CERTIFICATE = "itaf.framework.ws.server.certificate";
	protected static final String NAMESPACE_MOBILE = "itaf.framework.ws.server.mobile";

	private String serverUrl;

	public WsBaseClient(String serviceUrl) {
		this.serverUrl = AppConfig.wsServerUrl + serviceUrl;
	}

	protected WsPageResult<T> callSoapMethod(SoapObject paramSo, int contentWhat) {
		SoapObject result = null;
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		Log.d("BaseWSClient", "xml:" + envelope.env);
		envelope.bodyOut = paramSo;
		envelope.dotNet = false;
		envelope.setOutputSoapObject(paramSo);
		HttpTransportSE ht = null;
		try {
			ht = this.createHttpTransportSE();
			ht.debug = true;
			Map<String, String> httpHeader = AppApplication.getInstance()
					.getHttpHeader();
			synchronized (httpHeader) {
				List<HeaderProperty> headers = new ArrayList<HeaderProperty>();
				if (StringHelper.isNotEmpty(httpHeader.get("set-cookie"))) {
					headers.add(new HeaderProperty("Cookie", httpHeader
							.get("set-cookie")));
				}
				headers.add(new HeaderProperty("Connection", "close"));
				List<?> callBackHeaders = ht.call("", envelope, headers);
				for (Object header : callBackHeaders) {
					HeaderProperty headerProperty = (HeaderProperty) header;
					String headerKey = headerProperty.getKey();
					String headerValue = headerProperty.getValue();
					if ("set-cookie".equalsIgnoreCase(headerKey)
							&& headerValue != null) {
						Log.d("base WS client callSoapMethod hearder key",
								headerKey + " : " + headerValue);
						String[] cookieValues = (headerValue.split(";"));
						if (cookieValues != null && cookieValues.length > 0) {
							httpHeader.put("set-cookie", cookieValues[0]);
						}
					}
				}
			}
			result = (SoapObject) envelope.bodyIn;
		} catch (Exception e) {
			Log.e(this.getClass().getName(),
					"HttpTransportSE.callSoapMethod()错误： " + e.getMessage());
			throw new AppException("创建HttpTransportSE失败", e);
		} finally {
			try {
				ht.getServiceConnection().disconnect();
				ht = null;
			} catch (IOException e) {
				Log.e(this.getClass().getName(),
						"HttpTransportSE.disconnect()错误： " + e.getMessage());
				throw new AppException("HttpTransportSE无法disconnect", e);
			}
		}
		return processCallBack(contentWhat, result);
	}

	protected abstract WsPageResult<T> processCallBack(int contentWhat,
			SoapObject result);

	/**
	 * 创建HttpTransportSE
	 * 
	 * @return HttpTransportSE
	 */
	private HttpTransportSE createHttpTransportSE() {
		HttpTransportSE result = null;
		try {
			// 支持HTTPS和http2种协议
			String protocolName = serverUrl.substring(0, 5);
			if ("https".equals(protocolName.toLowerCase(Locale.getDefault()))) {
				String host_port = serverUrl.substring(8);
				// 主机地址
				String host = "";
				// 端口
				String port = "443";
				if (host_port.indexOf(":") != -1) {
					host = host_port.substring(0, host_port.indexOf(":"));
					port = host_port.substring(host_port.indexOf(":") + 1,
							host_port.indexOf("/"));
				} else {
					host = host_port.substring(0, host_port.indexOf("/"));
				}
				String file = host_port.substring(host_port.indexOf("/"));
				result = new HttpsTransportSE(host, Integer.valueOf(port),
						file, TIMEOUT);
				_FakeX509TrustManager.allowAllSSL();
				System.setProperty("http.keepAlive", "false");
			} else {
				result = new HttpTransportSE(serverUrl, TIMEOUT);
			}
		} catch (Exception e) {
			Log.d(this.getClass().getName(),
					"创建HttpTransportSE失败:" + e.getMessage());
			throw new AppException("创建HttpTransportSE失败", e);
		}
		return result;
	}

}
