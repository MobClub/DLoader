package m.dloader;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

public class DLoaderUIShell extends Activity {
	private static HashMap<String, Object> executors;
	private Object executor;
	public static int forceTheme;
	
	static {
		executors = new HashMap<String, Object>();
	}
	
	public static String registerExecutor(Object executor) {
		String launchTime = String.valueOf(System.currentTimeMillis());
		return registerExecutor(launchTime, executor);
	}
	
	public static String registerExecutor(String scheme, Object executor) {
		executors.put(scheme, executor);
		return scheme;
	}
	
	public void setTheme(int resid) {
		if (forceTheme > 0) {
			super.setTheme(forceTheme);
		} else {
			super.setTheme(resid);
		}
	}
	
	protected void onCreate(Bundle savedInstanceState) {
		Intent mIntent = getIntent();
		String launchTime = mIntent.getStringExtra("launch_time");
		String executorName = mIntent.getStringExtra("executor_name");
		
		executor = executors.remove(launchTime);
		if (executor == null) {
			String uriScheme = mIntent.getScheme();
			executor = executors.remove(uriScheme);
			if (executor == null) {
				RuntimeException t = new RuntimeException("Executor lost! launchTime = " + launchTime + ", executorName: " + executorName);
				t.printStackTrace();
				super.onCreate(savedInstanceState);
				finish();
				return;
			}
		}
		
//		try {
//			ResourcesWrapper res = new ResourcesWrapper(getResources());
//			ResourcesWrapper rwApp = (ResourcesWrapper) getApplicationContext().getResources();
//			for (Entry<String, Resources> ent : rwApp.getResourcesEntrySet()) {
//				res.registerResources(ent.getValue(), ent.getKey());
//			}
//			Object mBase = ReflectHelper.getInstanceField(this, "mBase");
//			ReflectHelper.setInstanceField(mBase, "mResources", res);
//			ReflectHelper.setInstanceField(this, "mResources", res);
//		} catch (Throwable t) {
//			t.printStackTrace();
//		}
		
		Log.i("DLoaderUIShell", "DLoaderUIShell found executor: " + executor.getClass());
		try {
			ReflectHelper.invokeInstanceMethod(executor, "setActivity", this);
		} catch (Throwable t) {
			t.printStackTrace();
		}
		super.onCreate(savedInstanceState);
		Log.d("DLoaderUIShell", executor.getClass().getSimpleName() + " onCreate");
		try {
			ReflectHelper.invokeInstanceMethod(executor, "onCreate");
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
	
//	private void addAssetPath(String path) {
//		try {
//			Resources base = getResources();
//			AssetManager am = base.getAssets();
//			Method addAssetPath = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
//			addAssetPath.invoke(am, path);
//		} catch (Throwable t) {
//			t.printStackTrace();
//		}
//	}
	
	public void setContentView(int layoutResID) {
		View contentView = LayoutInflater.from(this).inflate(layoutResID, null);
		setContentView(contentView);
	}
	
	public void setContentView(View view) {
		if (view == null) {
			return;
		}
		
		super.setContentView(view);
		if (executor != null) {
			try {
				ReflectHelper.invokeInstanceMethod(executor, "setContentView", view);
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
	}
	
	public void setContentView(View view, LayoutParams params) {
		if (view == null) {
			return;
		}
		
		if (params == null) {
			super.setContentView(view);
		} else {
			super.setContentView(view, params);
		}
		
		if (executor != null) {
			try {
				ReflectHelper.invokeInstanceMethod(executor, "setContentView", view);
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
	}
	
	protected void onNewIntent(Intent intent) {
		if (executor == null) {
			super.onNewIntent(intent);
		} else {
			try {
				ReflectHelper.invokeInstanceMethod(executor, "onNewIntent", intent);
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
	}
	
	protected void onStart() {
		if (executor != null) {
			Log.d("DLoaderUIShell", executor.getClass().getSimpleName() + " onStart");
			try {
				ReflectHelper.invokeInstanceMethod(executor, "onStart");
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		super.onStart();
	}
	
	protected void onResume() {
		if (executor != null) {
			Log.d("DLoaderUIShell", executor.getClass().getSimpleName() + " onResume");
			try {
				ReflectHelper.invokeInstanceMethod(executor, "onResume");
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		super.onResume();
	}
	
	protected void onPause() {
		if (executor != null) {
			Log.d("DLoaderUIShell", executor.getClass().getSimpleName() + " onPause");
			try {
				ReflectHelper.invokeInstanceMethod(executor, "onPause");
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		super.onPause();
	}
	
	protected void onStop() {
		if (executor != null) {
			Log.d("DLoaderUIShell", executor.getClass().getSimpleName() + " onStop");
			try {
				ReflectHelper.invokeInstanceMethod(executor, "onStop");
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		super.onStop();
	}
	
	protected void onRestart() {
		if (executor != null) {
			Log.d("DLoaderUIShell", executor.getClass().getSimpleName() + " onRestart");
			try {
				ReflectHelper.invokeInstanceMethod(executor, "onRestart");
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		super.onRestart();
	}
	
	protected void onDestroy() {
		if (executor != null) {
			try {
				ReflectHelper.invokeInstanceMethod(executor, "sendResult");
			} catch (Throwable t) {
				t.printStackTrace();
			}
			Log.d("DLoaderUIShell", executor.getClass().getSimpleName() + " onDestroy");
			try {
				ReflectHelper.invokeInstanceMethod(executor, "onDestroy");
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		super.onDestroy();
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (executor != null) {
			try {
				ReflectHelper.invokeInstanceMethod(executor, "onActivityResult", requestCode, resultCode, data);
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		boolean ret = false;
		if (executor != null) {
			try {
				ret = (Boolean) ReflectHelper.invokeInstanceMethod(executor, "onKeyEvent", keyCode, event);
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		return ret ? true : super.onKeyDown(keyCode, event);
	}
	
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		boolean ret = false;
		if (executor != null) {
			try {
				ret = (Boolean) ReflectHelper.invokeInstanceMethod(executor, "onKeyEvent", keyCode, event);
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		return ret ? true : super.onKeyUp(keyCode, event);
	}
	
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (executor != null) {
			try {
				ReflectHelper.invokeInstanceMethod(executor, "onConfigurationChanged", newConfig);
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
	}
	
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		if (executor != null) {
			try {
				ReflectHelper.invokeInstanceMethod(executor, "onRequestPermissionsResult", requestCode, permissions, grantResults);
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
	}
	
	public void finish() {
		if (executor != null) {
			boolean finishRes = false;
			try {
				finishRes = (Boolean) ReflectHelper.invokeInstanceMethod(executor, "onFinish");
			} catch (Throwable t) {
				t.printStackTrace();
			}
			if (finishRes) {
				return;
			}
		}
		super.finish();
	}
	
	public Object getExecutor() {
		return executor;
	}
	
}
