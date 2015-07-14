package genericCheckpointing.util;

import java.lang.reflect.Field;

public class SerializableObject {




@Override
public boolean equals(Object b)
{
	if ((this.hashCode()) ==b.hashCode())
      return true;
	else 
		return false;
}


@Override
public int hashCode()
{
	Object temp = this;
    long hashValue=0;
	
	try{
	  for (Field field : temp.getClass().getDeclaredFields())
  	  {
  		  field.setAccessible(true);
  		  
  		  String type = field.getType().toString();
          Object value = field.get(temp);
  		 		
  //	if(type.equals("int")||type.equals("float")||type.equals("long")||type.equals("short"))
  	if(type.equals("long"))
  		hashValue = (long)value*31 + 37+ hashValue;
  	
  	else if(type.equals("int"))
  	{		value= new Long((Integer)value);
  		hashValue = (long)value*31 + 37+ hashValue; }
  	
 /* 	else if(type.equals("float"))
  	{	hashValue = (long)value*31 + 37+ hashValue; }
  	
	else if(type.equals("double"))
  	{	
	//	hashValue = (long)(value.intValue())*31 + 37+ hashValue;
  	
  	} */
  	
  /*	else if(type.equals("char"))
  	{	hashValue = Character.getNumericValue((Character)value) + hashValue; } */
  	
  	else
  	{
  		System.out.println(value);
  		int t;
  		value= value.toString();
  		for (int i=0; i<((String) value).length(); i++)
  		{
  			t= Character.getNumericValue(((String) value).charAt(i));
  			hashValue = hashValue+ t*31;
  		}
  		
  	}
  	
  	  }
	  }
		catch (Exception e) {
  	      e.printStackTrace();
  	      }
	int l= (int) hashValue;
   System.out.println("Hashvalue is :"+ hashValue);
	return l;
}
}
