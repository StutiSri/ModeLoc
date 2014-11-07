package com.example.automodechange;

public class ListModel {
	
	private String Name = "";
	private String Mode="";
	private String Radius = "";
	/**************setter***********/
	
public void setName(String Name)

{
	this.Name=Name;
	
}

public void setMode(String Mode)

{
	this.Mode = Mode;
	
}

public void setRadius(String Radius)

{
	this.Radius = Radius;
	
}

/**********getter***********/

public String getName()

{
	return this.Name;
	
}
public String getMode()

{
	return this.Mode;
	
}

public String getRadius()

{
	return this.Radius;
	
}
}
