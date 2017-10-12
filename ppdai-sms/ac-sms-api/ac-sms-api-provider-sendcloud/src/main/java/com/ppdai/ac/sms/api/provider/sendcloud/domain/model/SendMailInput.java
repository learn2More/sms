package com.ppdai.ac.sms.api.provider.sendcloud.domain.model;

public class SendMailInput {
	private static String apiUser;  //邮件user
	private static String apiKey;  // 邮件key
	private String fromName;   //发件人名称
	private String from;   //发件人地址
	private String toAddress;            //收件人地址
	private String subject;  //发件人主题
	private  String html;    //邮件内容

	private  String  recordId;

	public static String getApiUser() {
		return apiUser;
	}

	public static void setApiUser(String apiUser) {
		SendMailInput.apiUser = apiUser;
	}

	public static String getApiKey() {
		return apiKey;
	}

	public static void setApiKey(String apiKey) {
		SendMailInput.apiKey = apiKey;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
}