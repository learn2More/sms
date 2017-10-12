package com.ppdai.ac.sms.api.provider.sendcloud.protocol.response;

import net.sf.json.JSONObject;

public class SendResponseData {

	private boolean result;
	private int statusCode;
	private String message;
	private SendResponseInfo info;

	public String toString() {
		return JSONObject.fromObject(this).toString();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean getResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public SendResponseInfo getInfo() {
		return info;
	}

	public void setInfo(SendResponseInfo info) {
		this.info = info;
	}

}