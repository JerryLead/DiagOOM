package object.model.job;

public class BufferObject extends FrameworkObject {

    private String name; // e.g., kvbuffer, kvindices, kvoffsets
    private String object; // e.g., byte[367001600] @ 0xce000000
    private String objId; // e.g., 0xce000000
   
    private long shallowHeap;
    private long retainedHeap;
    
    public BufferObject(String name, String objectName, long shallowHeap, long retainedHeap) {
	this.name = name;
	this.object = objectName;
	this.shallowHeap = shallowHeap;
	this.retainedHeap = retainedHeap;
	
	this.objId = object.substring(object.indexOf('@') + 2);
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

    public long getShallowHeap() {
        return shallowHeap;
    }

    public long getRetainedHeap() {
        return retainedHeap;
    }
    
    
    
}
