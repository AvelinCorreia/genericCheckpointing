package genericCheckpointing.server;
import genericCheckpointing.util.*;
import genericCheckpointing.server.*;
import genericCheckpointing.xmlStoreRestore.*;

public interface RestoreI {
	
	SerializableObject readObj(String wireFormat);
}
