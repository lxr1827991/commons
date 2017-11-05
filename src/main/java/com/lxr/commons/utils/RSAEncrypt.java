package com.lxr.commons.utils;


import java.io.BufferedReader;  
import java.io.BufferedWriter;  
import java.io.FileReader;  
import java.io.FileWriter;  
import java.io.IOException;  
import java.security.InvalidKeyException;  
import java.security.KeyFactory;  
import java.security.KeyPair;  
import java.security.KeyPairGenerator;  
import java.security.NoSuchAlgorithmException;  
import java.security.SecureRandom;  

import java.security.interfaces.RSAPrivateKey;  
import java.security.interfaces.RSAPublicKey;  
import java.security.spec.InvalidKeySpecException;  
import java.security.spec.PKCS8EncodedKeySpec;  
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

import javax.crypto.BadPaddingException;  
import javax.crypto.Cipher;  
import javax.crypto.IllegalBlockSizeException;  
import javax.crypto.NoSuchPaddingException;  


public class RSAEncrypt {  
  /** 
   * 字节数据转字符串专用集合 
   */  
  private static final char[] HEX_CHAR = { '0', '1', '2', '3', '4', '5', '6',  
          '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };  

  /** 
   * 随机生成密钥对 
   */  
  public static String[] genKeyPair(String filePath) {  
      // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象  
      KeyPairGenerator keyPairGen = null;  
      try {  
          keyPairGen = KeyPairGenerator.getInstance("RSA");  
      } catch (NoSuchAlgorithmException e) {  
          // TODO Auto-generated catch block  
          e.printStackTrace();  
      }  
      // 初始化密钥对生成器，密钥大小为96-1024位  
      keyPairGen.initialize(1024,new SecureRandom());  
      // 生成一个密钥对，保存在keyPair中  
      KeyPair keyPair = keyPairGen.generateKeyPair();  
      // 得到私钥  
      RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();  
      // 得到公钥  
      RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  
       
          // 得到公钥字符串  
          String publicKeyString = Base64Utils.encode(new String(publicKey.getEncoded()));  
          // 得到私钥字符串  
          String privateKeyString = Base64Utils.encode(new String(privateKey.getEncoded()));  
           
        return new String[]{publicKeyString,privateKeyString};
  }  

  /** 
   * 从文件中输入流中加载公钥 
   *  
   * @param in 
   *            公钥输入流 
   * @throws Exception 
   *             加载公钥时产生的异常 
   */  
  public static String loadPublicKeyByFile(String path) throws Exception {  
      try {  
          BufferedReader br = new BufferedReader(new FileReader(path  
                  + "/publicKey.keystore"));  
          String readLine = null;  
          StringBuilder sb = new StringBuilder();  
          while ((readLine = br.readLine()) != null) {  
              sb.append(readLine);  
          }  
          br.close();  
          return sb.toString();  
      } catch (IOException e) {  
          throw new Exception("公钥数据流读取错误");  
      } catch (NullPointerException e) {  
          throw new Exception("公钥输入流为空");  
      }  
  }  

  /** 
   * 从字符串中加载公钥 
   *  
   * @param publicKeyStr 
   *            公钥数据字符串 
   * @throws Exception 
   *             加载公钥时产生的异常 
   */  
  public static RSAPublicKey loadPublicKeyByStr(String publicKeyStr)  
          throws Exception {  
      try {  
          byte[] buffer = Base64Utils.decode(publicKeyStr).getBytes();  
          KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
          X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);  
          return (RSAPublicKey) keyFactory.generatePublic(keySpec);  
      } catch (NoSuchAlgorithmException e) {  
          throw new Exception("无此算法");  
      } catch (InvalidKeySpecException e) {  
          throw new Exception("公钥非法");  
      } catch (NullPointerException e) {  
          throw new Exception("公钥数据为空");  
      }  
  }  

  /** 
   * 从文件中加载私钥 
   *  
   * @param keyFileName 
   *            私钥文件名 
   * @return 是否成功 
   * @throws Exception 
   */  
  public static String loadPrivateKeyByFile(String path) throws Exception {  
      try {  
          BufferedReader br = new BufferedReader(new FileReader(path  
                  + "/privateKey.keystore"));  
          String readLine = null;  
          StringBuilder sb = new StringBuilder();  
          while ((readLine = br.readLine()) != null) {  
              sb.append(readLine);  
          }  
          br.close();  
          return sb.toString();  
      } catch (IOException e) {  
          throw new Exception("私钥数据读取错误");  
      } catch (NullPointerException e) {  
          throw new Exception("私钥输入流为空");  
      }  
  }  

