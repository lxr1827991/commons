package com.lxr.commons.spring;


import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.lxr.commons.annotation.AutoUpload;

public class FileUploadArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		
		 // 过滤出符合条件的参数
        if (parameter.hasParameterAnnotation(AutoUpload.class)) {
            return true;
        }
        return false;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		//数组上传
		 if(parameter.getParameterType().equals(String[].class)) {
			 HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
			 
			 AutoUpload autoUpload = parameter.getParameterAnnotation(AutoUpload.class);
			 //是否标准上传
			 if(autoUpload.standard()) {
				 return SpringFileupload.uploadStandardFiles(request, parameter.getParameterName());
			 }else {
				 if(!SpringFileupload.isFile(request, parameter.getParameterName()))return null;
				 
				 return SpringFileupload.uploadFiles(request, parameter.getParameterName());
				 
			 }
			 
			 
			 
		 }
		 //单文件上传
		 if(parameter.getParameterType().equals(String.class)) {
			 HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
			 
			 if(!SpringFileupload.isFile(request, parameter.getParameterName()))return null;
			 
			 return SpringFileupload.upload(request, parameter.getParameterName());
			 
		 }
		 
		 
		throw new IllegalStateException("文件上传不支持的类型");
	}



}