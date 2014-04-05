package object.model.job;

public class SegmentObj {

    private String location; // e.g., SegmentsInCopy
    private String name; // e.g., ReduceTask$ReduceCopier$MapOutput @ 0xe0db2458
    private String object; // e.g., byte[104585760] @ 0xd4646150
    
    private String objId; // e.g., 0xd4646150
    private int taskId; // e.g., 10 represents the 11th map task
    
    private long shallowHeap;
    private long retainedHeap;
    
    public SegmentObj(String name, String objectName, int taskId, long shallowHeap, long retainedHeap) {
	this.name = name;
	this.object = objectName;
	this.taskId = taskId;
	
	this.shallowHeap = shallowHeap;
	this.retainedHeap = retainedHeap;
	
	this.objId = object.substring(object.indexOf('@') + 2);
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public String getObject() {
        return object;
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
    
    
}
