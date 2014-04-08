package profile.commons.metrics;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class JvmUsage implements Serializable {


    private static final long serialVersionUID = 3168481618018710171L;
    
    private List<JstatItem> jstatMetricsList = new ArrayList<JstatItem>();
    private GcCapacity gcCap;

    private FullGCItem fullGCItem;
    
    
    private HeapUsage newGen;
    private HeapUsage eden;
    private HeapUsage from;
    private HeapUsage to;
    private HeapUsage oldGen;
    
    private OOMHeapUsage newGeneration;
    private OOMHeapUsage oldGeneration;
    private OOMHeapUsage heap;
    
    
    public void computeOOMUsage() {
	newGeneration = new OOMHeapUsage(newGen.getUsed(), newGen.getCommitted(), newGen.getTotal());
	oldGeneration = new OOMHeapUsage(oldGen.getUsed(), oldGen.getCommitted(), oldGen.getTotal());
	heap = new OOMHeapUsage(newGen.getUsed() + oldGen.getUsed(), newGen.getCommitted()
		+ oldGen.getCommitted(), newGen.getTotal() + oldGen.getTotal());
	
	if(fullGCItem != null) {
	    newGeneration.setUsed(fullGCItem.getNewGenCurrent());
	    newGeneration.setCommitted(fullGCItem.getNewGenCommitted());
	    
	    oldGeneration.setUsed(fullGCItem.getOldGenCurrent());
	    oldGeneration.setCommitted(fullGCItem.getOldGenCommitted());
	    
	    heap.setUsed(fullGCItem.getHeapCurrent());
	    heap.setCommitted(fullGCItem.getHeapCommitted());
	}
    }
    
    public void addJstatMetrics(String[] jstatParams) {
	JstatItem item = new JstatItem(jstatParams);
	jstatMetricsList.add(item);
    }

    public List<JstatItem> getJstatMetricsList() {
	return jstatMetricsList;
    }

    public GcCapacity getGcCapacity() {
	return gcCap;
    }

    
    public List<String> getJstatMetricsStringList() {

	List<String> list = new ArrayList<String>();
	String header = "TimeS S0U S1U EU OU PU S0C S1C EC OC PC NGC YGC FGC YGCT FGCT GCT";
	list.add(header);
	for (JstatItem item : jstatMetricsList) {
	    list.add(item.toString());
	}
	return list;
    }

    public void addGcCapacity(GcCapacity gcCap) {
	this.gcCap = gcCap;
    }

    /*
    public void addYoungGCItem(float newGenBefore, float newGenCurrent,
	    float newGenCommitted, float heapBefore, float heapCurrent,
	    float heapCommitted) {
	// TODO Auto-generated method stub
	
    }
    */
    
    public void setFullGCItem(float newGenBefore, float newGenCurrent,
	    float newGenCommitted, float oldGenBefore, float oldGenCurrent,
	    float oldGenCommitted, float heapBefore, float heapCurrent,
	    float heapCommitted) {
	
	if(fullGCItem == null) {
	    fullGCItem = new FullGCItem(newGenBefore, newGenCurrent, newGenCommitted, 
			oldGenBefore, oldGenCurrent, oldGenCommitted, 
			heapBefore, heapCurrent, heapCommitted);
	}
	else if(heapCurrent >= fullGCItem.getHeapCurrent()){
	    fullGCItem = new FullGCItem(newGenBefore, newGenCurrent, newGenCommitted, 
			oldGenBefore, oldGenCurrent, oldGenCommitted, 
			heapBefore, heapCurrent, heapCommitted);
	}
    }

    public void addHeapUsage(String name, float total,
	    float used, long[] bound) {
	switch(name) {
	case "newGen":
	    newGen = new HeapUsage(total, used, bound); 
	    break;
	    
	case "oldGen":
	    oldGen = new HeapUsage(total, used, bound);
	    break;
	    
	case "eden":
	    eden = new HeapUsage(total, used, bound);
	    break;
	    
	case "from":
	    from = new HeapUsage(total, used, bound);
	    break;
	    
	case "to":
	    to = new HeapUsage(total, used, bound);
	    break;
	  
	}
	
    }

    public String toString() {
	/*
	if(newGen == null)
	    return "";
	StringBuilder sb = new StringBuilder();
	
	sb.append("[Space] Used | Committed | Total\n");
	sb.append("[NewGen] " + (int)newGen.getUsed() + " | " + (int)newGen.getCommitted() + " | " + (int)newGen.getTotal() + "\n");
	sb.append("[eden] " + (int)eden.getUsed() + " | " + (int)eden.getCommitted() + " | " + (int)eden.getTotal() + "\n");
	sb.append("[from] " + (int)from.getUsed() + " | " + (int)from.getCommitted() + " | " + (int)from.getTotal() + "\n");
	sb.append("[to] " + (int)to.getUsed() + " | " + (int)to.getCommitted() + " | " + (int)to.getTotal() + "\n");
	sb.append("[OldGen] " + (int)oldGen.getUsed() + " | " + (int)oldGen.getCommitted() + " | " + (int)oldGen.getTotal() + "\n");
	
	return sb.toString();
	*/
	if(newGen == null)
	    return "";
	
	StringBuilder sb = new StringBuilder();
	
	sb.append("[Space] Used | Committed | Total\n");
	sb.append("[Heap] " + (int)heap.getUsed() + " | " + (int)heap.getCommitted() + " | " 
		+ (int)heap.getTotal() + "\n");
	sb.append("[NewGen] " + (int)newGeneration.getUsed() + " | " + (int)newGeneration.getCommitted() + " | " 
		+ (int)newGeneration.getTotal() + "\n");
	sb.append("[OldGen] " + (int)oldGeneration.getUsed() + " | " + (int)oldGeneration.getCommitted() + " | " 
		+ (int)oldGeneration.getTotal() + "\n");
	
	return sb.toString();
    }
}

class LongToTime {

    private static DateFormat f = new SimpleDateFormat("mm:ss");

    public static String longToMMSS(long time) {
	return f.format(time);
    }

}

class HeapUsage implements Serializable {
   

    private static final long serialVersionUID = -6962554582333146684L;
    
    float total;
    float used;
    
    long start;
    long committed;
    long end;
    
    public HeapUsage(float total, float used, long[] bound) {
   	this.total = total;
   	this.used = used;
   	this.start = bound[0];
   	this.committed = bound[1];
   	this.end = bound[2];
    }

    public float getTotal() {
        return ((float)(end - start)) / 1024 / 1024;
    }

    public float getUsed() {
        return used;
    }

    public float getCommitted() {
        return ((float)(committed - start)) / 1024 / 1024;
    }  
}

class OOMHeapUsage implements Serializable {
    private float used;
    private float committed;
    private float total;
    
    
    public OOMHeapUsage(float used, float committed, float total) {
	this.used = used;
	this.committed = committed;
	this.total = total;
    }

    public void setUsed(float used) {
        this.used = used;
    }

    public void setCommitted(float committed) {
        this.committed = committed;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getUsed() {
        return used;
    }

    public float getCommitted() {
        return committed;
    }

    public float getTotal() {
        return total;
    }
    
    
}