  public static RSAPrivateKey loadPrivateKeyByStr(String privateKeyStr)  
          throws Exception {  
      try {  
          byte[] buffer = Base64Utils.decode(privateKeyStr).getBytes();  
          PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);  
          KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
          return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);  
      } catch (NoSuchAlgorithmException e) {  
          throw new Exception("无此算法");  
      } catch (InvalidKeySpecException e) {  
          throw new Exception("私钥非法");  
      } catch (NullPointerException e) {  
          throw new Exception("私钥数据为空");  
      }  
  }  

  /** 
   * 公钥加密过程 
   *  
   * @param publicKey 
   *            公钥 
   * @param plainTextData 
   *            明文数据 
   * @return 
   * @throws Exception 
   *             加密过程中的异常信息 
   */  
  public static byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData)  
          throws Exception {  
      if (publicKey == null) {  
          throw new Exception("加密公钥为空, 请设置");  
      }  
      Cipher cipher = null;  
      try {  
          // 使用默认RSA  
          cipher = Cipher.getInstance("RSA");  
          // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());  
          cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
          byte[] output = cipher.doFinal(plainTextData);  
          return output;  
      } catch (NoSuchAlgorithmException e) {  
          throw new Exception("无此加密算法");  
      } catch (NoSuchPaddingException e) {  
          e.printStackTrace();  
          return null;  
      } catch (InvalidKeyException e) {  
          throw new Exception("加密公钥非法,请检查");  
      } catch (IllegalBlockSizeException e) {  
          throw new Exception("明文长度非法");  
      } catch (BadPaddingException e) {  
          throw new Exception("明文数据已损坏");  
      }  
  }  

  /** 
   * 私钥加密过程 
   *  
   * @param privateKey 
   *            私钥 
   * @param plainTextData 
   *            明文数据 
   * @return 
   * @throws Exception 
   *             加密过程中的异常信息 
   */  
  public static byte[] encrypt(RSAPrivateKey privateKey, byte[] plainTextData)  
          throws Exception {  
      if (privateKey == null) {  
          throw new Exception("加密私钥为空, 请设置");  
      }  
      Cipher cipher = null;  
      try {  
          // 使用默认RSA  
          cipher = Cipher.getInstance("RSA");  
          cipher.init(Cipher.ENCRYPT_MODE, privateKey);  
          byte[] output = cipher.doFinal(plainTextData);  
          return output;  
      } catch (NoSuchAlgorithmException e) {  
          throw new Exception("无此加密算法");  
      } catch (NoSuchPaddingException e) {  
          e.printStackTrace();  
          return null;  
      } catch (InvalidKeyException e) {  
          throw new Exception("加密私钥非法,请检查");  
      } catch (IllegalBlockSizeException e) {  
          throw new Exception("明文长度非法");  
      } catch (BadPaddingException e) {  
          throw new Exception("明文数据已损坏");  
      }  
  }  

  /** 
   * 私钥解密过程 
   *  
   * @param privateKey 
   *            私钥 
   * @param cipherData 
   *            密文数据 
   * @return 明文 
   * @throws Exception 
   *             解密过程中的异常信息 
   */  
  public static byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData)  
          throws Exception {  
      if (privateKey == null) {  
          throw new Exception("解密私钥为空, 请设置");  
      }  
      Cipher cipher = null;  
      try {  
          // 使用默认RSA  
          cipher = Cipher.getInstance("RSA");  
          // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());  
          cipher.init(Cipher.DECRYPT_MODE, privateKey);  
          byte[] output = cipher.doFinal(cipherData);  
          return output;  
      } catch (NoSuchAlgorithmException e) {  
          throw new Exception("无此解密算法");  
      } catch (NoSuchPaddingException e) {  
          e.printStackTrace();  
          return null;  
      } catch (InvalidKeyException e) {  
          throw new Exception("解密私钥非法,请检查");  
      } catch (IllegalBlockSizeException e) {  
          throw new Exception("密文长度非法");  
      } catch (BadPaddingException e) {  
          throw new Exception("密文数据已损坏");  
      }  
  }  

  /** 
   * 公钥解密过程 
   *  
   * @param publicKey 
   *            公钥 
   * @param cipherData 
   *            密文数据 
   * @return 明文 
   * @throws Exception 
   *             解密过程中的异常信息 
   */  
  public static byte[] decrypt(RSAPublicKey publicKey, byte[] cipherData)  
          throws Exception {  
      if (publicKey == null) {  
          throw new Exception("解密公钥为空, 请设置");  
      }  
      Cipher cipher = null;  
      try {  
          // 使用默认RSA  
          cipher = Cipher.getInstance("RSA");  
          // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());  
          cipher.init(Cipher.DECRYPT_MODE, publicKey);  
          byte[] output = cipher.doFinal(cipherData);  
          return output;  
      } catch (NoSuchAlgorithmException e) {  
          throw new Exception("无此解密算法");  
      } catch (NoSuchPaddingException e) {  
          e.printStackTrace();  
          return null;  
      } catch (InvalidKeyException e) {  
          throw new Exception("解密公钥非法,请检查");  
      } catch (IllegalBlockSizeException e) {  
          throw new Exception("密文长度非法");  
      } catch (BadPaddingException e) {  
          throw new Exception("密文数据已损坏");  
      }  
  }  

  /** 
   * 字节数据转十六进制字符串 
   *  
   * @param data 
   *            输入数据 
   * @return 十六进制内容 
   */  
  public static String byteArrayToString(byte[] data) {  
      StringBuilder stringBuilder = new StringBuilder();  
      for (int i = 0; i < data.length; i++) {  
          // 取出字节的高四位 作为索引得到相应的十六进制标识符 注意无符号右移  
          stringBuilder.append(HEX_CHAR[(data[i] & 0xf0) >>> 4]);  
          // 取出字节的低四位 作为索引得到相应的十六进制标识符  
          stringBuilder.append(HEX_CHAR[(data[i] & 0x0f)]);  
          if (i < data.length - 1) {  
              stringBuilder.append(' ');  
          }  
      }  
      return stringBuilder.toString();  
  }  
  
  
  public static byte[] encrypt(String puk,String content) throws Exception {
	return encrypt(loadPublicKeyByStr(puk), content.getBytes());

}
  
  
  
  public static void main(String[] args) throws Exception {
	  String puk = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQA/JoOC3P05ips/G4RRMD8wV+zfwEYOPyzg3j8I5WWqwVa2jz8tXKLnur/eRyw/M+2YPzwyYT8eo8Y/Hkdkptghsvx9ZuL5atPUQD8yPww/LSYijoLTySvV7HaymllgtIs/Js+PQ8mailZaeD88OD8OCz8+Py8sX0TQ6j8vLVQLPwIDAQAB";
	  String prk = "MD8CdgIBADANBgkqhkiG9w0BAQEFAAQ /AmAwPwJcAgEAAoGBAD8mg4Lc/TmKmz8bhFEwPzBX7N/ARg4/LODePwjlZarBVraPPy1coue6v95HLD8z7Zg/PDJhPx6jxj8eR2Sm2CGy/H1m4vlq09RAPzI/DD8tJiKOgtPJK9XsdrKaWWC0iz8mz49DyZqKVlp4Pzw4Pw4LPz4/LyxfRNDqPy8tVAs/AgMBAAECgYA5az8adFzP/s/2ULaGPwjJ/jAwNdbl39aN2XxhdEovNQJgPwofP46sL01gPyy6TTXg8zM/Ifywnsw/COGTVj8OnZfFT/xoHmWmlA7Gw8aojeAIK9xUjXEv7Ur4bOr1ZS1QlJ4wPySOWfHtmHILCz8cPzF2X2I/KDtPdJ7kW23roQJBAD8eVFSPbli+Q2bRjajDfXsEIIqmPxoXVniDdiL31kKZ2gIFPwdgAz87mXMiyUNpe12h9WnMwTwH32MQDDFN3P0CQQDs1/NlPz0roJU9Na/S70fJo3U7WDoB/UM/JqbrB1EET1I/AZHgYz8bo/q28JbV5llmluCytbGEDfrHy3LNUj8TAkEAqF57P3c7htqS5IvXPxh46kNwPwhLaT8nPwV0Vvy1rqm25mgdXnaC06q05Ljlq3FRYcxpCzDZ5kAW8Z0/DxVrPwJANJbe3Lhh9MX7XVQGbnc/IMrCPyKZ/j8Vv6pVdepx+tsCNM6EbabCPzBxAx344BX90+G8kvE/MLVJPwMiwqQ/MQJACj68+AtdPwgJq7RX1I+GuiQHPx8vbROauV6ymD8Uy7wnPzJOr6+n1Co/HZPuPwTyTKjs17bbUy0/EdrR65wXUA==";
	  
	  byte[] j = encrypt(puk, "1");
	  byte[] jm = decrypt(loadPublicKeyByStr(prk), j);
	  System.out.println(new String(jm));
	  
	  
	String[] p = genKeyPair("");
	
	System.out.println(Arrays.toString(p));
}
  
}