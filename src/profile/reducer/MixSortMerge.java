package profile.reducer;

import java.io.Serializable;

/*
 *  Merger: Merging 1 sorted segments
 *  Merger: Down to the last merge-pass, with 1 segments left of total size: 82317615 bytes
 *  ReduceTask: [MixSortMerge][CountersBeforeMerge]<InMemorySegmentsNum = 0, InMemorySegmentsSize = 0, OnDiskSegmentsNum = 1, OnDiskSegmentsSize = 82317619
 */
public class MixSortMerge implements Serializable {

    private static final long serialVersionUID = -2803969838577137062L;
    
    private int inMemSegsNum;
    private long inMemSegsBytes;
    private long onDiskSegsNum;
    private long onDiskSegsBytes;


    public MixSortMerge(int inMemSegsNum, long inMemSegsBytes,
	    int onDiskSegsNum, long onDiskSegsBytes) {

	this.inMemSegsNum = inMemSegsNum;
	this.inMemSegsBytes = inMemSegsBytes;
	this.onDiskSegsNum = onDiskSegsNum;
	this.onDiskSegsBytes = onDiskSegsBytes;
    }


    public int getInMemSegsNum() {
        return inMemSegsNum;
    }


    public long getInMemSegsBytes() {
        return inMemSegsBytes;
    }


    public long getOnDiskSegsNum() {
        return onDiskSegsNum;
    }


    public long getOnDiskSegsBytes() {
        return onDiskSegsBytes;
    }
    
}