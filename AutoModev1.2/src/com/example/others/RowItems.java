package com.example.others;

public class RowItems{
	
	private String locName, mode;
	private boolean isEnabled;
	
	public RowItems(String locName, String mode,boolean isEnabled){
		this.locName=locName;
		this.isEnabled=isEnabled;
		this.mode=mode;
	}
	public void setLocName(String locName){
		this.locName=locName;
	}
	public void setIsEnabled(boolean isEnabled){
		this.isEnabled=isEnabled;
	}
	public void setMode(String mode){
		this.mode=mode;
	}
	
	public String getLocName(){
		return locName;
	}
	public boolean getIsEnabled(){
		return isEnabled;
	}
	public String getMode(){
		return mode;
	}
}
