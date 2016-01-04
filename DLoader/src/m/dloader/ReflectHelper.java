package m.dloader;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;

public class ReflectHelper {
	
	public static <T extends Object> T invokeInstanceMethod(Object receiver, String methodName, 
			Object... args) throws Throwable {
		try {
			return onInvokeInstanceMethod(receiver, methodName, args);
		} catch (Throwable t) {
			if (t instanceof NoSuchMethodException) {
				throw t;
			} else {
				String msg = "className: " + receiver.getClass() + ", methodName: " + methodName + ", args: " + Arrays.toString(args);
				throw new Throwable(msg, t);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private static <T extends Object> T onInvokeInstanceMethod(Object receiver, String methodName, 
			Object... args) throws Throwable {
		Class<?> clz = receiver.getClass();
		Class<?>[] types = getTypes(args);
		ArrayList<Class<?>> clzs = new ArrayList<Class<?>>();
		while (clz != null) {
			clzs.add(clz);
			clz = clz.getSuperclass();
		}
		
		for (Class<?> c : clzs) {
			Method[] mths = c.getDeclaredMethods();
			for (Method m : mths) {
				if (m.getName().equals(methodName) && !Modifier.isStatic(m.getModifiers()) 
						&& matchParams(m.getParameterTypes(), types)) {
					m.setAccessible(true);
					if (m.getReturnType() == Void.TYPE) {
						m.invoke(receiver, args);
						return null;
					} else {
						return (T) m.invoke(receiver, args);
					}
				}
			}
		}
		
		throw new NoSuchMethodException("className: " + receiver.getClass() + ", methodName: " + methodName + ", args: " + Arrays.toString(args));
	}
	
	private static Class<?>[] getTypes(Object[] args) {
		Class<?>[] types = new Class[args.length];
		for (int i = 0; i < args.length; i++) {
			types[i] = args[i] == null ? null : args[i].getClass();
		}
		return types;
	}
	
	private static boolean matchParams(Class<?>[] mTypes, Class<?>[] types) {
		if (mTypes.length == types.length) {
			boolean match = true;
			for (int i = 0; i < mTypes.length; i++) {
				if (types[i] != null && !primitiveEquals(mTypes[i], types[i]) 
						&& !mTypes[i].isAssignableFrom(types[i])) {
					match = false;
					break;
				}
			}
			return match;
		}
		return false;
	}
	
	private static boolean primitiveEquals(Class<?> primitive, Class<?> target) {
		return ((primitive == byte.class && target == Byte.class)
				|| (primitive == short.class && target == Short.class)
				|| (primitive == char.class && target == Character.class)
				|| (primitive == int.class && target == Integer.class)
				|| (primitive == long.class && target == Long.class)
				|| (primitive == float.class && target == Float.class)
				|| (primitive == double.class && target == Double.class)
				|| (primitive == boolean.class && target == Boolean.class));
	}
	
	public static void setInstanceField(Object receiver, String fieldName, Object value) 
			throws Throwable {
		try {
			onSetInstanceField(receiver, fieldName, value);
		} catch (Throwable t) {
			if (t instanceof NoSuchFieldException) {
				throw t;
			} else {
				
				String msg = "className: " + receiver.getClass() + ", fieldName: " + fieldName + ", value: " + String.valueOf(value);
				throw new Throwable(msg, t);
			}
		}
	}
	
	private static void onSetInstanceField(Object receiver, String fieldName, Object value) 
			throws Throwable {
		ArrayList<Class<?>> clzs = new ArrayList<Class<?>>();
		Class<?> clz = receiver.getClass();
		while (clz != null) {
			clzs.add(clz);
			clz = clz.getSuperclass();
		}
		
		for (Class<?> c : clzs) {
			Field fld = null;
			try {
				fld = c.getDeclaredField(fieldName);
			} catch (Throwable t) {}
			if (fld != null && !Modifier.isStatic(fld.getModifiers())) {
				fld.setAccessible(true);
				fld.set(receiver, value);
				return;
			}
		}
		
		throw new NoSuchFieldException("className: " + receiver.getClass() + ", fieldName: " + fieldName + ", value: " + String.valueOf(value));
	}
	
	public static <T extends Object> T getInstanceField(Object receiver, String fieldName) 
			throws Throwable {
		try {
			return onGetInstanceField(receiver, fieldName);
		} catch (Throwable t) {
			if (t instanceof NoSuchFieldException) {
				throw t;
			} else {
				String msg = "className: " + receiver.getClass() + ", fieldName: " + fieldName;
				throw new Throwable(msg, t);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Object> T onGetInstanceField(Object receiver, String fieldName) 
			throws Throwable {
		ArrayList<Class<?>> clzs = new ArrayList<Class<?>>();
		Class<?> clz = receiver.getClass();
		while (clz != null) {
			clzs.add(clz);
			clz = clz.getSuperclass();
		}
		
		for (Class<?> c : clzs) {
			Field fld = null;
			try {
				fld = c.getDeclaredField(fieldName);
			} catch (Throwable t) {}
			if (fld != null && !Modifier.isStatic(fld.getModifiers())) {
				fld.setAccessible(true);
				return (T) fld.get(receiver);
			}
		}
		
		throw new NoSuchFieldException("className: " + receiver.getClass() + ", fieldName: " + fieldName);
	}
	
}
