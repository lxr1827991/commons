package com.lxr.commons.spring;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import com.lxr.commons.exception.ApplicationException;
import com.lxr.commons.utils.SortUtils;
import com.lxr.commons.web.WebContext;

public class DefaultFileUploadResolve implements FileUploadResolve{

	@Override
	public String unUpload(String fileName, long size) {
		return null;
	}

	@Override
	public String[] resolve(String fileName, long size) {
		 // 文件名
        try {
			fileName = java.net.URLEncoder.encode(fileName,"utf-8").replace("%", "");
		} catch (UnsupportedEncodingException e) {
			throw new ApplicationException(e);
		}
         //上传目录 （upload/项目名/yyyy-MM-dd）
        String webroot = "upload"+((WebContext.getContextPath()==null||"/".equals(WebContext.getContextPath()))?"":WebContext.getContextPath())+
        		"/"+DateFormatUtils.format(new Date(), "yyyy-MM-dd");
        //上传目录系统目录
        String realPath= new File(getTomcatHost(),"webapps/"+webroot).getPath();
        //创建上传文件名
        String trueFileName= genFileName(fileName);
                
                
		return new String[]{realPath+"/"+trueFileName,"/"+webroot+"/"+trueFileName};
	}

	
	public static String genFileName(String fileName){
		String trueFileName=String.valueOf(System.currentTimeMillis())+fileName;
		
		return trueFileName;
	}
	
	public static String getTomcatHost() {
		return System.getProperty("catalina.home");

	}

	@Override
	public List<String> resolveFileNames(String key, List<String> names) {
		
		List<String> list = new ArrayList<>();
		
		for (int i = 0; i < names.size(); i++) {
			if(names.get(i).startsWith(key+"[")&&names.get(i).endsWith("]"))
				list.add(names.get(i));
		}
		
		return SortUtils.asciiSort(list);
	}

}
