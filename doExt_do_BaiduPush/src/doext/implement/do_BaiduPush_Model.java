package doext.implement;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;

import core.DoServiceContainer;
import core.helper.DoJsonHelper;
import core.interfaces.DoIScriptEngine;
import core.object.DoInvokeResult;
import core.object.DoSingletonModule;
import doext.define.do_BaiduPush_IMethod;

/**
 * 自定义扩展SM组件Model实现，继承DoSingletonModule抽象类，并实现do_BaiduPush_IMethod接口方法；
 * #如何调用组件自定义事件？可以通过如下方法触发事件：
 * this.model.getEventCenter().fireEvent(_messageName, jsonResult);
 * 参数解释：@_messageName字符串事件名称，@jsonResult传递事件参数对象；
 * 获取DoInvokeResult对象方式new DoInvokeResult(this.getUniqueKey());
 */
public class do_BaiduPush_Model extends DoSingletonModule implements do_BaiduPush_IMethod {

	public do_BaiduPush_Model() throws Exception {
		super();
	}

	/**
	 * 同步方法，JS脚本调用该组件对象方法时会被调用，可以根据_methodName调用相应的接口实现方法；
	 * 
	 * @_methodName 方法名称
	 * @_dictParas 参数（K,V）
	 * @_scriptEngine 当前Page JS上下文环境对象
	 * @_invokeResult 用于返回方法结果对象
	 */
	@Override
	public boolean invokeSyncMethod(String _methodName, JSONObject _dictParas, DoIScriptEngine _scriptEngine, DoInvokeResult _invokeResult) throws Exception {
		if ("startWork".equals(_methodName)) {
			startWork(_dictParas, _scriptEngine, _invokeResult);
			return true;
		}
		if ("stopWork".equals(_methodName)) {
			stopWork(_dictParas, _scriptEngine, _invokeResult);
			return true;
		}
		return super.invokeSyncMethod(_methodName, _dictParas, _scriptEngine, _invokeResult);
	}

	/**
	 * 异步方法（通常都处理些耗时操作，避免UI线程阻塞），JS脚本调用该组件对象方法时会被调用， 可以根据_methodName调用相应的接口实现方法；
	 * 
	 * @_methodName 方法名称
	 * @_dictParas 参数（K,V）
	 * @_scriptEngine 当前page JS上下文环境
	 * @_callbackFuncName 回调函数名 #如何执行异步方法回调？可以通过如下方法：
	 *                    _scriptEngine.callback(_callbackFuncName,
	 *                    _invokeResult);
	 *                    参数解释：@_callbackFuncName回调函数名，@_invokeResult传递回调函数参数对象；
	 *                    获取DoInvokeResult对象方式new
	 *                    DoInvokeResult(this.getUniqueKey());
	 */
	@Override
	public boolean invokeAsyncMethod(String _methodName, JSONObject _dictParas, DoIScriptEngine _scriptEngine, String _callbackFuncName) throws Exception {
		// ...do something
		if ("setTags".equals(_methodName)) {
			this.setTags(_dictParas, _scriptEngine, _callbackFuncName);
			return true;
		}
		if ("removeTags".equals(_methodName)) {
			this.removeTags(_dictParas, _scriptEngine, _callbackFuncName);
			return true;
		}
		return super.invokeAsyncMethod(_methodName, _dictParas, _scriptEngine, _callbackFuncName);
	}

	/**
	 * Push服务初始化及绑定；
	 * 
	 * @_dictParas 参数（K,V），可以通过此对象提供相关方法来获取参数值（Key：为参数名称）；
	 * @_scriptEngine 当前Page JS上下文环境对象
	 * @_invokeResult 用于返回方法结果对象
	 */
	@Override
	public void startWork(JSONObject _dictParas, DoIScriptEngine _scriptEngine, DoInvokeResult _invokeResult) throws Exception {
		Context context = DoServiceContainer.getPageViewFactory().getAppContext();
		PushManager.startWork(context, PushConstants.LOGIN_TYPE_API_KEY, Utils.getMetaValue(context, "do_baidupush_api_key"));
	}

	/**
	 * 停止Push服务；
	 * 
	 * @_dictParas 参数（K,V），可以通过此对象提供相关方法来获取参数值（Key：为参数名称）；
	 * @_scriptEngine 当前Page JS上下文环境对象
	 * @_invokeResult 用于返回方法结果对象
	 */
	@Override
	public void stopWork(JSONObject _dictParas, DoIScriptEngine _scriptEngine, DoInvokeResult _invokeResult) throws Exception {
		PushManager.stopWork(DoServiceContainer.getPageViewFactory().getAppContext());
	}

	@Override
	public void setTags(JSONObject _dictParas, DoIScriptEngine _scriptEngine, String _callbackFuncName) throws Exception {
		JSONArray _jsonArray = DoJsonHelper.getJSONArray(_dictParas, "tag");
		PushManager.setTags(DoServiceContainer.getPageViewFactory().getAppContext(), getTags(_jsonArray));
	}

	@Override
	public void removeTags(JSONObject _dictParas, DoIScriptEngine _scriptEngine, String _callbackFuncName) throws Exception {
		JSONArray _jsonArray = DoJsonHelper.getJSONArray(_dictParas, "tag");
		PushManager.delTags(DoServiceContainer.getPageViewFactory().getAppContext(), getTags(_jsonArray));
	}

	private List<String> getTags(JSONArray _jsonArray) {
		List<String> tags = null;
		if (_jsonArray != null && _jsonArray.length() > 0) {
			String list = _jsonArray.toString();
			list = list.replace("]", "");
			list = list.replace("[", "");
			list = list.replace("\"", "");
			tags = Utils.getTagsList(list);
		}

		return tags;
	}
}