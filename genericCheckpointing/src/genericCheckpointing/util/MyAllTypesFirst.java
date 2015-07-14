package genericCheckpointing.util;

import java.lang.reflect.Field;

public class MyAllTypesFirst extends SerializableObject {
	private int myInt;
	private String myString;
	private double myDouble;
	private long myLong;
	private char myChar;
	private char myVal ='D';
	
	
	public MyAllTypesFirst (int i, String s, double d, long l, char c, char myVal)
	{
		myInt= i;
		myString = s;
		myDouble = d;
		myLong = l;
		myChar = c;
		myVal =myVal;
	}
	
public int getMyInt ()
{
return myInt;
}

public String getMyString ()
{
return myString;
}
public double getMyDouble ()
{
return myDouble;
}
public Long getMyLong ()
{
return myLong;
}
public char getMyChar ()
{
return myChar;
}


public void setMyInt (int i)
{
myInt =i;
}

public void getMyString (String s)
{
 myString = s;
}
public void getMyDouble (double d)
{
myDouble =d;
}
public void getMyLong (Long l)
{
myLong = l;
}
public void getMyChar (char c)
{
myChar = c;
}

}