package genericCheckpointing.xmlStoreRestore;

import java.util.regex.*;

import genericCheckpointing.util.*;
import genericCheckpointing.server.*;
import genericCheckpointing.xmlStoreRestore.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;



public class StoreRestoreHandler implements InvocationHandler {
	 
	
	private Object target;
	private String fileName;
	private PrintWriter pw;
	private Scanner scan;
	
	public StoreRestoreHandler(Object obj, String fn)
	{
		target =obj;
		fileName=fn;		
	}
	
	
	public HashMap mapTypes(HashMap<String, String> map)
	{
		
		  map.put("class java.lang.String", "string");
		  map.put("int", "int");
		  map.put("double", "double");
		  map.put("char", "char");
		  map.put("short", "short");
		  map.put("float", "float");
		  map.put("long", "long");
		  
		  return map;
	}
	 
	 
	    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
	        //Prints the input
	        Object val = null;
	 
	        if(method.getName().equalsIgnoreCase("writeObj"))
	        {
	        	  HashMap<String, String> map = new HashMap <String, String>();
	    		  map = mapTypes(map);
	    		  SerializableObject aRecord = (SerializableObject) args[0];
	    		  String wireFormat = (String)args[1];
	    		  serialize(aRecord,wireFormat,map);
	        }
	        
	        else if(method.getName().equalsIgnoreCase("readObj"))
	        {
	        	val=deserialize();
	        	return val;
	        }
	        
    	       /* if(void.class == method.getReturnType())
	        	method.invoke(target, args);
	        else
	         val= method.invoke(target, args);
	 
	        //Prints the output
	        //System.out.println("[AFTER METHOD CALL ] The method " + method.getName() + "() ends with " + result.toString());
	 */
	        return val; 
	    }
	    
	    
	    
	    public void serialize(SerializableObject aRecord, String wireFormat, HashMap<String,String> map)
		  {
			  if(wireFormat.equalsIgnoreCase("XML"))
			  {
		  		  
				  try{
				  		
				  	  pw.println("<DPSerialization>");
				  	  pw.println("  <complexType xsi:type=\""+aRecord.getClass()+"\">");
				  	  
				  	  for (Field field : aRecord.getClass().getDeclaredFields())
				  	  {
				  		  field.setAccessible(true);
				  		 // pw.println(field.getName());
				  		  String type = field.getType().toString();
				  		  type= map.get(type);
				  		//  pw.println(map.get(type));
				  	//	pw.println(value);
				  		Object value = field.get(aRecord);
				  		pw.println("    <"+field.getName()+" xsi:type=\"xsd:"+type+"\">"+value.toString()+"</"+field.getName()+">");
				  		  }
						pw.println("  </complexType>");
						pw.println("</DPSerialization>");
		  		  }
						catch (Exception e) {
				  	      e.printStackTrace();
				  	      }
				}
		  }
	    
	    public Object deserialize ()
	    {
	    	SerializableObject record = new SerializableObject();
	    	String line, className="", fieldName, dataType, fieldValue="";
	        String[] strarr;
	        ArrayList<String> paramArr = new ArrayList<String>();

	    	line = scan.nextLine();
	    	
	    	
	    	while(!line.equalsIgnoreCase("</DPSerialization>"))
	    	{
	    		line=line.trim();
	    		if (line.equalsIgnoreCase("<DPSerialization>") ||(line.equalsIgnoreCase("</complexType>"))) 
	    			{line = scan.nextLine();}
	    		
	    		if(line.equalsIgnoreCase("</DPSerialization>"))
	    		{break;}
	    		
	    		
	    		line=line.trim();
	    		strarr= line.split(" ");
	        
	    		if(strarr[0].substring(1).equalsIgnoreCase("complexType"))
	    		{
	    			strarr=strarr[2].split("\"");
	     			className= strarr[0];
	    		}
	    		
	    		else
	    			
	    		{
	    			fieldName=strarr[0].substring(1);
	    	 		Pattern type= Pattern.compile("xsd:(.*?)\">");
	    		Matcher matcher = type.matcher(line);
	    		if (matcher.find())
	    		{
	    		    dataType=(matcher.group(1));
	    		}
	    		
	    		Pattern value = Pattern.compile(">(.*?)<");
	    		matcher = value.matcher(line);
	    		if (matcher.find())
	    		{
	    		    fieldValue=(matcher.group(1));
	    		}
	    			    		
	    		paramArr.add(fieldValue);
	    		
	    		}	
	        
	    		line = scan.nextLine(); 				
	    	}
	    
	    	
	    	
	    
	    
	      Object[] convertedArgs = new Object[paramArr.size()];
	    	
	     // String className1="util.MyAllTypesFirst";
	    	try{
	    	Class<?> clazz = Class.forName(className);
	    	
	    	for (Constructor<?> ctor : clazz.getConstructors()) {
	            Class<?>[] paramTypes = ctor.getParameterTypes();

	            // If the arity matches, let's use it.
	            if (paramArr.size() == paramTypes.length) {

	                // Convert the String arguments into the parameters' types.
	               
	                for (int i = 0; i < convertedArgs.length; i++) {
	                    convertedArgs[i] = convert(paramTypes[i], paramArr.get(i));
	                }
	    	 }
	        	return ctor.newInstance(convertedArgs);
	    	}
	    
	    	}
	    	
	    	catch (Exception e) {
		  	      e.printStackTrace();
		  	      }
	    	return record;
	    	}
	    
	      
	    
	    static Object convert(Class<?> target, String s) {
	    	try{
	        if (target == Object.class || target == String.class || s == null) {
	            return s;
	        }
	        if (target == Character.class || target == char.class) {
	            return s.charAt(0);
	        }
	         if (target == Byte.class || target == byte.class) {
	            return Byte.parseByte(s);
	        }
	         if (target == Short.class || target == short.class) {
	            return Short.parseShort(s);
	        }
	         if (target == Integer.class || target == int.class) {
	            return Integer.parseInt(s);
	        }
	         if (target == Long.class || target == long.class) {
	            return Long.parseLong(s);
	        }
	         if (target == Float.class || target == float.class) {
	            return Float.parseFloat(s);
	        }
	        if (target == Double.class || target == double.class) {
	            return Double.parseDouble(s);
	        }
	        if (target == Boolean.class || target == boolean.class) {
	            return Boolean.parseBoolean(s);
	        }
	       
	    	}
	    	catch (Exception e) 
	    	{
	    		System.out.println("Don't know how to convert "+ s + " to " + target);
	    		System.exit(0);
	    	}
			return s;
	    }
	    
	    
	    
	    public void openFileToWrite()
		  {
			  
			  try {
				     File file = new File(fileName);
				     FileWriter fw = new FileWriter(file);
				     pw = new PrintWriter(fw);
			      } catch (IOException e) {
				     e.printStackTrace();
				  }
				  }
		  
		public void  openFileToRead()
		  {			
			try{	
					  File file = new File(fileName);
						 scan = new Scanner(file);  	
		    	}
		      catch (IOException e) {
				     System.out.println("File not found or empty, Kindly serialize first");
				     System.exit(0);
				     } 
		  }


		  public void closeFileOpenedToWrite()
		  {
			  pw.close();
		  }

		  public void closeFileOpenedToRead()
		  {
			  scan.close();
		  }   
	}