package object.model.job;

import java.text.DecimalFormat;

public class UserObject {

    private String objectName; // e.g., org.apache.pig.data.BinSedesTuple @ 0xf4fa6248 
    			   // and java.util.LinkedList @ 0xed4f7860
    private long shallowHeap; // shallowHeap of the user object
    private long retainedHeap;

    private long length; // contains how many inner objects
    private String innerObject; // e.g., java.lang.String @ 0xd1b28fd8

    private String innerObjRetainedHeap; // e.g., 72 + 496
    
    private String thread; // referenced thread
    private String code;  // referenced code
    private String objId; // such as 45335268
    
    private String stackFrame; 
    
    public UserObject(String name, long shallowHeap, long retainedHeap,
	    long length, String innerObject, String innerObjRetainedHeap,
	    String thread, String code, String objectId) {
	
	this.objectName = name.substring(0, name.indexOf('@') - 1);
	this.shallowHeap = shallowHeap;
	this.retainedHeap = retainedHeap;

	this.length = length;
	this.innerObject = innerObject;
	this.innerObjRetainedHeap = innerObjRetainedHeap;
	
	this.thread = thread;
	this.code = code;

	this.objId = objectId;
    }
    
    public UserObject(String[] s) {
	this.objectName = s[0];
	this.shallowHeap = Long.parseLong(s[1].replaceAll(",", ""));
	this.retainedHeap = Long.parseLong(s[2].replaceAll(",", ""));
	this.length = Long.parseLong(s[3].replaceAll(",", ""));
	this.innerObject = s[4];
	this.innerObjRetainedHeap = s[5];
	this.thread = s[6];
	this.code = s[7];
	this.objId = objectName.substring(objectName.indexOf('@') + 2);
    }

    public long getRetainedHeap() {
        return retainedHeap;
    }
    
    public long getLength() {
	return length;
    }
    
    public String getObjectName() {
	return objectName;
    }
    
    public String getObjectId() {
	return objId;
    }

    public void setThreadStack(String stackFrame) {
	this.stackFrame = stackFrame;
	
    }

    public void display() {
	DecimalFormat format = new DecimalFormat(",###");
	//System.out.println("| User object | shallow heap | retained heap | length | inner object | inner size | threads | code() |");
	StringBuilder sb = new StringBuilder("| " + objectName + " | " + format.format(shallowHeap)
			+ " | " + format.format(retainedHeap) + " | " + format.format(length) + " | ");
	
	sb.append(innerObject + " | " + innerObjRetainedHeap + " | ");
	sb.append(thread + " | " + code + " |");	
	//sb.append(objId + " |");
	
	System.out.println(sb.toString());
	
    }
}
