package profile.mapper;

import java.io.Serializable;

public class MergeInfo implements Serializable {

    private static final long serialVersionUID = -3073508117026105230L;
    
    private int partitionId;
    private int segmentsNum;

    private long recordsBeforeMerge;
    private long rawLengthBeforeMerge;
    private long compressedLengthBeforeMerge;

    private long recordsAfterMerge;
    private long rawLengthAfterMerge;
    private long compressedLengthAfterMerge;

    public MergeInfo(int partitionId, int segmentsNum,
	    long rawLengthBeforeMerge, long compressedLengthBeforeMerge) {
	this.partitionId = partitionId;
	this.segmentsNum = segmentsNum;
	this.rawLengthBeforeMerge = rawLengthBeforeMerge;
	this.compressedLengthBeforeMerge = compressedLengthBeforeMerge;
    }

    public void setAfterMergeItem(long recordsBeforeMerge,
	    long recordsAfterMerge, long rawLengthEnd, long compressedLengthEnd) {

	this.recordsBeforeMerge = recordsBeforeMerge;
	this.recordsAfterMerge = recordsAfterMerge;
	this.rawLengthAfterMerge = rawLengthEnd;
	this.compressedLengthAfterMerge = compressedLengthEnd;

    }

    public int getPartitionId() {
        return partitionId;
    }

    public void setPartitionId(int partitionId) {
        this.partitionId = partitionId;
    }

    public int getSegmentsNum() {
        return segmentsNum;
    }

    public void setSegmentsNum(int segmentsNum) {
        this.segmentsNum = segmentsNum;
    }

    public long getRecordsBeforeMerge() {
        return recordsBeforeMerge;
    }

    public void setRecordsBeforeMerge(long recordsBeforeMerge) {
        this.recordsBeforeMerge = recordsBeforeMerge;
    }

    public long getRawLengthBeforeMerge() {
        return rawLengthBeforeMerge;
    }

    public void setRawLengthBeforeMerge(long rawLengthBeforeMerge) {
        this.rawLengthBeforeMerge = rawLengthBeforeMerge;
    }

    public long getCompressedLengthBeforeMerge() {
        return compressedLengthBeforeMerge;
    }

    public void setCompressedLengthBeforeMerge(long compressedLengthBeforeMerge) {
        this.compressedLengthBeforeMerge = compressedLengthBeforeMerge;
    }

    public long getRecordsAfterMerge() {
        return recordsAfterMerge;
    }

    public void setRecordsAfterMerge(long recordsAfterMerge) {
        this.recordsAfterMerge = recordsAfterMerge;
    }

    public long getRawLengthAfterMerge() {
        return rawLengthAfterMerge;
    }

    public void setRawLengthAfterMerge(long rawLengthAfterMerge) {
        this.rawLengthAfterMerge = rawLengthAfterMerge;
    }

    public long getCompressedLengthAfterMerge() {
        return compressedLengthAfterMerge;
    }

    public void setCompressedLengthAfterMerge(long compressedLengthAfterMerge) {
        this.compressedLengthAfterMerge = compressedLengthAfterMerge;
    }

    
}