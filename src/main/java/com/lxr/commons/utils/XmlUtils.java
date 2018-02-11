package com.lxr.commons.utils;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XmlUtils {

	public static Map<String, String> xml2map(InputStream in) throws DocumentException {
		  SAXReader reader = new SAXReader();  
	        //读取文件 转换成Document  
	        Document document = reader.read(in);
	        
	        Map<String, String> map = new HashMap<>();
	        
	        Element root = document.getRootElement();
	        
	        Iterator<Element> iterator = root.elementIterator();
	        
	        while(iterator.hasNext()){  
	            Element e = iterator.next();  
	            map.put(e.getName(), e.getStringValue());
	        }  
	        
	        
	        return map;

	}
	
	
	public static void main(String[] args) throws DocumentException {
		System.out.println(xml2map(XmlUtils.class.getResourceAsStream("/config.xml")));
	}
	
}
