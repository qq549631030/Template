package com.hx.template.global;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseArray;

import com.hx.template.annotation.InstanceType;
import com.hx.template.annotation.SaveInstanceAnnotation;
import com.hx.template.base.BaseActivity;
import com.hx.template.base.BaseFragment;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

public final class SaveSceneUtils {

    public static final String RESTORE_INSTANCE_STATE_FLAG = "__restore_instance_state_flag";

    private SaveSceneUtils() {
    }

    public static final void onSaveInstanceState(final BaseActivity activity, Bundle outState) {
        onSaveInstanceStateInternal(activity, activity.getClass(), outState);
        outState.putBoolean(RESTORE_INSTANCE_STATE_FLAG, false);
    }

    public static final void onRestoreInstanceState(final BaseActivity activity, Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.getBoolean(RESTORE_INSTANCE_STATE_FLAG, false)) {
            return;
        }
        onRestoreInstanceStateInternal(activity, activity.getClass(), savedInstanceState);
        if (savedInstanceState != null) {
            savedInstanceState.putBoolean(RESTORE_INSTANCE_STATE_FLAG, true);
        }
    }

    public static final void onSaveInstanceState(final BaseFragment fragment, Bundle outState) {
        onSaveInstanceStateInternal(fragment, fragment.getClass(), outState);
        outState.putBoolean(RESTORE_INSTANCE_STATE_FLAG, false);
    }

    public static final void onRestoreInstanceState(final BaseFragment fragment, Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.getBoolean(RESTORE_INSTANCE_STATE_FLAG, false)) {
            return;
        }
        onRestoreInstanceStateInternal(fragment, fragment.getClass(), savedInstanceState);
        if (savedInstanceState != null) {
            savedInstanceState.putBoolean(RESTORE_INSTANCE_STATE_FLAG, true);
        }
    }

    @SuppressLint("NewApi")
    @SuppressWarnings("unchecked")
    private static final void onSaveInstanceStateInternal(final Object object, Class<?> clz, Bundle outState) {
        if (object == null || outState == null || outState.isEmpty()) {
            return;
        }
        if (!(object instanceof BaseFragment || object instanceof BaseActivity)) {
            return;
        }
        try {
            if (object instanceof BaseFragment) {
                clz.asSubclass(BaseFragment.class);
            }
            if (object instanceof BaseActivity) {
                clz.asSubclass(BaseActivity.class);
            }
        } catch (ClassCastException e) {
            return;
        }
        onSaveInstanceStateInternal(object, clz.getSuperclass(), outState);
        Field[] fields = clz.getDeclaredFields();
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                SaveInstanceAnnotation saveInstanceAnnotation = field.getAnnotation(SaveInstanceAnnotation.class);
                if (saveInstanceAnnotation == null) {
                    continue;
                }
                String key = TextUtils.isEmpty(saveInstanceAnnotation.key()) ? field.getName() : saveInstanceAnnotation.key();
                InstanceType instanceType = transformInstanceType(saveInstanceAnnotation.type(), field);
                try {
                    if (!InstanceType.AUTORECOGNIZE.equals(instanceType) && !InstanceType.UNKNOW.equals(instanceType)) {
                        if (outState.containsKey(key)) {
                            outState.remove(key);
                        }
                        field.setAccessible(true);
                    }
                    Object instanceState = field.get(object);
                    boolean logInfo = true;
                    switch (instanceType) {
                        case BINDER:
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                                outState.putBinder(key, (IBinder) instanceState);
                            }
                            break;
                        case BOOLEAN:
                            outState.putBoolean(key, (Boolean) instanceState);
                            break;
                        case BOOLEANARRAY:
                            outState.putBooleanArray(key, (boolean[]) instanceState);
                            break;
                        case BUNDLE:
                            outState.putBundle(key, (Bundle) instanceState);
                            break;
                        case BYTE:
                            outState.putByte(key, (Byte) instanceState);
                            break;
                        case BYTEARRAY:
                            outState.putByteArray(key, (byte[]) instanceState);
                            break;
                        case CHAR:
                            outState.putChar(key, (Character) instanceState);
                            break;
                        case CHARARRAY:
                            outState.putCharArray(key, (char[]) instanceState);
                            break;
                        case CHARSEQUENCE:
                            outState.putCharSequence(key, (CharSequence) instanceState);
                            break;
                        case CHARSEQUENCEARRAY:
                            outState.putCharSequenceArray(key, (CharSequence[]) instanceState);
                            break;
                        case CHARSEQUENCEARRAYLIST:
                            outState.putCharSequenceArrayList(key, (ArrayList<CharSequence>) instanceState);
                            break;
                        case DOUBLE:
                            outState.putDouble(key, (Double) instanceState);
                            break;
                        case DOUBLEARRAY:
                            outState.putDoubleArray(key, (double[]) instanceState);
                            break;
                        case FLOAT:
                            outState.putFloat(key, (Float) instanceState);
                            break;
                        case FLOATARRAY:
                            outState.putFloatArray(key, (float[]) instanceState);
                            break;
                        case INT:
                            outState.putInt(key, (Integer) instanceState);
                            break;
                        case INTARRAY:
                            outState.putIntArray(key, (int[]) instanceState);
                            break;
                        case INTEGERARRAYLIST:
                            outState.putIntegerArrayList(key, (ArrayList<Integer>) instanceState);
                            break;
                        case LONG:
                            outState.putLong(key, (Long) instanceState);
                            break;
                        case LONGARRAY:
                            outState.putLongArray(key, (long[]) instanceState);
                            break;
                        case PARCELABLE:
                            outState.putParcelable(key, (Parcelable) instanceState);
                            break;
                        case PARCELABLEARRAY:
                            outState.putParcelableArray(key, (Parcelable[]) instanceState);
                            break;
                        case PARCELABLEARRAYLIST:
                            outState.putParcelableArrayList(key, (ArrayList<Parcelable>) instanceState);
                            break;
                        case SERIALIZABLE:
                            outState.putSerializable(key, (Serializable) instanceState);
                            break;
                        case SHORT:
                            outState.putShort(key, (Short) instanceState);
                            break;
                        case SHORTARRAY:
                            outState.putShortArray(key, (short[]) instanceState);
                            break;
                        case SIZE:
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                outState.putSize(key, (Size) instanceState);
                            }
                            break;
                        case SIZEF:
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                outState.putSizeF(key, (SizeF) instanceState);
                            }
                            break;
                        case SPARSEPARCELABLEARRAY:
                            outState.putSparseParcelableArray(key, (SparseArray<? extends Parcelable>) instanceState);
                            break;
                        case STRING:
                            outState.putString(key, (String) instanceState);
                            break;
                        case STRINGARRAY:
                            outState.putStringArray(key, (String[]) instanceState);
                            break;
                        case STRINGARRAYLIST:
                            outState.putStringArrayList(key, (ArrayList<String>) instanceState);
                            break;
                        default:
                            logInfo = false;
                            break;
                    }
                    if (logInfo) {
                        HXLog.d("Save [%s.%s] instanceType=[%s] key=[%s] value=[%s]", object.getClass().getSimpleName(), field.getName(), instanceType.toString(), key, instanceState == null ? "null" : instanceState.toString());
                    } else {
                        HXLog.d("Can not save state[%s.%s], because instance type [%s] !NOT! support by android SaveInstanceState!", object.getClass().getSimpleName(), field.getName(), field.getGenericType().toString());
                    }
                } catch (Exception e) {
                    HXLog.e(e);
                }
            }
        }
    }

    private static final void onRestoreInstanceStateInternal(final Object object, Class<?> clz, Bundle savedInstanceState) {
        if (object == null || savedInstanceState == null || savedInstanceState.isEmpty()) {
            return;
        }
        if (!(object instanceof BaseFragment || object instanceof BaseActivity)) {
            return;
        }
        try {
            if (object instanceof BaseFragment) {
                clz.asSubclass(BaseFragment.class);
            }
            if (object instanceof BaseActivity) {
                clz.asSubclass(BaseActivity.class);
            }
        } catch (ClassCastException e) {
            return;
        }
        onRestoreInstanceStateInternal(object, clz.getSuperclass(), savedInstanceState);
        Field[] fields = clz.getDeclaredFields();
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                SaveInstanceAnnotation saveInstanceAnnotation = field.getAnnotation(SaveInstanceAnnotation.class);
                if (saveInstanceAnnotation == null) {
                    continue;
                }
                String key = TextUtils.isEmpty(saveInstanceAnnotation.key()) ? field.getName() : saveInstanceAnnotation.key();
                InstanceType instanceType = transformInstanceType(saveInstanceAnnotation.type(), field);
                try {
                    if (!InstanceType.AUTORECOGNIZE.equals(instanceType) && !InstanceType.UNKNOW.equals(instanceType)) {
                        field.setAccessible(true);
                    }
                    Object instanceState = savedInstanceState.get(key);
                    boolean logInfo = true;
                    switch (instanceType) {
                        case BOOLEAN:
                        case BOOLEANARRAY:
                        case BYTE:
                        case BYTEARRAY:
                        case CHAR:
                        case CHARARRAY:
                        case CHARSEQUENCE:
                        case CHARSEQUENCEARRAY:
                        case DOUBLE:
                        case DOUBLEARRAY:
                        case FLOAT:
                        case FLOATARRAY:
                        case INT:
                        case INTARRAY:
                        case LONG:
                        case LONGARRAY:
                        case PARCELABLE:
                        case SERIALIZABLE:
                        case SHORT:
                        case SHORTARRAY:
                        case STRING:
                        case STRINGARRAY:
                        case SPARSEPARCELABLEARRAY:
                        case CHARSEQUENCEARRAYLIST:
                        case INTEGERARRAYLIST:
                        case PARCELABLEARRAYLIST:
                        case STRINGARRAYLIST:
                        case BUNDLE:
                            field.set(object, instanceState);
                            break;

                        case BINDER:
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                                field.set(object, instanceState);
                            }
                            break;

                        case SIZE:
                        case SIZEF:
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                field.set(object, instanceState);
                            }
                            break;

                        case PARCELABLEARRAY:
                            Parcelable[] instanceArray = (Parcelable[]) instanceState;
                            if (instanceArray != null) {
                                Object parcelableArray = Array.newInstance(field.getType().getComponentType(), instanceArray.length);
                                for (int i = 0; i < instanceArray.length; i++) {
                                    Array.set(parcelableArray, i, field.getType().getComponentType().cast(instanceArray[i]));
                                }
                                field.set(object, parcelableArray);
                            } else {
                                field.set(object, null);
                            }
                            break;

                        default:
                            logInfo = false;
                            break;
                    }
                    if (logInfo) {
                        HXLog.d("Restore [%s.%s] instanceType=[%s] key=[%s] value=[%s]", object.getClass().getSimpleName(), field.getName(), instanceType.toString(), key, instanceState == null ? "null" : instanceState.toString());
                    } else {
                        HXLog.d("Can not restore state[%s.%s], because instance type [%s] !NOT! support by android RestoreInstanceState!", object.getClass().getSimpleName(), field.getName(), field.getGenericType().toString());
                    }
                } catch (Exception e) {
                    HXLog.e(e);
                }
            }
        }
    }

    private static final InstanceType transformInstanceType(InstanceType type, Field field) {
        InstanceType instanceType = InstanceType.UNKNOW;
        if (InstanceType.AUTORECOGNIZE.equals(type)) {
            Class<?> fieldType = field.getType();
            for (InstanceType it : InstanceType.values()) {
                if (InstanceType.CHARSEQUENCEARRAYLIST.equals(it)
                        || InstanceType.INTEGERARRAYLIST.equals(it)
                        || InstanceType.PARCELABLEARRAYLIST.equals(it)
                        || InstanceType.STRINGARRAYLIST.equals(it)
                        || InstanceType.SPARSEPARCELABLEARRAY.equals(it)) {
                    Type superType = field.getGenericType();
                    if (!(superType instanceof ParameterizedType)) {
                        continue;
                    }
                    ParameterizedType parameterizedType = (ParameterizedType) superType;
                    Type[] argumentTypes = parameterizedType.getActualTypeArguments();
                    if (argumentTypes == null || argumentTypes.length != 1) {
                        continue;
                    }
                    try {
                        boolean isMatch = false;
                        if (InstanceType.SPARSEPARCELABLEARRAY.equals(it)) {
                            ((Class<?>) parameterizedType.getRawType()).asSubclass(SparseArray.class);
                            ((Class<?>) argumentTypes[0]).asSubclass(Parcelable.class);
                            isMatch = true;
                        } else {
                            ((Class<?>) parameterizedType.getRawType()).asSubclass(ArrayList.class);
                            if (InstanceType.STRINGARRAYLIST.equals(it)) {
                                ((Class<?>) argumentTypes[0]).asSubclass(String.class);
                                isMatch = true;
                            } else if (InstanceType.PARCELABLEARRAYLIST.equals(it)) {
                                ((Class<?>) argumentTypes[0]).asSubclass(Parcelable.class);
                                isMatch = true;
                            } else if (InstanceType.INTEGERARRAYLIST.equals(it)) {
                                ((Class<?>) argumentTypes[0]).asSubclass(Integer.class);
                                isMatch = true;
                            } else if (InstanceType.CHARSEQUENCEARRAYLIST.equals(it)) {
                                ((Class<?>) argumentTypes[0]).asSubclass(CharSequence.class);
                                isMatch = true;
                            }
                        }
                        if (isMatch) {
                            instanceType = it;
                            break;
                        }
                    } catch (ClassCastException e) {
                        continue;
                    }
                } else {
                    if (it.getName() != null) {
                        if (it.getName().equals(fieldType.getName())) {
                            instanceType = it;
                            break;
                        }
                    }
                    if (it.getClz() != null) {
                        try {
                            fieldType.asSubclass(it.getClz());
                            instanceType = it;
                            break;
                        } catch (ClassCastException e) {
                            continue;
                        }
                    }
                }
            }
        } else {
            instanceType = type;
        }
        return instanceType;
    }

}
