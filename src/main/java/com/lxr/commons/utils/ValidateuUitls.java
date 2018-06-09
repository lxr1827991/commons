package com.lxr.commons.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class ValidateuUitls {

	 /** 
    * ��½�ֻ�����11λ����ƥ���ʽ��ǰ��λ�̶���ʽ+��8λ������ 
    * �˷�����ǰ��λ��ʽ�У� 
    * 13+������ 
    * 15+��4�������� 
    * 18+��1��4�������� 
    * 17+��9�������� 
    * 147 
    */  
   public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {  
       String regExp = "^((13[0-9])|(15[^4])|(18[1,0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";  
       Pattern p = Pattern.compile(regExp);  
       Matcher m = p.matcher(str);  
       return m.matches();  
   }  
 
   /** 
    * ����ֻ�����8λ����5|6|8|9��ͷ+7λ������ 
    */  
   public static boolean isHKPhoneLegal(String str)throws PatternSyntaxException {  
       String regExp = "^(5|6|8|9)\\d{7}$";  
       Pattern p = Pattern.compile(regExp);  
       Matcher m = p.matcher(str);  
       return m.matches();  
   } 
	
   
   /**
    *��֤����
    *��������Դ����
    * @param vehicleNumber
    * @return
    */
   public static boolean isVehicleNumber(String vehicleNumber)

   {
   	
   	if(vehicleNumber==null)
   		return false;

       
       String regExp = "^[�����弽ԥ���ɺ�����³������Ӷ���ʽ����¼���������ش�����ʹ��A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9��ѧ���۰�]{1,2}$";
 
       Pattern p = Pattern.compile(regExp);  
       Matcher m = p.matcher(vehicleNumber);  
       return m.matches();  

   }
   
   
   /**
    *�ж��ǲ���32λmd5
    * @param vehicleNumber
    * @return
    */
   public static boolean has32Md5(String md5)
   {

       
       if(md5==null||md5.length()!=32)return false;
       
       return true;  

   }
   
   public static void main(String[] args) {
   	int[] vars = new int[]{134,135,136,137,138,139,150,151,152,158,159,157,182,187,188,147,130,131,132,155,156,185,186
   			,180,189,133,153};
   	
   	for (int i = 0; i < vars.length; i++) {
   		if(!isChinaPhoneLegal(vars[i]+"70824615"))
   			System.out.println(vars[i]);
   			
		}
   	
   	
		
	}
   
}
