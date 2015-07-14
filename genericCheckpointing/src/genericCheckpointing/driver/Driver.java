
package genericCheckpointing.driver;

import java.lang.reflect.Field;
import java.util.Random;
import java.util.Vector;

import genericCheckpointing.util.*;
import genericCheckpointing.server.*;
import genericCheckpointing.xmlStoreRestore.*;

// import the other types used in this file

public class Driver {
    
    public static void main(String[] args) {
    	int NUM_OF_OBJECTS=0;
    	String fileName="";
    	String mode="";
	// FIXME: read the value of checkpointFile from the command line
	if(args.length!=3)
	{
		System.out.println("Invalid number of arguments, Please try again");
		System.exit(0);
	}
    	try
    	{
        NUM_OF_OBJECTS = Integer.parseInt(args[0]);
        fileName = args[1];
        mode= args[2];
    	}
    	catch (Exception e)
    	{
    		System.out.println("Cannot parse the arguments, Check the data types");
    		System.exit(0);
    	}
    
	ProxyCreator pc = new ProxyCreator();
		
	StoreRestoreHandler handler = new StoreRestoreHandler(new Object(), fileName);
	// create an instance of StoreRestoreHandler (which implements
	// the InvocationHandler
	
	// create a proxy
	StoreRestoreI cpointRef = (StoreRestoreI) pc.createProxy(
								 new Class[] {
								     StoreI.class, RestoreI.class
								 },  handler);
		
	
	// FIXME: invoke a method on the handler instance to set the file name for checkpointFile and open the file

	MyAllTypesFirst myFirst;
	MyAllTypesSecond  mySecond;

	// create a vector to store the objects being serialized
	Vector<SerializableObject> vector_old = new Vector<SerializableObject>();
	
   if((mode.equalsIgnoreCase("ser"))||(mode.equalsIgnoreCase("serdeser")))
   handler.openFileToWrite();
	
   Random rand= new Random();
	for (int i=0; i<NUM_OF_OBJECTS; i++) {
	    // FIXME: create these object instances correctly.
	   // myFirst = new MyAllTypesFirst(i+rand.nextInt(50), "MyInstanceFirst"+i,(11.13+rand.nextInt(50)),238374L+rand.nextInt(12340),(char)('C'+rand.nextInt(40)));
	   // mySecond = new MyAllTypesSecond(i+rand.nextInt(100),"MyInstanceSecond"+i, 1.10f+rand.nextInt(100),(short)(i+rand.nextInt(30)),(char)('A'+rand.nextInt(100)));

		 myFirst = new MyAllTypesFirst(i*3, "MyInstanceFirst"+i,(11.13+i*3), 238374L+i*3,(char)('C'+i*5), 'A');
		 mySecond = new MyAllTypesSecond(i*4,"MyInstanceSecond"+i, 1.10f+i*4, (short)(i+i*3),(char)('A'+i*8));

		
	    vector_old.add(myFirst);
	    vector_old.add(mySecond);
	   
	   if((mode.equalsIgnoreCase("ser"))||(mode.equalsIgnoreCase("serdeser")))
       {((StoreI) cpointRef).writeObj(myFirst, "XML");
       ((StoreI) cpointRef).writeObj(mySecond, "XML");}
	}

	if((mode.equalsIgnoreCase("ser"))||(mode.equalsIgnoreCase("serdeser")))
	handler.closeFileOpenedToWrite();
	
	
	SerializableObject myRecordRet;
	
	Vector<SerializableObject> vector_new = new Vector<SerializableObject>();
	
	if((mode.equalsIgnoreCase("deser"))||(mode.equalsIgnoreCase("serdeser")))
	{

	try {
	handler.openFileToRead();

	// create a vector to store the returned ojects
	for (int j=0; j<2*NUM_OF_OBJECTS; j++) {

	    myRecordRet = ((RestoreI) cpointRef).readObj("XML");
	    
	    vector_new.add(myRecordRet);
	}

	handler.closeFileOpenedToRead();
	}
	catch (Exception e)
	{
		System.out.println("Kindly serialize first");
		System.exit(0);
	}
		
	int counter=0;
	for (int i=0; i<2*NUM_OF_OBJECTS; i++)
	{
		boolean flag= true;
		//System.out.println("counter" + counter);
		
		flag = vector_old.get(i).equals(vector_new.get(i));
		if (flag==false)
		{
			counter++;
		}
	}
	
	System.out.println(counter + " mismatched objects");
	
	}
    }
    
    
	// FIXME: invoke a method on the handler to close the file (if it hasn't already been closed

	// FIXME: compare and confirm that the serialized and deserialzed objects are equal
    
}