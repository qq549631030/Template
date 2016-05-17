package com.hx.template.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface SaveInstanceAnnotation {
	/**
	 * 保存到Bundle里面的key，默认为字段名FieldName
	 */
	String key() default "";
	
	/**
	 * 保存字段的类型，默认为自动识别。也可指定InstanceType中的类型，具体类型说明，请参考InstanceType中的定义。
	 */
	InstanceType type() default InstanceType.AUTORECOGNIZE;
}

