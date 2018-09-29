package com.lxr.commons.spring;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Null;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import com.lxr.commons.exception.ApplicationException;
import com.lxr.commons.web.WebContext;

public class SpringFileupload {
	
	static FileUploadResolve defResolve;
	
	
	static {
		
		defResolve = new DefaultFileUploadResolve();
		
	}
	
	
	
	/**
	 * 默认上传
	 * @param file
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public static String upload(MultipartFile  file,String rootPath) throws IllegalStateException, IOException {
		 
		return upload(file, defResolve,rootPath);

	}
	
	/**
	 * 
	 * @param file
	 * @param resolve 上传路径决策
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public static String upload(MultipartFile  file,FileUploadResolve resolve,String rootPath) throws IllegalStateException, IOException {
		 if (file.isEmpty()) return null;
		 if(StringUtils.isEmpty(rootPath))rootPath = "";
	           
		 String msg = resolve.unUpload(file.getOriginalFilename(), file.getSize());
		 
		 if(msg!=null)throw new ApplicationException(msg);
		 
	     String[] upPath = resolve.resolve(file.getOriginalFilename(), file.getSize());
	      if(upPath==null||upPath.length!=2)throw new ApplicationException("文件上传路径错误："+Arrays.toString(upPath));
	      
	      if(!new File(upPath[0]).exists())
          	new File(upPath[0]).mkdirs();
	      
	   // 转存文件到指定的路径
          file.transferTo(new File(upPath[0]));
	      
		return rootPath+upPath[1];

	}
	
	
	/**
	 * 
	 * @param request
	 * @param name
	 * @return
	 */
	public static String upload(HttpServletRequest request,String name,String rootPath) {
		 CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
	       
		  String path = null;
	        // 判断是否有文件上传  
	        if (!commonsMultipartResolver.isMultipart(request))throw new MultipartException("Could not parse multipart servlet request");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;         
		
	try {         
		// 获得文件：
		MultipartFile mfile = (MultipartFile) multipartRequest.getFile(name); 
	    
		return upload(mfile,rootPath);
		}  catch (Exception e1) {
			throw new ApplicationException(e1);
		}
			

	}
	
	/**
	 * 判断是否有文件
	 * @param request
	 * @param name
	 * @return
	 */
	public static boolean isFile(HttpServletRequest request,String name) {
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
	       
		  String path = null;
	        // 判断是否有文件上传  
	        if (!commonsMultipartResolver.isMultipart(request))return false;
	
	        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;         
			// 获得文件：
			MultipartFile mfile = (MultipartFile) multipartRequest.getFile(name);
		
			if(mfile==null)return false;
			
			return true;

	}
	
	
	
	/**
	 * 同名文件上传
	 * @param request
	 * @param name
	 * @return
	 */
	public static String[] uploadFiles(HttpServletRequest request,String name,String rootPath) {
		 CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
	       
		  String path = null;
	        // 判断是否有文件上传  
	        if (!commonsMultipartResolver.isMultipart(request))throw new MultipartException("Could not parse multipart servlet request");
	        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;         
		
	try {
		// 获得文件：
		List<MultipartFile> mfile =  multipartRequest.getFiles(name); 
	    String[] files = new String[mfile.size()];
	    for (int i = 0; i < files.length; i++) {
	    	files[i] = upload(mfile.get(i),rootPath);
		}
	    return files;
		}  catch (Exception e1) {
			throw new ApplicationException(e1);
		}
	}
	
	
	public static String[] uploadStandardFiles(HttpServletRequest request,String name,String rootPath) {
		
		if(StringUtils.isEmpty(rootPath))rootPath = "";
		
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
	       
		  String path = null;
	        // 判断是否有文件上传  
	        if (!commonsMultipartResolver.isMultipart(request))throw new MultipartException("Could not parse multipart servlet request");
	        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
		
		List<String> names = copyIterator(multipartRequest.getFileNames());
		List<String> mNames = defResolve.resolveFileNames(name, names);
		
		if(mNames==null) return new String[0] ;
		
		 String[] files = new String[mNames.size()];
		    for (int i = 0; i < mNames.size(); i++) {
		    	files[i] =  upload(request,mNames.get(i),rootPath);
			}
		return files;
		
		
	}
	
	public static String[] uploadStandardFilesOnUpdate(HttpServletRequest request,String name,String rootPath) {
		
		if(StringUtils.isEmpty(rootPath))rootPath = "";
		List<String> fnames = null;
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
		if (commonsMultipartResolver.isMultipart(request)){
			fnames = copyIterator(((MultipartHttpServletRequest)request).getFileNames());
	        }
		
		List<String> names = enumeration2List(request.getParameterNames());
		
		if(fnames!=null)names.addAll(fnames);
		List<String> mNames = defResolve.resolveFileNames(name, names);
		
		if(mNames==null) return new String[0] ;
		
		 String[] files = new String[mNames.size()];
		    for (int i = 0; i < mNames.size(); i++) {
		    	if(isFile(request, mNames.get(i)))
		    	files[i] =  upload(request,mNames.get(i),rootPath);
		    	else files[i] = request.getParameter(mNames.get(i));
			}
		return files;
		
		
	}
	
	private static <T> List<T> enumeration2List(Enumeration<T> en) {
		 List<T> copy = new ArrayList<T>();
		while (en.hasMoreElements()) {
			T object = (T) en.nextElement();
			copy.add(object);
		}
		return copy;
	}
	
	
	public static <T> List<T> copyIterator(Iterator<T> iter) {
	    List<T> copy = new ArrayList<T>();
	    while (iter.hasNext())
	        copy.add(iter.next());
	    return copy;

	}
	
	
}
