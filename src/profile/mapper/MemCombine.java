package profile.mapper;

import java.io.Serializable;


/*
 * Spilling map output: full = buffer, records = 4902718, bytes = 402653117
 * 
 * [Start combine() in spill 0][partition 0]<currentCombineInputRecords = 0, totalInputRecords = 148582, currentCombineOutputRecords = 0>
 * [Start combine() in spill 0][partition 1]<currentCombineInputRecords = 148582, totalInputRecords = 614521, currentCombineOutputRecords = 68>
 * [Start combine() in spill 0][partition 2]<currentCombineInputRecords = 763103, totalInputRecords = 863173, currentCombineOutputRecords = 131>
 * [Start combine() in spill 0][partition 3]<currentCombineInputRecords = 1626276, totalInputRecords = 914113, currentCombineOutputRecords = 195>
 * [Start combine() in spill 0][partition 4]<currentCombineInputRecords = 2540389, totalInputRecords = 798808, currentCombineOutputRecords = 262>
 */
public class MemCombine implements Serializable {
    

    private static final long serialVersionUID = 7864311941259328010L;
    
    // <currentCombineInputRecords = 0, totalInputRecords = 148582, currentCombineOutputRecords = 0>
    int spillNum; // 0
    int partitionId; // 0
    
    long startInputRecord; // 1
    long endInputRecord; // 148596
    
    long totalRecord; // 148596
    long currentCombineOutputRecords; // 0
    
    
    public MemCombine(int spillNum, int partitionId, long currentCombineInputRecords, 
	    long totalRecord, long currentCombineOutputRecords) {
	this.spillNum = spillNum;
	this.partitionId = partitionId;
	
	this.startInputRecord = currentCombineInputRecords;
	this.endInputRecord = currentCombineInputRecords + totalRecord;
	
	this.totalRecord = totalRecord;
    }

    public int getSpillNum() {
        return spillNum;
    }

    public int getPartitionId() {
        return partitionId;
    }

    public long getStartInputRecord() {
        return startInputRecord;
    }

    public long getEndInputRecord() {
        return endInputRecord;
    }

    public long getTotalRecord() {
        return totalRecord;
    }

    public long getCurrentCombineOutputRecords() {
        return currentCombineOutputRecords;
    }

    public void setCurrentCombineOutputRecords(long currentCombineOutputRecords) {
        this.currentCombineOutputRecords = currentCombineOutputRecords;
    }
}
