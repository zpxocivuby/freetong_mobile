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
package itaf.mobile.app.im.xmpp;

import org.jivesoftware.smack.SmackException.NoResponseException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;

import android.util.Log;

/**
 * VCard管理
 * 
 * 
 * @author
 * 
 * @updateDate 2014年1月7日
 */
public class XmppVCardManager {

	private XmppVCardManager() {
	}

	private static class SingletonHolder {
		static final XmppVCardManager INSTANCE = new XmppVCardManager();
	}

	public static XmppVCardManager getInstance() {
		return SingletonHolder.INSTANCE;
	}

	/**
	 * 获取用户的VCard信息
	 * 
	 * @param connection
	 * @param jid
	 * @return
	 * @throws XMPPException
	 */
	public VCard getVCardByJid(String jid) {
		VCard vcard = new VCard();
		try {
			if (XmppConnectionManager.getInstance().isLogin()) {
				vcard.load(XmppConnectionManager.getInstance().getConnection(),
						jid);
			}
		} catch (XMPPException e) {
			Log.e(this.getClass().getName(), "getUserVCard():" + e.getMessage());
		} catch (NoResponseException e) {

			e.printStackTrace();
		} catch (NotConnectedException e) {

			e.printStackTrace();
		}
		return vcard;
	}

	public String getNickname(String jid) {
		VCard vcard = getVCardByJid(jid);
		return vcard.getNickName();
	}

}
