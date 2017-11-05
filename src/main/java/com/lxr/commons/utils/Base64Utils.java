package com.lxr.commons.utils;

import org.apache.commons.codec.binary.Base64;

public final class Base64Utils {  
	 public static String encode(String plainText){  
	        byte[] b=plainText.getBytes();  
	        Base64 base64=new Base64();  
	        b=base64.encode(b);  
	        String s=new String(b);  
	        return s;  
	    }
	 
	 public static String decode(String encodeStr){  
	        byte[] b=encodeStr.getBytes();  
	        Base64 base64=new Base64();  
	        b=base64.decode(b);  
	        String s=new String(b);  
	        return s;  
	    }  
}  