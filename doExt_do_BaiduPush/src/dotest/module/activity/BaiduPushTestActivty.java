package dotest.module.activity;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import core.DoServiceContainer;
import doext.implement.do_BaiduPush_Model;
import dotest.module.frame.debug.DoService;
/**
 * webview组件测试样例
 */
public class BaiduPushTestActivty extends DoTestActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void initModuleModel() throws Exception {
		this.model = new do_BaiduPush_Model();
	}

	@Override
	protected void doTestSyncMethod() {
		Map<String, String> _paras_back = new HashMap<String, String>();
        DoService.syncMethod(this.model, "startWork", _paras_back);
	}

	@Override
	protected void onEvent() {
		DoService.subscribeEvent(this.model, "bind", new DoService.EventCallBack() {
			@Override
			public void eventCallBack(String _data) {
				DoServiceContainer.getLogEngine().writeDebug("事件回调：" + _data);
			}
		});
		DoService.subscribeEvent(this.model, "notificationClicked", new DoService.EventCallBack() {
			@Override
			public void eventCallBack(String _data) {
				DoServiceContainer.getLogEngine().writeDebug("事件回调：" + _data);
			}
		});
		DoService.subscribeEvent(this.model, "message", new DoService.EventCallBack() {
			@Override
			public void eventCallBack(String _data) {
				DoServiceContainer.getLogEngine().writeDebug("事件回调：" + _data);
			}
		});
		DoService.subscribeEvent(this.model, "unbind", new DoService.EventCallBack() {
			@Override
			public void eventCallBack(String _data) {
				DoServiceContainer.getLogEngine().writeDebug("事件回调：" + _data);
			}
		});
	}


}
