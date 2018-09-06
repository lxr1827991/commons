package com.lxr.commons.api;


public class ApiUtils {

	protected static ApiParser apiParser = null;
	
	protected static ThreadLocal<Caller> threadCaller = new ThreadLocal<>();
	
	protected static ThreadLocal<ApiInterface> threadApiInterface = new ThreadLocal<>();
	
	
	public static Caller getCaller() {
		
		return threadCaller.get();
	}
	
	
	public static ApiInterface getAppInterface() {
		return threadApiInterface.get();

	}
	
	/**
	 * 颁发token
	 * @param caller
	 */
	public static <T> T awardToken(Caller caller,Class<T> cls) {
		
		return (T)apiParser.createToken(caller);
	}
}
