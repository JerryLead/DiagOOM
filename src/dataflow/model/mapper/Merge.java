package dataflow.model.mapper;

public class Merge {
    private int partitionId;
    private int segmentsNum;

    private long recordsBeforeMerge;
    private long rawLengthBeforeMerge;
    private long compressedLengthBeforeMerge;

    private long recordsAfterMerge;
    private long rawLengthAfterMerge;
    private long compressedLengthAfterMerge;


    public Merge(int partitionId, int segmentsNum,
	    long rawLengthBeforeMerge, long compressedLengthBeforeMerge) {
	
	this.partitionId = partitionId;
	this.segmentsNum = segmentsNum;
	this.rawLengthBeforeMerge = rawLengthBeforeMerge;
	this.compressedLengthBeforeMerge = compressedLengthBeforeMerge;
    }
    
    public void setAfterMerge(
		long recordsBeforeMerge, long recordsAfterMerge,
		long rawLengthEnd, long compressedLengthEnd) {
	
	this.recordsBeforeMerge = recordsBeforeMerge;
	this.recordsAfterMerge = recordsAfterMerge;
	this.rawLengthAfterMerge = rawLengthEnd;
	this.compressedLengthAfterMerge = compressedLengthEnd;
	
}
}
