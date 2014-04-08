package object.model.job;

import java.text.DecimalFormat;

public class BufferObject {

    private String name; // e.g., kvbuffer, kvindices, kvoffsets
    private String innerObject; // e.g., byte[367001600] @ 0xce000000
    private String objId; // e.g., 0xce000000
   
    private long shallowHeap;
    private long retainedHeap;
    
    public BufferObject(String name, String innerObject, long shallowHeap, long retainedHeap) {
	this.name = name;
	this.innerObject = innerObject;
	this.shallowHeap = shallowHeap;
	this.retainedHeap = retainedHeap;
	
	this.objId = innerObject.substring(innerObject.indexOf('@') + 2);
    }

    public BufferObject(String[] s) {
	this.name = s[0];
	this.innerObject = s[1];
	this.shallowHeap = Long.parseLong(s[2].replaceAll(",", ""));
	this.retainedHeap = Long.parseLong(s[3].replaceAll(",", ""));
	
    }

    public String getName() {
        return name;
    }

    public String getInnerObject() {
        return innerObject;
    }

    public String getObjId() {
        return objId;
    }

    public long getShallowHeap() {
        return shallowHeap;
    }

    public long getRetainedHeap() {
        return retainedHeap;
    }
    
    public String toString() {
	DecimalFormat format = new DecimalFormat(",###");
	return "| " + name + "\t| " + innerObject + "\t| " 
		+ format.format(shallowHeap) + "\t| " + format.format(retainedHeap) + "\t|";
    }
    
}
