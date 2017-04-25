package com.example.android_heart;

import java.io.ByteArrayOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import android.R.integer;

public class CodeFormat {
	
  static String dataOne ;
  /* 
	* 16杩涘埗鏁板瓧瀛楃闆� 
	*/ 
	private static String hexString="0123456789ABCDEF"; 
	/* 
	* 灏嗗瓧绗︿覆缂栫爜鎴�16杩涘埗鏁板瓧,閫傜敤浜庢墍鏈夊瓧绗︼紙鍖呮嫭涓枃锛� 
	*/ 
	public static String encode(String str) 
	{  
		dataOne = str;
	//鏍规嵁榛樿缂栫爜鑾峰彇瀛楄妭鏁扮粍 
	byte[] bytes=str.getBytes(); 
	StringBuilder sb=new StringBuilder(bytes.length*2); 
	//灏嗗瓧鑺傛暟缁勪腑姣忎釜瀛楄妭鎷嗚В鎴�2浣�16杩涘埗鏁存暟 
	for(int i=0;i<bytes.length;i++) 
	{ 
	sb.append(hexString.charAt((bytes[i]&0xf0)>>4)); 
	sb.append(hexString.charAt((bytes[i]&0x0f)>>0)+" "); 
	} 
	 
	  return sb.toString(); 
	  
	} 
	/* 
	* 灏�16杩涘埗鏁板瓧瑙ｇ爜鎴愬瓧绗︿覆,閫傜敤浜庢墍鏈夊瓧绗︼紙鍖呮嫭涓枃锛� 
	*/ 
	public static String decode(String bytes)
	{
		
	ByteArrayOutputStream baos=new ByteArrayOutputStream(bytes.length()/2);
	//灏嗘瘡2浣�16杩涘埗鏁存暟缁勮鎴愪竴涓瓧鑺�
	for(int i=0;i<bytes.length();i+=2)
	baos.write((hexString.indexOf(bytes.charAt(i))<<4 |hexString.indexOf(bytes.charAt(i+1))));
	return new String(baos.toByteArray());
	
	}
	
	 public   static   String StringFilter(String   str)   throws   PatternSyntaxException   {      
		               // 鍙厑璁稿瓧姣嶅拰鏁板瓧        
		               // String   regEx  =  "[^a-zA-Z0-9]";                      
		               // 娓呴櫎鎺夋墍鏈夌壒娈婂瓧绗�   
		          String regEx="[`~!@#$%^&*()+=|{}':;',//[//].<>/?~锛丂#锟�%鈥︹��&*锛堬級鈥曗��+|{}銆愩�戔�橈紱锛氣�濃�溾�欍�傦紝銆侊紵]";   
		         Pattern   p   =   Pattern.compile(regEx);      
		          Matcher   m   =   p.matcher(str);      
		         return   m.replaceAll("").trim();      
    }
	 /** 
	銆�銆�* Convert byte[] to hex string.杩欓噷鎴戜滑鍙互灏哹yte杞崲鎴恑nt锛岀劧鍚庡埄鐢↖nteger.toHexString(int)鏉ヨ浆鎹㈡垚16杩涘埗瀛楃涓层��

	銆�銆�* @param src byte[] data

	銆�銆�* @return hex string

	銆�銆�*/

	public static String bytesToHexString(byte[] src){

	StringBuilder stringBuilder = new StringBuilder("");
	if (src == null || src.length <= 0) {

	 return null;

	}

	for (int i = 0; i < 20; i++) {
    
	int v = src[i] & 0xFF;

	String hv = Integer.toHexString(v);

	if (hv.length() < 2) {

	 stringBuilder.append(0);
     System.out.println(stringBuilder);
	}

	 stringBuilder.append(hv);

	}

	return stringBuilder.toString();

}

	/** *//** 
	    * 鎶婂瓧鑺傛暟缁勮浆鎹㈡垚16杩涘埗瀛楃涓� 
	    * @param bArray 
	    * @return 
	    */ 
	public static final String bytesToHexStringTwo(byte[] bArray,int count) { 
	    StringBuffer sb = new StringBuffer(bArray.length); 
	    String sTemp; 
	    for (int i = 0; i < count; i++) { 
	     sTemp = Integer.toHexString(0xFF & bArray[i]); 
	     if (sTemp.length() < 2) 
	      sb.append(0); 
	     sb.append(sTemp.toUpperCase()); 
	    } 
	    return sb.toString(); 
	}


	 
	 //鍒嗗壊瀛楃涓�
      public static  String  Stringspace(String str){
    	 
			String temp=""; 
			String temp2="";
			for(int i=0;i<str.length();i++) 
			{  
				
				if (i%2==0) {
				  temp=str.charAt(i)+"";
				  temp2+=temp;
			      System.out.println(temp);
				}else {
					temp2+=str.charAt(i)+" ";
				}
				
			}  
    	 return temp2;
      }
      /**
  	 * Byte -> Hex
  	 * 
  	 * @param bytes
  	 * @return
  	 */
  	public static String byteToHex(byte[] bytes, int count) {
  		StringBuffer sb = new StringBuffer();
  		for (int i = 0; i < count; i++) {
  			String hex = Integer.toHexString(bytes[i] & 0xFF);
  			if (hex.length() == 1) {
  				hex = '0' + hex;
  			}
  			sb.append(hex).append(" ");
  		}
  		return sb.toString();
  	}
  	/**
	 * String -> Hex
	 * 
	 * @param s
	 * @return
	 */
	public static String stringToHex(String s) {
		String str = "";
		for (int i = 0; i < s.length(); i++) {
			int ch = (int) s.charAt(i);
			String s4 = Integer.toHexString(ch);
			if (s4.length() == 1) {
				s4 = '0' + s4;
			}
			str = str + s4 + " ";
		}
		return str;
	}
	
 }
    