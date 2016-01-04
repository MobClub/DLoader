package m.dloader.impl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import m.dloader.DLoader;
import m.dloader.LoadSDKHandler;
import m.dloader.ReflectHelper;
import android.content.Context;
import android.os.Handler.Callback;
import android.os.Message;
import dalvik.system.DexClassLoader;

public class SMSSDK extends DLoader {
	public static final int RESULT_COMPLETE = -1;
	public static final int RESULT_ERROR = 0;
	
	public static final int EVENT_GET_SUPPORTED_COUNTRIES = 1;
	public static final int EVENT_GET_VERIFICATION_CODE = 2;
	public static final int EVENT_SUBMIT_VERIFICATION_CODE = 3;
	public static final int EVENT_GET_CONTACTS = 4;
	public static final int EVENT_SUBMIT_USER_INFO = 5;
	public static final int EVENT_GET_FRIENDS_IN_APP = 6;
	public static final int EVENT_GET_NEW_FRIENDS_COUNT = 7;
	
	private static Class<?> SMSSDK;

	public static void initSDK(final Context context, final String appkey, final String appSecrect) {
		initSDK(context, appkey, appSecrect, false);
	}
	
	public static void initSDK(final Context context, final String appkey, final String appSecrect, 
			final boolean warnOnReadContact) {
		load(context, new LoadSDKHandler() {
			public void onLoad(DexClassLoader loader) throws Throwable {
				SMSSDK = loader.loadClass("cn.smssdk.SMSSDK");
				Method initSDK = SMSSDK.getMethod("initSDK", Context.class, String.class, String.class, boolean.class);
				initSDK.setAccessible(true);
				initSDK.invoke(null, context.getApplicationContext(), appkey, appSecrect, warnOnReadContact);
			}
		});
	}
	
	public static class RegisterPage {
		private Object page;
		
		public RegisterPage() {
			try {
				Class<?> RegisterPage = dcLoader.loadClass("cn.smssdk.gui.RegisterPage");
				page = RegisterPage.newInstance();
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		
		public void setRegisterCallback(EventHandler eventHandler) {
			try {
				ReflectHelper.invokeInstanceMethod(page, "setRegisterCallback", eventHandler.getEnventHandler());
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		
		public void show(Context context) {
			try {
				ReflectHelper.invokeInstanceMethod(page, "show", context);
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		
		public void setOnSendMessageHandler(OnSendMessageHandler h) {
			try {
				ReflectHelper.invokeInstanceMethod(page, "setOnSendMessageHandler", h.getHandler());
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
	}
	
	public static class EventHandler implements Callback {
		private static final int MSG_ON_REGISTER = 1;
		private static final int MSG_BEFORE_EVENT = 2;
		private static final int MSG_AFTER_EVENT = 3;
		private static final int MSG_ON_UNREGISTER = 4;
		
		private Object enventHandler;
		
		public EventHandler() {
			try {
				Class<?> ReflectableEnventHandler = dcLoader.loadClass("cn.smssdk.ReflectableEnventHandler");
				enventHandler = ReflectableEnventHandler.newInstance();
				ReflectHelper.invokeInstanceMethod(enventHandler, "setOnRegisterCallback", MSG_ON_REGISTER, this);
				ReflectHelper.invokeInstanceMethod(enventHandler, "setBeforeEventCallback", MSG_BEFORE_EVENT, this);
				ReflectHelper.invokeInstanceMethod(enventHandler, "setAfterEventCallback", MSG_AFTER_EVENT, this);
				ReflectHelper.invokeInstanceMethod(enventHandler, "setOnUnregisterCallback", MSG_ON_UNREGISTER, this);
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		
		public final boolean handleMessage(Message msg) {
			switch (msg.what) {
				case MSG_ON_REGISTER: {
					onRegister();
				} break;
				case MSG_BEFORE_EVENT: {
					Object[] objs = (Object[]) msg.obj;
					int event = (Integer) objs[0];
					Object data = objs[1];
					beforeEvent(event, data);
				} break;
				case MSG_AFTER_EVENT: {
					Object[] objs = (Object[]) msg.obj;
					int event = (Integer) objs[0];
					int result = (Integer) objs[1];
					Object data = objs[2];
					afterEvent(event, result, data);
				} break;
				case MSG_ON_UNREGISTER: {
					onUnregister();
				} break;
			}
			return false;
		}
		
		public void onRegister() {
			
		}

		public void beforeEvent(int event, Object data) {
			
		}

		public void afterEvent(int event, int result, Object data) {
			
		}

		public void onUnregister() {
			
		}
		
		final Object getEnventHandler() {
			return enventHandler;
		}
	}
	
	public static class OnSendMessageHandler implements InvocationHandler {
		private Object onSendMessageHandler;
		
		public OnSendMessageHandler() {
			try {
				Class<?> DefaultOnSendMessageHandler = dcLoader.loadClass("cn.smssdk.DefaultOnSendMessageHandler");
				onSendMessageHandler = Proxy.newProxyInstance(DefaultOnSendMessageHandler.getClassLoader(), 
						DefaultOnSendMessageHandler.getInterfaces(), this);	
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		
		public Object getHandler() {
			return onSendMessageHandler;
		}

		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			if ("onSendMessage".equals(method.getName())) {
				return onSendMessage((String) args[0], (String) args[1]);
			}
			return method.invoke(onSendMessageHandler, args);
		}
		
		public boolean onSendMessage(String country, String phone) {
			return false;
		}
		
	}
	
}
