package com.lxr.commons.utils;


import java.security.KeyFactory;  
import java.security.PrivateKey;  
import java.security.PublicKey;  
import java.security.spec.PKCS8EncodedKeySpec;  
import java.security.spec.X509EncodedKeySpec;  
 
 
 
/** 
* RSAǩ����ǩ�� 
*/  
public class RSASignature{  
     
   /** 
    * ǩ���㷨 
    */  
   public static final String SIGN_ALGORITHMS = "SHA1WithRSA";  
 
   /** 
   * RSAǩ�� 
   * @param content ��ǩ������ 
   * @param privateKey �̻�˽Կ 
   * @param encode �ַ������� 
   * @return ǩ��ֵ 
   */  
   public static String sign(String content, String privateKey, String encode)  
   {  
       try   
       {  
           PKCS8EncodedKeySpec priPKCS8    = new PKCS8EncodedKeySpec( Base64Utils.decode(privateKey).getBytes() );   
             
           KeyFactory keyf                 = KeyFactory.getInstance("RSA");  
           PrivateKey priKey               = keyf.generatePrivate(priPKCS8);  
 
           java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);  
 
           signature.initSign(priKey);  
           signature.update( content.getBytes(encode));  
 
           byte[] signed = signature.sign();  
             
           return Base64Utils.encode(new String(signed));  
       }  
       catch (Exception e)   
       {  
           e.printStackTrace();  
       }  
         
       return null;  
   }  
     
   public static String sign(String content, String privateKey)  
   {  
       try   
       {  
           PKCS8EncodedKeySpec priPKCS8    = new PKCS8EncodedKeySpec( Base64Utils.decode(privateKey).getBytes() );   
           KeyFactory keyf = KeyFactory.getInstance("RSA");  
           PrivateKey priKey = keyf.generatePrivate(priPKCS8);  
           java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);  
           signature.initSign(priKey);  
           signature.update( content.getBytes());  
           byte[] signed = signature.sign();  
           return Base64Utils.encode(new String(signed));  
       }  
       catch (Exception e)   
       {  
           e.printStackTrace();  
       }  
       return null;  
   }  
     
   /** 
   * RSA��ǩ����� 
   * @param content ��ǩ������ 
   * @param sign ǩ��ֵ 
   * @param publicKey ����������̹�Կ 
   * @param encode �ַ������� 
   * @return ����ֵ 
   */  
   public static boolean doCheck(String content, String sign, String publicKey,String encode)  
   {  
       try   
       {  
           KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
           byte[] encodedKey = Base64Utils.decode(publicKey).getBytes();  
           PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));  
 
         
           java.security.Signature signature = java.security.Signature  
           .getInstance(SIGN_ALGORITHMS);  
         
           signature.initVerify(pubKey);  
           signature.update( content.getBytes(encode) );  
         
           boolean bverify = signature.verify( Base64Utils.decode(sign).getBytes() );  
           return bverify;  
             
       }   
       catch (Exception e)   
       {  
           e.printStackTrace();  
       }  
         
       return false;  
   }  
     
   public static boolean doCheck(String content, String sign, String publicKey)  
   {  
       try   
       {  
           KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
           byte[] encodedKey = Base64Utils.decode(publicKey).getBytes();  
           PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));  
 
         
           java.security.Signature signature = java.security.Signature  
           .getInstance(SIGN_ALGORITHMS);  
         
           signature.initVerify(pubKey);  
           signature.update( content.getBytes() );  
         
           boolean bverify = signature.verify( Base64Utils.decode(sign).getBytes() );  
           return bverify;  
             
       }   
       catch (Exception e)   
       {  
           e.printStackTrace();  
       }  
         
       return false;  
   }  
     
   
}