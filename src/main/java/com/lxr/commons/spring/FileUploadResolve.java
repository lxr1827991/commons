package com.lxr.commons.spring;

import java.util.List;

public interface FileUploadResolve {
	
	/**
	 * 判断是否支持上传
	 * 
	 * @param fileName 文件名
	 * @param size 文件大小
	 * @return 不支持上传的原因，为null则支持上传
	 */
	String unUpload(String fileName,long size);

	/**
	 * 
	 * @param fileName
	 * @param size
	 * @return 返回[文件名系统路径，文件访问路径]
	 */
	String[] resolve(String fileName,long size);
	
	
	/**
	 * 
	 * @return
	 */
	List<String> resolveFileNames(String key,List<String> names);
	
}
