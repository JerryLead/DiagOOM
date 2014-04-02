package profile.reducer;

import java.io.Serializable;

public class ShuffleBuffer implements Serializable {


    private static final long serialVersionUID = -1309096120528545918L;
    
    // private long runtime_maxMemory;     // [---------------Runtime().maxMemory()---------------------]
    private long shuffleBound; 	        // [-------------- ShuffleBound --------------][--for other--]
    private long maxSingleRecordBytes;  //

    //private long inMemoryBufferLimit;   // [------- MergeBound ------][--- unmerged --]

    public void set(long shuffleBound, long maxSingleRecordBytes) {
	this.shuffleBound = shuffleBound;
	this.maxSingleRecordBytes = maxSingleRecordBytes;
    }
    
    public String toString() {
	return "[ShuffleBound] " + shuffleBound / 1024 / 1024 + " MB\n";
    }

    public long getShuffleBound() {
        return shuffleBound;
    }

    public long getMaxSingleRecordBytes() {
        return maxSingleRecordBytes;
    }
    
    
}
