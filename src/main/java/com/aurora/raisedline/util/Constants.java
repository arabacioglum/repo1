package com.aurora.raisedline.util;


public class Constants {

	public static final int ID_MAX = 8;
	public static final int NAME_MAX = 40;
	public static final int DESCR_MAX = 255;
	
	public static enum PaymentMethod{
		TRANSFER("TRANSFER"),
		CREDIT_CARD("CREDIT_CARD"),
		ON_DELIVERY("ON_DELIVERY");
		
		private final String label;
		
		private PaymentMethod(String label){
			this.label = label;
		}
		
		public String getLabel(){
			return this.label;
		}
	}
}
