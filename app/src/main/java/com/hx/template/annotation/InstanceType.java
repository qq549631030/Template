package com.hx.template.annotation;

import android.text.TextUtils;

public enum InstanceType {
	/**
	 * 类型不能识别，不做保存/恢复处理
	 */
	UNKNOW(null),
	/**
	 * 类型自动识别，尝试根据FieldType自动识别类型，如不能识别则认为是InstanceType.UNKNOW类型
	 */
	AUTORECOGNIZE(null),
	/**
	 * 对应IBinder类型字段，使用Bundle.putBinder(String key, IBinder value)方法保存
	 */
	BINDER("android.os.IBinder"),
	/**
	 * 对应boolean类型字段，使用Bundle.putBoolean(String key, boolean value)方法保存
	 */
	BOOLEAN("boolean"),
	/**
	 * 对应boolean[]类型字段，使用Bundle.putBooleanArray(String key, boolean[] value)方法保存
	 */
	BOOLEANARRAY("[Z"),
	/**
	 * 对应Bundle类型字段，使用Bundle.putBundle(String key, Bundle value)方法保存
	 */
	BUNDLE("android.os.Bundle"),
	/**
	 * 对应byte类型字段，使用Bundle.putByte(String key, byte value)方法保存
	 */
	BYTE("byte"),
	/**
	 * 对应byte[]类型字段，使用Bundle.putByteArray(String key, byte[] value)方法保存
	 */
	BYTEARRAY("[B"),
	/**
	 * 对应char类型字段，使用Bundle.putChar(String key, char value)方法保存
	 */
	CHAR("char"),
	/**
	 * 对应char[]类型字段，使用Bundle.putCharArray(String key, char[] value)方法保存
	 */
	CHARARRAY("[C"),
	/**
	 * 对应double类型字段，使用Bundle.putDouble(String key, double value)方法保存
	 */
	DOUBLE("double"),
	/**
	 * 对应double[]类型字段，使用Bundle.putDoubleArray(String key, double[] value)方法保存
	 */
	DOUBLEARRAY("[D"),
	/**
	 * 对应float类型字段，使用Bundle.putFloat(String key, float value)方法保存
	 */
	FLOAT("float"),
	/**
	 * 对应float[]类型字段，使用Bundle.putFloatArray(String key, float[] value)方法保存
	 */
	FLOATARRAY("[F"),
	/**
	 * 对应int类型字段，使用Bundle.putInt(String key, int value)方法保存
	 */
	INT("int"),
	/**
	 * 对应int[]类型字段，使用Bundle.putIntArray(String key, int[] value)方法保存
	 */
	INTARRAY("[I"),
	/**
	 * 对应ArrayList<Integer>类型字段，使用Bundle.putIntegerArrayList(String key, ArrayList<Integer> value)方法保存
	 */
	INTEGERARRAYLIST(null),
	/**
	 * 对应long类型字段，使用Bundle.putLong(String key, long value)方法保存
	 */
	LONG("long"),
	/**
	 * 对应long[]类型字段，使用Bundle.putLongArray(String key, long[] value)方法保存
	 */
	LONGARRAY("[J"),
	/**
	 * 对应short类型字段，使用Bundle.putShort(String key, short value)方法保存
	 */
	SHORT("short"),
	/**
	 * 对应short[]类型字段，使用Bundle.putShortArray(String key, short[] value)方法保存
	 */
	SHORTARRAY("[S"),
	/**
	 * 对应Size类型字段，使用Bundle.putSize(String key, Size value)方法保存
	 */
	SIZE("android.util.Size"),
	/**
	 * 对应SizeF类型字段，使用Bundle.putSizeF(String key, SizeF value)方法保存
	 */
	SIZEF("android.util.SizeF"),
	/**
	 * 对应SparseArray<? extends Parcelable>类型字段，使用Bundle.putSparseParcelableArray(String key, SparseArray<? extends Parcelable> value)方法保存
	 */
	SPARSEPARCELABLEARRAY(null),
	/**
	 * 对应String类型字段，使用Bundle.putString(String key, String value)方法保存
	 */
	STRING("java.lang.String"),
	/**
	 * 对应String[]类型字段，使用Bundle.putStringArray(String key, String[] value)方法保存
	 */
	STRINGARRAY("[Ljava.lang.String;"),
	/**
	 * 对应ArrayList<String>类型字段，使用Bundle.putStringArrayList(String key, ArrayList<String> value)方法保存
	 */
	STRINGARRAYLIST(null),
	/**
	 * 对应CharSequence类型字段，使用Bundle.putCharSequence(String key, CharSequence value)方法保存
	 */
	CHARSEQUENCE("java.lang.CharSequence"),
	/**
	 * 对应CharSequence[]类型字段，使用Bundle.putCharSequenceArray(String key, CharSequence[] value)方法保存
	 */
	CHARSEQUENCEARRAY("[Ljava.lang.CharSequence;"),
	/**
	 * 对应ArrayList<CharSequence>类型字段，使用Bundle.putCharSequenceArrayList(String key, ArrayList<CharSequence> value)方法保存
	 */
	CHARSEQUENCEARRAYLIST(null),
	/**
	 * 对应Parcelable类型字段，使用Bundle.putParcelable(String key, Parcelable value)方法保存
	 */
	PARCELABLE("android.os.Parcelable"),
	/**
	 * 对应Parcelable[]类型字段，使用Bundle.putParcelableArray(String key, Parcelable[] value)方法保存
	 */
	PARCELABLEARRAY("[Landroid.os.Parcelable;"),
	/**
	 * 对应ArrayList<? extends Parcelable>类型字段，使用Bundle.putParcelableArrayList(String key, ArrayList<? extends Parcelable> value)方法保存
	 */
	PARCELABLEARRAYLIST(null),
	/**
	 * 对应Serializable类型字段，使用Bundle.putSerializable(String key, Serializable value)方法保存
	 */
	SERIALIZABLE("java.io.Serializable");
	
	private final Class<?> clz;
	private final String name;
	
	public Class<?> getClz() {
		return clz;
	}
	
	public String getName() {
		return name;
	}
	
	private InstanceType(String clzName) {
		name = clzName;
		Class<?> clz = null;
		try {
			if(!TextUtils.isEmpty(clzName)) {
				clz = Class.forName(clzName);
			}
		} catch (ClassNotFoundException e) {
		}
		this.clz = clz;
	}
}
