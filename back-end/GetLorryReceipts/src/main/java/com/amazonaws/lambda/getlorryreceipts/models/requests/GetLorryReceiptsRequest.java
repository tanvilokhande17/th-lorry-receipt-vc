package com.amazonaws.lambda.getlorryreceipts.models.requests;

import java.util.Map;

public class GetLorryReceiptsRequest {

	private Map<String, String> querystring;

	private Map<String, String> header;

	public Map<String, String> getQuerystring() {
		return querystring;
	}

	public void setQuerystring(Map<String, String> querystring) {
		this.querystring = querystring;
	}

	public Map<String, String> getHeader() {
		return header;
	}

	public void setHeader(Map<String, String> header) {
		this.header = header;
	}
}
