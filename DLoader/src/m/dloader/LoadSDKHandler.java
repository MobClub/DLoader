package m.dloader;

import dalvik.system.DexClassLoader;

public interface LoadSDKHandler {

	public void onLoad(DexClassLoader loader) throws Throwable;
	
}
