package object.model.job;

import java.text.DecimalFormat;

public class SegmentObj {

    private String location; // e.g., SegmentsInCopy
    private String name; // e.g., ReduceTask$ReduceCopier$MapOutput @ 0xe0db2458
    private String innerObject; // e.g., byte[104585760] @ 0xd4646150

    private int taskId; // e.g., 10 represents the 11th map task
    
    private long shallowHeap;
    private long retainedHeap;
    
    private String objId; // e.g., 0xd4646150
    
    public SegmentObj(String location, String name, String innerObject, long shallowHeap, long retainedHeap, int taskId) {
	this.location = location;
	this.name = name;
	this.innerObject = innerObject;	
	
	this.shallowHeap = shallowHeap;
	this.retainedHeap = retainedHeap;
	this.taskId = taskId;
	
	this.objId = innerObject.substring(innerObject.indexOf('@') + 2);
    }

    public SegmentObj(String location, String[] s) {
	this.location = location;
	this.name = s[0];
	this.innerObject = s[1];
	this.shallowHeap = Long.parseLong(s[2].replaceAll(",", ""));
	this.retainedHeap = Long.parseLong(s[3].replaceAll(",", ""));
	if(s.length == 5)
	    this.taskId = Integer.parseInt(s[4]);
	else 
	    this.taskId = -2;
	this.objId = innerObject.substring(innerObject.indexOf('@') + 2);
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public String getObject() {
        return innerObject;
    }

    public String getObjId() {
        return objId;
    }

    public int getTaskId() {
        return taskId;
    }

    public long getShallowHeap() {
        return shallowHeap;
    }

    public long getRetainedHeap() {
        return retainedHeap;
    }
    
    public String toString() {
	DecimalFormat format = new DecimalFormat(",###");
	return "| " + location + "\t| " + name + "\t| " + innerObject + "\t| "
		+ format.format(retainedHeap) + "\t| " + taskId + "\t|";	
    }
    
}
