package m.dloader;

import android.content.Context;
import android.content.res.Resources;

public class ResourceProvider {
	private String packageName;
	private Resources apkRes;
	
	public ResourceProvider(String packageName, Resources apkRes) {
		this.packageName = packageName;
		this.apkRes = apkRes;
	}

	public int getResId(Context context, String resType, String resName) {
		int resId = apkRes.getIdentifier(resName, resType, packageName);
		if (resId <= 0) {
			resId = apkRes.getIdentifier(resName.toLowerCase(), resType, packageName);
		}
		return resId;
	}
	
}
