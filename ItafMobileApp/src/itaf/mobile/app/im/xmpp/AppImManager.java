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

/**
 * IM管理类
 * 
 * 
 * @author
 * 
 * @update 2013年11月13日
 */
public class AppImManager {

	private static class SingletonHolder {
		static final AppImManager instance = new AppImManager();
	}

	public static AppImManager getInstance() {
		return SingletonHolder.instance;
	}

	public void exit() {
		// 关闭连接
		XmppConnectionManager.getInstance().closeConnection();
		// TODO 清理用户的在线状态
	}

}
