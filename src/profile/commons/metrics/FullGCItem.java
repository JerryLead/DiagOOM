package profile.commons.metrics;

import java.io.Serializable;

public class FullGCItem implements Serializable {

    private static final long serialVersionUID = 2662626133102893826L;
    // All are MB
    // 169.606: [Full GC [PSYoungGen: 128K->0K(478720K)] [PSOldGen: 22776K->22834K(1024000K)] 
    // 22904K->22834K(1502720K) [PSPermGen: 14920K->14920K(33856K)], 0.0247090 secs] 
    // [Times: user=0.02 sys=0.00, real=0.02 secs] 
    private float newGenBefore;
    private float oldGenBefore;
    private float heapBefore;
    
    private float newGenCurrent;
    private float oldGenCurrent;
    private float heapCurrent;
    
    private float newGenCommitted;
    private float oldGenCommitted;
    private float heapCommitted;

    public FullGCItem(float newGenBefore, float newGenCurrent,
	    float newGenCommitted, float oldGenBefore, float oldGenCurrent,
	    float oldGenCommitted, float heapBefore, float heapCurrent,
	    float heapCommitted) {
	
	this.newGenBefore = newGenBefore;
	this.newGenCurrent = newGenCurrent;
	this.newGenCommitted = newGenCommitted;
	
	this.oldGenBefore = oldGenBefore;
	this.oldGenCurrent = oldGenCurrent;
	this.oldGenCommitted = oldGenCommitted;
	
	this.heapBefore = heapBefore;
	this.heapCurrent = heapCurrent;
	this.heapCommitted = heapCommitted;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public float getNewGenBefore() {
        return newGenBefore;
    }

    public float getOldGenBefore() {
        return oldGenBefore;
    }

    public float getHeapBefore() {
        return heapBefore;
    }

    public float getNewGenCurrent() {
        return newGenCurrent;
    }

    public float getOldGenCurrent() {
        return oldGenCurrent;
    }

    public float getHeapCurrent() {
        return heapCurrent;
    }

    public float getNewGenCommitted() {
        return newGenCommitted;
    }

    public float getOldGenCommitted() {
        return oldGenCommitted;
    }

    public float getHeapCommitted() {
        return heapCommitted;
    }
    
    
}
