package object.model.job;

public class UserObject {

    private String object; // e.g., org.apache.pig.data.BinSedesTuple @ 0xf4fa6248 
    			   // and java.util.LinkedList @ 0xed4f7860
    private long shallowHeap; // shallowHeap of the user object
    private long retainedHeap;
   

    private String objId; // such as 0xf4fa6248
    
    private long length; // contains how many inner objects
    private String innerObject; // e.g., java.lang.String @ 0xd1b28fd8
    private long innerObjRetainedHeap; // e.g., 256
    
    private String thread; // referenced thread
    private String code;  // referenced code
    
    private String stackFrame; 
    
    public UserObject(String object, long shallowHeap, long retainedHeap,
	    long length, String innerObject, long innerObjRetainedHeap,
	    String thread, String code, String stackFrame) {
	
	this.object = object.substring(0, object.indexOf('@') -1);
	this.shallowHeap = shallowHeap;
	this.retainedHeap = retainedHeap;
	this.objId = object.substring(object.indexOf('@') + 2);
	
	this.length = length;
	this.innerObject = innerObject;
	this.innerObjRetainedHeap = innerObjRetainedHeap;
	
	this.thread = thread;
	this.code = code;
	this.stackFrame = stackFrame;
    }
    
    public long getRetainedHeap() {
        return retainedHeap;
    }
    
    public long getLength() {
	return length;
    }
    
    public String getObjectName() {
	return object;
    }
}
