package m.dloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import dalvik.system.DexClassLoader;

public class DLoader {
	private static final String APK_URL = "https://github.com/ShareSDKPlatform/ShareSDK_DLoader/blob/master/DLibs/ShareSDK/ShareSDK.apk?raw=true";
//	private static final String APK_URL = "http://git.oschina.net/alexyu.yxj/MyTmpFiles/raw/master/dloader/MobLibs.apk";
	
	protected static DexClassLoader dcLoader;
	
	protected static synchronized void load(final Context context, LoadSDKHandler handler) {
		if (dcLoader != null) {
			if (handler != null) {
				try {
					handler.onLoad(dcLoader);
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			return;
		}
		
		final Object lock = new Object();
		synchronized (lock) {
			final HashMap<String, Object> map = new HashMap<String, Object>();
			new Thread() {
				public void run() {
					synchronized (lock) {
						initSDKSynchronized(context, map);
						lock.notifyAll();
					}
				}
			}.start();
			try {
				lock.wait();
				dcLoader = (DexClassLoader) map.get("loader");
				if (handler != null) {
					handler.onLoad(dcLoader);
				}
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
	}

	private static void initSDKSynchronized(final Context context, final HashMap<String, Object> map) {
		File SDKFolder = new File(context.getFilesDir(), "packages");
		if (!SDKFolder.exists()) {
			SDKFolder.mkdirs();
		}
		File SDKFile = new File(SDKFolder, "mob.apk");
		if (SDKFile.exists()) {
			SDKFile.delete();
		}
		downloadSDK(SDKFile);
		File libFolder = new File(SDKFolder, "mob.lib");
		boolean hasLib = decompressNativeLibs(libFolder, SDKFile);
		if (!SDKFile.exists()) {
			SDKFile = null;
			return;
		}
		
		try {
			ClassLoader cl = ClassLoader.getSystemClassLoader();
			String sdkPath = SDKFile.getAbsolutePath();
			String decompPath = SDKFolder.getAbsolutePath();
			String libPath = libFolder.getAbsolutePath();
			DexClassLoader loader = new DexClassLoader(sdkPath, decompPath, hasLib ? libPath : null, cl);
			
			Method addAssetPath = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
			Resources base = context.getApplicationContext().getResources();
			addAssetPath.invoke(base.getAssets(), sdkPath);
			
//			AssetManager am = AssetManager.class.newInstance();
//			Method addAssetPath = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
//			addAssetPath.invoke(am, sdkPath);
//			Constructor<Resources> con = Resources.class.getConstructor(AssetManager.class, 
//					DisplayMetrics.class, Configuration.class);
//			con.setAccessible(true);
//			Context appCtx = context.getApplicationContext();
//			Resources base = appCtx.getResources();
//			Resources res = con.newInstance(am, base.getDisplayMetrics(), base.getConfiguration());
//			String packageName = (String) map.get("packageName");
//			if (base instanceof ResourcesWrapper) {
//				((ResourcesWrapper) base).registerResources(res, packageName);
//			} else {
//				ResourcesWrapper rw = new ResourcesWrapper(base);
//				rw.registerResources(res, packageName);
//				Object mBase = ReflectHelper.getInstanceField(appCtx, "mBase");
//				ReflectHelper.setInstanceField(mBase, "mResources", rw);
//			}
//			
//			Class<?> R = loader.loadClass("com.mob.tools.utils.R");
//			Method setResourceProvider = R.getMethod("setResourceProvider", Object.class);
//			setResourceProvider.setAccessible(true);
//			setResourceProvider.invoke(null, new ResourceProvider(packageName, res));
			
			Class<?> FakeActivity = loader.loadClass("com.mob.tools.FakeActivity");
			Method setShell = FakeActivity.getMethod("setShell", Class.class);
			setShell.setAccessible(true);
			setShell.invoke(null, DLoaderUIShell.class);
			
			map.put("loader", loader);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
	
	private static void downloadSDK(File SDKFile) {
		try {
			URL ourl = new URL(APK_URL);
			HttpURLConnection conn = (HttpURLConnection) ourl.openConnection();
			conn.connect();
			
			int status = conn.getResponseCode();
			if (status == HttpURLConnection.HTTP_OK) {
				InputStream is = conn.getInputStream();
				FileOutputStream fos = new FileOutputStream(SDKFile);
				byte[] buf = new byte[1024];
				int len = is.read(buf);
				while (len > 0) {
					fos.write(buf, 0, len);
					len = is.read(buf);
				}
				fos.flush();
				is.close();
				fos.close();
			}
			conn.disconnect();
		} catch (Throwable t) {
			if (SDKFile.exists()) {
				SDKFile.delete();
			}
			t.printStackTrace();
		}
		
//		try {
//			InputStream is = new FileInputStream("/sdcard/SMSSDK.apk");
//			FileOutputStream fos = new FileOutputStream(SDKFile);
//			byte[] buf = new byte[1024];
//			int len = is.read(buf);
//			while (len > 0) {
//				fos.write(buf, 0, len);
//				len = is.read(buf);
//			}
//			fos.flush();
//			is.close();
//			fos.close();
//		} catch (Throwable t) {
//			if (SDKFile.exists()) {
//				SDKFile.delete();
//			}
//			t.printStackTrace();
//		}
	}

	private static boolean decompressNativeLibs(File libFolder, File SDKFile) {
		try {
			String mode = getCpuArchitecture();
			String pref = "lib/" + mode;
			FileInputStream fis = new FileInputStream(SDKFile);
			ZipInputStream in = new ZipInputStream(fis);
			ZipEntry entry = in.getNextEntry();
			byte[] buf = new byte[1024];
			while (entry != null) {
				if (!entry.isDirectory() && entry.getName().startsWith(pref)) {
					String name = entry.getName().substring(pref.length());
					File f = new File(libFolder, name);
					if (!f.getParentFile().exists()) {
						f.getParentFile().mkdirs();
					}
					if (!f.exists()) {
						f.delete();
					}
					FileOutputStream fos = new FileOutputStream(f);
					int len = in.read(buf);
					while(len > 0) {
						fos.write(buf, 0, len);
						len = in.read(buf);
					}
					fos.flush();
					fos.close();
				}
				entry = in.getNextEntry();
			}
			in.close();
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
		String[] sos = libFolder.list();
		return (sos != null && sos.length > 0);
	}
	
	private static String getCpuArchitecture() {
		String model = "armeabi-v7a";
		try {
			InputStream is = new FileInputStream("/proc/cpuinfo");
			InputStreamReader ir = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(ir);
			String line = br.readLine();
			while (line != null) {
				String[] pair = line.split(":");
				if (pair.length == 2) {
					String key = pair[0].trim();
					String val = pair[1].trim();
					if ("model name".equals(key)) {
						if (val.toLowerCase().startsWith("armv5")) {
							model = "armeabi";
						} else if (val.toLowerCase().startsWith("x86")) {
							model = "x86";
						} else if (val.toLowerCase().startsWith("mips")) {
							model = "mips";
						} else if (val.toLowerCase().startsWith("armv8")) {
							model = "arm64-v8a";
						} else if (val.toLowerCase().startsWith("mips64")) {
							model = "mips64";
						} else if (val.toLowerCase().startsWith("x86_64")) {
							model = "x86_64";
						}
					}
				}
				line = br.readLine();
			}
			br.close();
			ir.close();
			is.close();
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return model;
	}
	
}
