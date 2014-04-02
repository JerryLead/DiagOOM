package dataflow.model.mapper;

import profile.mapper.MergeInfo;

public class MergeAction {
    private int partitionId;
    private int segmentsNum;

    private long recordsBefore;
    private long bytesBefore;

    private long recordsAfter;
    private long bytesAfter;
  
    public MergeAction(MergeInfo mergeInfo) {
	this.partitionId = mergeInfo.getPartitionId();
	this.segmentsNum = mergeInfo.getSegmentsNum();
	
	this.recordsBefore = mergeInfo.getRecordsBeforeMerge();
	this.bytesBefore = mergeInfo.getRawLengthBeforeMerge();
	
	this.recordsAfter = mergeInfo.getRecordsAfterMerge();
	this.bytesAfter = mergeInfo.getRawLengthAfterMerge();
    }

    public int getPartitionId() {
        return partitionId;
    }

    public int getSegmentsNum() {
        return segmentsNum;
    }

    public long getRecordsBefore() {
        return recordsBefore;
    }

    public long getBytesBefore() {
        return bytesBefore;
    }

    public long getRecordsAfter() {
        return recordsAfter;
    }

    public long getBytesAfter() {
        return bytesAfter;
    }

    

    
}
