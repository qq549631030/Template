package com.hx.template.http;

import org.json.JSONObject;

/**
 * 
 * @ClassName: HttpListener
 * @Description: HTTP请求监听
 * @author huangxiang
 * @date 2015-3-11 下午7:22:17
 */
public abstract class HttpListener {

	/**
	 * 请求成功回调
	 * 
	 * @param jsonObject
	 *            返回JSON数据
	 * @return void
	 */
	public abstract void onPass(JSONObject jsonObject);

	/**
	 * 请求错误回调
	 * 
	 * @param ErrorMsg
	 *            错误信息
	 * @param errorCode
	 *            错误代码
	 * @return void
	 */
	public abstract void onError(String ErrorMsg, int errorCode);

}
