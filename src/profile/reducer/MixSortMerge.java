package profile.reducer;

import java.io.Serializable;

/*
 *  Merger: Merging 1 sorted segments
 *  Merger: Down to the last merge-pass, with 1 segments left of total size: 82317615 bytes
 *  ReduceTask: [MixSortMerge][CountersBeforeMerge]<InMemorySegmentsNum = 0, InMemorySegmentsSize = 0, OnDiskSegmentsNum = 1, OnDiskSegmentsSize = 82317619
 */
public class MixSortMerge implements Serializable {

    private int inMemSegsNum;
    private long inMemSegsBytes;
    private long onDiskSegsNum;
    private long onDiskSegsBytes;

    // used in SortModel
    private long inMemoryRecords;
    private long onDiskRecords;

    public MixSortMerge(int inMemSegsNum, long inMemSegsBytes,
	    int onDiskSegsNum, long onDiskSegsBytes) {

	this.inMemSegsNum = inMemSegsNum;
	this.inMemSegsBytes = inMemSegsBytes;
	this.onDiskSegsNum = onDiskSegsNum;
	this.onDiskSegsBytes = onDiskSegsBytes;
    }

}