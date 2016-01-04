package m.dloader.impl;

import java.lang.reflect.Method;
import java.util.HashMap;

import m.dloader.DLoader;
import m.dloader.LoadSDKHandler;
import m.dloader.ReflectHelper;
import android.content.Context;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import dalvik.system.DexClassLoader;

public class ShareSDK extends DLoader {
	private static Class<?> ShareSDK;
	private static Class<?> Platform;
	
	public static void initSDK(Context context) {
		initSDK(context, null);
	}
	
	public static void initSDK(final Context context, final String appkey) {
		load(context, new LoadSDKHandler() {
			public void onLoad(DexClassLoader loader) throws Throwable {
				ShareSDK = loader.loadClass("cn.sharesdk.framework.ShareSDK");
				if (appkey == null) {
					Method initSDK = ShareSDK.getMethod("initSDK", Context.class);
					initSDK.setAccessible(true);
					initSDK.invoke(null, context);
				} else {
					Method initSDK = ShareSDK.getMethod("initSDK", Context.class, String.class);
					initSDK.setAccessible(true);
					initSDK.invoke(null, context, appkey);
				}
				
				Platform = loader.loadClass("cn.sharesdk.framework.Platform");
			}
		});
	}
	
	public static Platform[] getPlatformList() {
		try {
			Method getPlatformList = ShareSDK.getMethod("getPlatformList");
			getPlatformList.setAccessible(true);
			Object[] ps = (Object[]) getPlatformList.invoke(null);
			if (ps != null) {
				Platform[] platfroms = new Platform[ps.length];
				for (int i = 0; i < platfroms.length; i++) {
					platfroms[i] = new Platform(ps[i]);
				}
				return platfroms;
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return null;
	}
	
	public static Platform getPlatform(String name) {
		try {
			Method getPlatform = ShareSDK.getMethod("getPlatform", String.class);
			getPlatform.setAccessible(true);
			Object p = getPlatform.invoke(null, name);
			if (p != null) {
				return new Platform(p);
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return null;
	}
	
	public static int platformNameToId(String name) {
		try {
			Method platformNameToId = ShareSDK.getMethod("platformNameToId", String.class);
			platformNameToId.setAccessible(true);
			return (Integer) platformNameToId.invoke(null, name);
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return 0;
	}
	
	public static String platformIdToName(int id) {
		try {
			Method platformIdToName = ShareSDK.getMethod("platformIdToName", int.class);
			platformIdToName.setAccessible(true);
			return (String) platformIdToName.invoke(null, id);
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return null;
	}
	
	public static class Platform implements Callback {
		public static final int ACTION_AUTHORIZING = 1;
		public static final int ACTION_GETTING_FRIEND_LIST = 2;
		public static final int ACTION_SENDING_DIRECT_MESSAGE = 5;
		public static final int ACTION_FOLLOWING_USER = 6;
		public static final int ACTION_TIMELINE = 7;
		public static final int ACTION_USER_INFOR = 8;
		public static final int ACTION_SHARE = 9;
		protected static final int ACTION_GETTING_BILATERAL_LIST = 10;
		protected static final int ACTION_GETTING_FOLLOWER_LIST = 11;
		protected static final int ACTION_CUSTOMER = 655360;
		public static final int CUSTOMER_ACTION_MASK = 65535;
		
		public static final int SHARE_TEXT = 1;
		public static final int SHARE_IMAGE = 2;
		public static final int SHARE_WEBPAGE = 4;
		public static final int SHARE_MUSIC = 5;
		public static final int SHARE_VIDEO = 6;
		public static final int SHARE_APPS = 7;
		public static final int SHARE_FILE = 8;
		public static final int SHARE_EMOJI = 9;
		
		private static final int MSG_ON_COMP = 1;
		private static final int MSG_ON_CANC = 2;
		private static final int MSG_ON_ERRO = 3;
		
		private Object platform;
		private PlatformActionListener pa;
		
		private Platform(Object platform) {
			this.platform = platform;
		}
		
		public String getName() {
			try {
				Method getName = Platform.getMethod("getName");
				getName.setAccessible(true);
				return (String) getName.invoke(platform);
			} catch (Throwable t) {
				t.printStackTrace();
			}
			return null;
		}
		
		public void setPlatformActionListener(PlatformActionListener pa) {
			try {
				this.pa = pa;
				Class<?> ReflectablePlatformActionListener = dcLoader.loadClass("cn.sharesdk.framework.ReflectablePlatformActionListener");
				Object rpa = ReflectablePlatformActionListener.newInstance();
				Callback cb = new Callback() {
					public boolean handleMessage(Message msg) {
						Handler handler = new Handler(Looper.getMainLooper(), Platform.this);
						handler.sendMessage(msg);
						return false;
					}
				};
				
				String[] mthNames = new String[] {
						"setOnCompleteCallback",
						"setOnCancelCallback",
						"setOnErrorCallback"
				};
				int[] msgs = new int[] {
						MSG_ON_COMP,
						MSG_ON_CANC,
						MSG_ON_ERRO
				};
				for (int i = 0; i < 3; i++) {
					Method mth = ReflectablePlatformActionListener.getMethod(mthNames[i], int.class, Callback.class);
					mth.setAccessible(true);
					mth.invoke(rpa, msgs[i], cb);
				}

				Class<?> PlatformActionListener = dcLoader.loadClass("cn.sharesdk.framework.PlatformActionListener");
				Method mth = Platform.getMethod("setPlatformActionListener", PlatformActionListener);
				mth.setAccessible(true);
				mth.invoke(platform, rpa);
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		
		public boolean isValid() {
			try {
				Method isValid = Platform.getMethod("isValid");
				isValid.setAccessible(true);
				return (Boolean) isValid.invoke(platform);
			} catch (Throwable t) {
				t.printStackTrace();
			}
			return false;
		}
		
		public void SSOSetting(boolean disable) {
			try {
				Method SSOSetting = Platform.getMethod("SSOSetting", boolean.class);
				SSOSetting.setAccessible(true);
				SSOSetting.invoke(platform, disable);
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		
		public void share(ShareParams sp) {
			try {
				Method share = Platform.getMethod("share", sp.getShareParams().getClass());
				share.setAccessible(true);
				share.invoke(platform, sp.getShareParams());
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		
		public void removeAccount() {
			try {
				Method removeAccount = Platform.getMethod("removeAccount");
				removeAccount.setAccessible(true);
				removeAccount.invoke(platform);
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		
		public PlatformDb getDb() {
			try {
				Method getDb = Platform.getMethod("getDb");
				getDb.setAccessible(true);
				Object db = getDb.invoke(platform);
				if (db != null) {
					return new PlatformDb(db);
				}
			} catch (Throwable t) {
				t.printStackTrace();
			}
			return null;
		}
		
		public void showUser(String account) {
			try {
				Method showUser = Platform.getMethod("showUser", String.class);
				showUser.setAccessible(true);
				showUser.invoke(platform, account);
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		
		public boolean handleMessage(Message msg) {
			if (pa != null) {
				switch (msg.what) {
					case MSG_ON_COMP: {
						Object[] objs = (Object[]) msg.obj;
						int action = (Integer) objs[1];
						@SuppressWarnings("unchecked")
						HashMap<String, Object> res = (HashMap<String, Object>) objs[2];
						pa.onComplete(this, action, res);
					} break;
					case MSG_ON_CANC: {
						Object[] objs = (Object[]) msg.obj;
						int action = (Integer) objs[1];
						pa.onCancel(this, action);
					} break;
					case MSG_ON_ERRO: {
						Object[] objs = (Object[]) msg.obj;
						int action = (Integer) objs[1];
						Throwable t = (Throwable) objs[2];
						pa.onError(this, action, t);
					} break;
				}
			}
			return false;
		}
		
	}
	
	public static class PlatformActionListener {
		public void onComplete(Platform platform, int action, HashMap<String, Object> res) {
			
		}
		
		public void onError(Platform platform, int action, Throwable t) {
			
		}
		
		public void onCancel(Platform platform, int action) {
			
		}
	}

	public static class ShareParams {
		private Object sp;
		
		public ShareParams() {
			try {
				Class<?> ShareParams = dcLoader.loadClass("cn.sharesdk.framework.Platform$ShareParams");
				sp = ShareParams.newInstance();
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		
		public void setShareType(int type) {
			try {
				Method setShareType = sp.getClass().getMethod("setShareType", int.class);
				setShareType.setAccessible(true);
				setShareType.invoke(sp, type);
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		
		public void setText(String text) {
			try {
				Method setText = sp.getClass().getMethod("setText", String.class);
				setText.setAccessible(true);
				setText.invoke(sp, text);
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		
		public void setTitle(String title) {
			try {
				Method setTitle = sp.getClass().getMethod("setTitle", String.class);
				setTitle.setAccessible(true);
				setTitle.invoke(sp, title);
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		
		public void setTitleUrl(String titleUrl) {
			try {
				Method setTitleUrl = sp.getClass().getMethod("setTitleUrl", String.class);
				setTitleUrl.setAccessible(true);
				setTitleUrl.invoke(sp, titleUrl);
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		
		public void setUrl(String url) {
			try {
				Method setUrl = sp.getClass().getMethod("setUrl", String.class);
				setUrl.setAccessible(true);
				setUrl.invoke(sp, url);
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		
		public void setSiteUrl(String siteUrl) {
			try {
				Method setSiteUrl = sp.getClass().getMethod("setSiteUrl", String.class);
				setSiteUrl.setAccessible(true);
				setSiteUrl.invoke(sp, siteUrl);
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		
		public void setImagePath(String imagePath) {
			try {
				Method setImagePath = sp.getClass().getMethod("setImagePath", String.class);
				setImagePath.setAccessible(true);
				setImagePath.invoke(sp, imagePath);
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		
		public void setImageUrl(String imageUrl) {
			try {
				Method setImageUrl = sp.getClass().getMethod("setImageUrl", String.class);
				setImageUrl.setAccessible(true);
				setImageUrl.invoke(sp, imageUrl);
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		
		final Object getShareParams() {
			return sp;
		}
		
		public String toString() {
			return sp == null ? null : sp.toString();
		}
		
	}
	
	public static class PlatformDb {
		private Object db;
		
		public PlatformDb(Object db) {
			this.db = db;
		}
		
		public String getUserId() {
			try {
				Method getUserId = db.getClass().getMethod("getUserId");
				getUserId.setAccessible(true);
				return (String) getUserId.invoke(null);
			} catch (Throwable t) {
				t.printStackTrace();
			}
			return null;
		}
		
		public String getUserName() {
			try {
				Method getUserName = db.getClass().getMethod("getUserName");
				getUserName.setAccessible(true);
				return (String) getUserName.invoke(null);
			} catch (Throwable t) {
				t.printStackTrace();
			}
			return null;
		}
		
	}
	
	public static class OnekeyShare implements Callback {
		private static final int MSG_ON_COMP = 1;
		private static final int MSG_ON_CANC = 2;
		private static final int MSG_ON_ERRO = 3;
		
		private Object oks;
		private PlatformActionListener pa;
		
		public OnekeyShare() {
			try {
				Class<?> OnekeyShare = dcLoader.loadClass("cn.sharesdk.onekeyshare.OnekeyShare");
				oks = OnekeyShare.newInstance();
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		
		public void disableSSOWhenAuthorize() {
			try {
				ReflectHelper.invokeInstanceMethod(oks, "disableSSOWhenAuthorize");
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		
		public void setText(String text) {
			try {
				ReflectHelper.invokeInstanceMethod(oks, "setText", text);
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		
		public void setTitle(String title) {
			try {
				ReflectHelper.invokeInstanceMethod(oks, "setTitle", title);
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		
		public void setTitleUrl(String titleUrl) {
			try {
				ReflectHelper.invokeInstanceMethod(oks, "setTitleUrl", titleUrl);
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		
		public void setUrl(String url) {
			try {
				ReflectHelper.invokeInstanceMethod(oks, "setUrl", url);
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		
		public void setSiteUrl(String siteUrl) {
			try {
				ReflectHelper.invokeInstanceMethod(oks, "setSiteUrl", siteUrl);
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		
		public void setImagePath(String imagePath) {
			try {
				ReflectHelper.invokeInstanceMethod(oks, "setImagePath", imagePath);
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		
		public void setImageUrl(String imageUrl) {
			try {
				ReflectHelper.invokeInstanceMethod(oks, "setImageUrl", imageUrl);
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		
		public void setCallback(PlatformActionListener pa) {
			try {
				this.pa = pa;
				Class<?> ReflectablePlatformActionListener = dcLoader.loadClass("cn.sharesdk.framework.ReflectablePlatformActionListener");
				Object rpa = ReflectablePlatformActionListener.newInstance();
				Callback cb = new Callback() {
					public boolean handleMessage(Message msg) {
						Handler handler = new Handler(Looper.getMainLooper(), OnekeyShare.this);
						handler.sendMessage(msg);
						return false;
					}
				};
				ReflectHelper.invokeInstanceMethod(rpa, "setOnCompleteCallback", MSG_ON_COMP, cb);
				ReflectHelper.invokeInstanceMethod(rpa, "setOnCancelCallback", MSG_ON_CANC, cb);
				ReflectHelper.invokeInstanceMethod(rpa, "setOnErrorCallback", MSG_ON_ERRO, cb);
				ReflectHelper.invokeInstanceMethod(oks, "setCallback", rpa);
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		
		public void show(Context context) {
			try {
				ReflectHelper.invokeInstanceMethod(oks, "show", context);
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		
		public void setEditPageBackground(View bgView) {
			try {
				ReflectHelper.invokeInstanceMethod(oks, "setEditPageBackground", bgView);
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		
		public boolean handleMessage(Message msg) {
			if (pa != null) {
				switch (msg.what) {
					case MSG_ON_COMP: {
						Object[] objs = (Object[]) msg.obj;
						Platform plat = new Platform(objs[0]);
						int action = (Integer) objs[1];
						@SuppressWarnings("unchecked")
						HashMap<String, Object> res = (HashMap<String, Object>) objs[2];
						pa.onComplete(plat, action, res);
					} break;
					case MSG_ON_CANC: {
						Object[] objs = (Object[]) msg.obj;
						Platform plat = new Platform(objs[0]);
						int action = (Integer) objs[1];
						pa.onCancel(plat, action);
					} break;
					case MSG_ON_ERRO: {
						Object[] objs = (Object[]) msg.obj;
						Platform plat = new Platform(objs[0]);
						int action = (Integer) objs[1];
						Throwable t = (Throwable) objs[2];
						pa.onError(plat, action, t);
					} break;
				}
			}
			return false;
		}
		
	}
	
}
