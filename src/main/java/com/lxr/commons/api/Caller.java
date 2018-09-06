package com.lxr.commons.api;

public class Caller {

	//密码登录
	public static final int AUTH_TYPE_PWD = 1;
	//第三方登录
	public static final int AUTH_TYPE_OPEN = 2;
	
	String uid;
	String account;
	Integer authType;
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Integer getAuthType() {
		return authType;
	}
	public void setAuthType(Integer authType) {
		this.authType = authType;
	}
	
	
	
	
}
