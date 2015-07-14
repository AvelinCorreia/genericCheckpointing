package genericCheckpointing.server;
import genericCheckpointing.util.*;
import genericCheckpointing.server.*;
import genericCheckpointing.xmlStoreRestore.*;

public interface StoreI extends StoreRestoreI{
	
	void writeObj(MyAllTypesFirst aRecord, String wireFormat);
    void writeObj(MyAllTypesSecond aRecord, String wireFormat);
}
