package com.amazonaws.lambda.actionOnLorryReceipt.constants;

public class Constants {

	public static final String API_METHOD_GET = "GET";
	public static final String API_METHOD_POST = "POST";
	public static final String REQUEST_HEADER_AUTHORIZATION = "Authorization";
	public static final String REQUEST_HEADER_ACCEPT = "Accept";
	public static final String REQUEST_HEADER_CONTENT_TYPE = "Content-Type";
	public static final String CONTENT_TYPE_APPLICATION_JSON = "application/json";
	public static final String USER_ROLE_TRANSPORTER = "TRANSPORTER";
	public static final String USER_ROLE_CONSIGNER = "CONSIGNER";
	public static final String USER_ROLE_CONSIGNEE = "CONSIGNEE";
	public static final String USER_ROLE_DRIVER = "DRIVER";
	public static final String LORRYRECEIPT_NUMBER_PREFIX = "LRN";
	public static final String LORRYRECEIPT_STATUS_BOOKED = "BOOKED";
	public static final String LORRYRECEIPT_STATUS_CANCELLED = "CANCELLED";
	public static final String LORRYRECEIPT_STATUS_PICKEDUP = "PICKEDUP";
	public static final String LORRYRECEIPT_STATUS_INTRANSIT = "INTRANSIT";
	public static final String LORRYRECEIPT_STATUS_DELAYED = "DELAYED";
	public static final String LORRYRECEIPT_STATUS_DELIVERED = "DELIVERED";
	public static final String LORRYRECEIPT_STATUS_COMPLETED = "COMPLETED";
	public static final String LORRYRECEIPT_ACTION_APPROVE = "APPROVE";
	public static final String LORRYRECEIPT_ACTION_CANCELLED = "CANCELLED";
	public static final String LORRYRECEIPT_ACTION_PICKUP = "PICKUP";
	public static final String LORRYRECEIPT_ACTION_DELAYED = "DELAYED";
	public static final String LORRYRECEIPT_ACTION_INTRANSIT = "INTRANSIT";
	public static final String LORRYRECEIPT_ACTION_DELIVERY = "DELIVERY";
	public static final String LORRYRECEIPT_ACTION_DECLINED = "DECLINED";
}
