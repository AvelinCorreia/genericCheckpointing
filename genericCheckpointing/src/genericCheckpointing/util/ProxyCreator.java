package genericCheckpointing.util;
import genericCheckpointing.xmlStoreRestore.*;
import genericCheckpointing.server.*;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;


public class ProxyCreator{

	
	public StoreRestoreI createProxy(Class<?>[] interfaceArray, InvocationHandler handler){

    	StoreRestoreI storeRestoreRef =
                (StoreRestoreI)
                Proxy.newProxyInstance(
                                       getClass().getClassLoader(),
                                       interfaceArray,
                                       handler
                                       );
    	
		return storeRestoreRef; 
		}
}