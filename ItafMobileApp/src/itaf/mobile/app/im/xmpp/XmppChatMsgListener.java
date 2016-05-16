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

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;

/**
 * 
 * @author
 * 
 *          该类是接收xmpp消息的回调处理类
 *         <p>
 *         需要进行消息显示的activity需要实现该接口，
 *         <p>
 *         并通过xmppMessageMager.addRefreshDataListener方法进行注册
 */
public interface XmppChatMsgListener {

	void refreshChatMsg(Chat newchat, Message message);

}
