package com.lxr.commons.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoUpload {

	String value() default "";
	
	/**
	 * 是否标准模式 上传文件数组
	 * @return
	 */
	boolean standard() default false;
}
