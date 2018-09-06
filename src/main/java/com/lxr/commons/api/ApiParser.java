package com.lxr.commons.api;

public abstract class ApiParser <T>  {
	
	public ApiParser() {
		super();
		ApiUtils.apiParser = this;
	}



	public void setCaller(Caller caller) {
		
		ApiUtils.threadCaller.set(caller);

	}
	

	
	public void setAppInterface(ApiInterface apiInterface) {
		
		ApiUtils.threadApiInterface.set(apiInterface);

	}
	
	public abstract void configCaller(T  obj);
	
	
	public abstract <T> T createToken(Caller caller) ;
	
	/**
	 * 
	 * @param caller
	 * @param l 单位毫秒
	 * @return
	 */
	public abstract <T> T createToken(Caller caller,long l) ;
	
	
	public void requestEnd() {
		
		ApiUtils.threadCaller.remove();

	}
	
}
