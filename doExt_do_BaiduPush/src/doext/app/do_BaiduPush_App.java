package doext.app;
import com.baidu.frontia.FrontiaApplication;

import android.content.Context;
import core.interfaces.DoIAppDelegate;

/**
 * APP启动的时候会执行onCreate方法；
 *
 */
public class do_BaiduPush_App implements DoIAppDelegate {

	private static do_BaiduPush_App instance;
	
	private do_BaiduPush_App(){
		
	}
	
	public static do_BaiduPush_App getInstance() {
		if(instance == null){
			instance = new do_BaiduPush_App();
		}
		return instance;
	}
	
	@Override
	public void onCreate(Context context) {
		FrontiaApplication.initFrontiaApplication(context);
	}
	
	public String getModuleTypeID() {
		return "do_BaiduPush";
	}
}
