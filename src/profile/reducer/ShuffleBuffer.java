package profile.reducer;

public class ShuffleBuffer {

    private long runtime_maxMemory;     // [---------------Runtime().maxMemory()---------------------]
    private long shuffleBound; 	        // [-------------- ShuffleBound --------------][--for other--]
    private long maxSingleRecordBytes;  //

    //private long inMemoryBufferLimit;   // [------- MergeBound ------][--- unmerged --]

    public void set(long shuffleBound, long maxSingleRecordBytes) {
	this.shuffleBound = shuffleBound;
	this.maxSingleRecordBytes = maxSingleRecordBytes;
    }
    
    public float bytesToMB(long bytes) {
	return (float) ((double) bytes / 1024 / 1024);
    }
}
