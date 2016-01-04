package m.dloader;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import org.xmlpull.v1.XmlPullParserException;

import android.content.res.AssetFileDescriptor;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Movie;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class ResourcesWrapper extends Resources {
	private HashMap<String, Resources> resources;

	public ResourcesWrapper(Resources base) {
		super(base.getAssets(), base.getDisplayMetrics(), base.getConfiguration());
		resources = new HashMap<String, Resources>();
	}
	
	public void registerResources(Resources res, String packageName) {
		resources.put(packageName, res);
	}
	
	public Set<Entry<String, Resources>> getResourcesEntrySet() {
		return resources.entrySet();
	}
	
	private Resources getResources() {
		if (resources != null) {
			for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
				String cn = ste.getClassName();
				for (Entry<String, Resources> ent : resources.entrySet()) {
					if (cn.startsWith(ent.getKey())) {
						return ent.getValue();
					}
				}
			}
		}
		return null;
	}
	
	public int getIdentifier(String name, String defType, String defPackage) {
		Resources res = getResources();
		if (res != null) {
			return res.getIdentifier(name, defType, defPackage);
		} else {
			return super.getIdentifier(name, defType, defPackage);
		}
	}
	
	public XmlResourceParser getAnimation(int id) throws NotFoundException {
		Resources res = getResources();
		if (res != null) {
			return res.getAnimation(id);
		} else {
			return super.getAnimation(id);
		}
	}
	
	public boolean getBoolean(int id) throws NotFoundException {
		Resources res = getResources();
		if (res != null) {
			return res.getBoolean(id);
		} else {
			return super.getBoolean(id);
		}
	}
	
	@SuppressWarnings("deprecation")
	public int getColor(int id) throws NotFoundException {
		Resources res = getResources();
		if (res != null) {
			return res.getColor(id);
		} else {
			return super.getColor(id);
		}
	}
	
	@SuppressWarnings("deprecation")
	public ColorStateList getColorStateList(int id) throws NotFoundException {
		Resources res = getResources();
		if (res != null) {
			return res.getColorStateList(id);
		} else {
			return super.getColorStateList(id);
		}
	}
	
	public Configuration getConfiguration() {
		Resources res = getResources();
		if (res != null) {
			return res.getConfiguration();
		} else {
			return super.getConfiguration();
		}
	}
	
	public float getDimension(int id) throws NotFoundException {
		Resources res = getResources();
		if (res != null) {
			return res.getDimension(id);
		} else {
			return super.getDimension(id);
		}
	}
	
	public int getDimensionPixelOffset(int id) throws NotFoundException {
		Resources res = getResources();
		if (res != null) {
			return res.getDimensionPixelOffset(id);
		} else {
			return super.getDimensionPixelOffset(id);
		}
	}
	
	public int getDimensionPixelSize(int id) throws NotFoundException {
		Resources res = getResources();
		if (res != null) {
			return res.getDimensionPixelSize(id);
		} else {
			return super.getDimensionPixelSize(id);
		}
	}
	
	public DisplayMetrics getDisplayMetrics() {
		Resources res = getResources();
		if (res != null) {
			return res.getDisplayMetrics();
		} else {
			return super.getDisplayMetrics();
		}
	}
	
	@SuppressWarnings("deprecation")
	public Drawable getDrawable(int id) throws NotFoundException {
		Resources res = getResources();
		if (res != null) {
			return res.getDrawable(id);
		} else {
			return super.getDrawable(id);
		}
	}
	
	@SuppressWarnings("deprecation")
	public Drawable getDrawableForDensity(int id, int density)
			throws NotFoundException {
		Resources res = getResources();
		if (res != null) {
			return res.getDrawableForDensity(id, density);
		} else {
			return super.getDrawableForDensity(id, density);
		}
	}
	
	public float getFraction(int id, int base, int pbase) {
		Resources res = getResources();
		if (res != null) {
			return res.getFraction(id, base, pbase);
		} else {
			return super.getFraction(id, base, pbase);
		}
	}
	
	public int[] getIntArray(int id) throws NotFoundException {
		Resources res = getResources();
		if (res != null) {
			return res.getIntArray(id);
		} else {
			return super.getIntArray(id);
		}
	}
	
	public int getInteger(int id) throws NotFoundException {
		Resources res = getResources();
		if (res != null) {
			return res.getInteger(id);
		} else {
			return super.getInteger(id);
		}
	}
	
	public XmlResourceParser getLayout(int id) throws NotFoundException {
		Resources res = getResources();
		if (res != null) {
			return res.getLayout(id);
		} else {
			return super.getLayout(id);
		}
	}
	
	public Movie getMovie(int id) throws NotFoundException {
		Resources res = getResources();
		if (res != null) {
			return res.getMovie(id);
		} else {
			return super.getMovie(id);
		}
	}
	
	public String getQuantityString(int id, int quantity)
			throws NotFoundException {
		Resources res = getResources();
		if (res != null) {
			return res.getQuantityString(id, quantity);
		} else {
			return super.getQuantityString(id, quantity);
		}
	}
	
	public String getQuantityString(int id, int quantity, Object... formatArgs)
			throws NotFoundException {
		Resources res = getResources();
		if (res != null) {
			return res.getQuantityString(id, quantity, formatArgs);
		} else {
			return super.getQuantityString(id, quantity, formatArgs);
		}
	}
	
	public CharSequence getQuantityText(int id, int quantity)
			throws NotFoundException {
		Resources res = getResources();
		if (res != null) {
			return res.getQuantityText(id, quantity);
		} else {
			return super.getQuantityText(id, quantity);
		}
	}
	
	public String getResourceEntryName(int resid) throws NotFoundException {
		Resources res = getResources();
		if (res != null) {
			return res.getResourceEntryName(resid);
		} else {
			return super.getResourceEntryName(resid);
		}
	}
	
	public String getResourceName(int resid) throws NotFoundException {
		Resources res = getResources();
		if (res != null) {
			return res.getResourceName(resid);
		} else {
			return super.getResourceName(resid);
		}
	}
	
	public String getResourcePackageName(int resid) throws NotFoundException {
		Resources res = getResources();
		if (res != null) {
			return res.getResourcePackageName(resid);
		} else {
			return super.getResourcePackageName(resid);
		}
	}
	
	public String getResourceTypeName(int resid) throws NotFoundException {
		Resources res = getResources();
		if (res != null) {
			return res.getResourceTypeName(resid);
		} else {
			return super.getResourceTypeName(resid);
		}
	}
	
	public String getString(int id) throws NotFoundException {
		Resources res = getResources();
		if (res != null) {
			return res.getString(id);
		} else {
			return super.getString(id);
		}
	}
	
	public String getString(int id, Object... formatArgs)
			throws NotFoundException {
		Resources res = getResources();
		if (res != null) {
			return res.getString(id, formatArgs);
		} else {
			return super.getString(id, formatArgs);
		}
	}
	
	public String[] getStringArray(int id) throws NotFoundException {
		Resources res = getResources();
		if (res != null) {
			return res.getStringArray(id);
		} else {
			return super.getStringArray(id);
		}
	}
	
	public CharSequence getText(int id) throws NotFoundException {
		Resources res = getResources();
		if (res != null) {
			return res.getText(id);
		} else {
			return super.getText(id);
		}
	}
	
	public CharSequence getText(int id, CharSequence def) {
		Resources res = getResources();
		if (res != null) {
			return res.getText(id, def);
		} else {
			return super.getText(id, def);
		}
	}
	
	public CharSequence[] getTextArray(int id) throws NotFoundException {
		Resources res = getResources();
		if (res != null) {
			return res.getTextArray(id);
		} else {
			return super.getTextArray(id);
		}
	}
	
	public void getValue(int id, TypedValue outValue, boolean resolveRefs)
			throws NotFoundException {
		Resources res = getResources();
		if (res != null) {
			res.getValue(id, outValue, resolveRefs);
		} else {
			super.getValue(id, outValue, resolveRefs);
		}
	}
	
	public void getValue(String name, TypedValue outValue, boolean resolveRefs)
			throws NotFoundException {
		Resources res = getResources();
		if (res != null) {
			res.getValue(name, outValue, resolveRefs);
		} else {
			super.getValue(name, outValue, resolveRefs);
		}
	}
	
	public void getValueForDensity(int id, int density, TypedValue outValue,
			boolean resolveRefs) throws NotFoundException {
		Resources res = getResources();
		if (res != null) {
			res.getValueForDensity(id, density, outValue, resolveRefs);
		} else {
			super.getValueForDensity(id, density, outValue, resolveRefs);
		}
	}
	
	public XmlResourceParser getXml(int id) throws NotFoundException {
		Resources res = getResources();
		if (res != null) {
			return res.getXml(id);
		} else {
			return super.getXml(id);
		}
	}
	
	public TypedArray obtainAttributes(AttributeSet set, int[] attrs) {
		Resources res = getResources();
		if (res != null) {
			return res.obtainAttributes(set, attrs);
		} else {
			return super.obtainAttributes(set, attrs);
		}
	}
	
	public TypedArray obtainTypedArray(int id) throws NotFoundException {
		Resources res = getResources();
		if (res != null) {
			return res.obtainTypedArray(id);
		} else {
			return super.obtainTypedArray(id);
		}
	}
	
	public InputStream openRawResource(int id) throws NotFoundException {
		Resources res = getResources();
		if (res != null) {
			return res.openRawResource(id);
		} else {
			return super.openRawResource(id);
		}
	}
	
	public InputStream openRawResource(int id, TypedValue value)
			throws NotFoundException {
		Resources res = getResources();
		if (res != null) {
			return res.openRawResource(id, value);
		} else {
			return super.openRawResource(id, value);
		}
	}
	
	public AssetFileDescriptor openRawResourceFd(int id)
			throws NotFoundException {
		Resources res = getResources();
		if (res != null) {
			return res.openRawResourceFd(id);
		} else {
			return super.openRawResourceFd(id);
		}
	}
	
	public void parseBundleExtra(String tagName, AttributeSet attrs,
			Bundle outBundle) throws XmlPullParserException {
		Resources res = getResources();
		if (res != null) {
			res.parseBundleExtra(tagName, attrs, outBundle);
		} else {
			super.parseBundleExtra(tagName, attrs, outBundle);
		}
	}
	
	public void parseBundleExtras(XmlResourceParser parser, Bundle outBundle)
			throws XmlPullParserException, IOException {
		Resources res = getResources();
		if (res != null) {
			res.parseBundleExtras(parser, outBundle);
		} else {
			super.parseBundleExtras(parser, outBundle);
		}
	}
	
	public void updateConfiguration(Configuration config, DisplayMetrics metrics) {
		Resources res = getResources();
		if (res != null) {
			res.updateConfiguration(config, metrics);
		} else {
			super.updateConfiguration(config, metrics);
		}
	}

	public int getColor(int id, Theme theme) throws NotFoundException {
		Resources res = getResources();
		if (res != null) {
			return res.getColor(id, theme);
		} else {
			return super.getColor(id, theme);
		}
	}
	
	public ColorStateList getColorStateList(int id, Theme theme)
			throws NotFoundException {
		Resources res = getResources();
		if (res != null) {
			return res.getColorStateList(id, theme);
		} else {
			return super.getColorStateList(id, theme);
		}
	}
	
	public Drawable getDrawable(int id, Theme theme) throws NotFoundException {
		Resources res = getResources();
		if (res != null) {
			return res.getDrawable(id, theme);
		} else {
			return super.getDrawable(id, theme);
		}
	}
	
	public Drawable getDrawableForDensity(int id, int density, Theme theme) {
		Resources res = getResources();
		if (res != null) {
			return res.getDrawableForDensity(id, density, theme);
		} else {
			return super.getDrawableForDensity(id, density, theme);
		}
	}
	
}
