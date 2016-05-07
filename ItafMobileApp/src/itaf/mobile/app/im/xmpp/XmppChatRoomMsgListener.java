package itaf.mobile.app.im.xmpp;

import org.jivesoftware.smack.packet.Message;

/**
 * 
 * @author
 * 
 *          该类是接收xmpp消息的回调处理类
 *         <p>
 *         需要进行消息显示的activity需要实现该接口，
 *         <p>
 *         并通过XmppChatRoomMsgListenerManager.addRefreshDataListener方法进行注册
 */
public interface XmppChatRoomMsgListener {

	void refreshChatRoomMsg(Message message);

}
