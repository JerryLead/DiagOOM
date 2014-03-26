package profile.commons.metrics;

public class FullGCItem {
    
  
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
}
