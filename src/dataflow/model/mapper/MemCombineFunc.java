package dataflow.model.mapper;

import java.text.DecimalFormat;

public class MemCombineFunc {

    // [Start combine() in spill 0][partition 1]<currentCombineInputRecords = 148582, totalInputRecords = 614521, currentCombineOutputRecords = 68>
    private int spillNum; // 0
    private int partitionId; // 1
    
    private long startInputRecord; // 148582 = currentCombineInputRecords

    private long cCombineInputRecords = -1; // Counter(Combine input records) - startInputRecord
    
    private long cCombineOutputRecords = -1; // Counter(Combine output records) - currentCombineOutputRecords

    private long tCombineInputRecords = -1; // totalInputRecords

    public int getSpillNum() {
        return spillNum;
    }

    public int getPartitionId() {
        return partitionId;
    }

    public long getStartInputRecord() {
        return startInputRecord;
    }

    public long getcCombineInputRecords() {
        return cCombineInputRecords;
    }

    public long getcCombineOutputRecords() {
        return cCombineOutputRecords;
    }

    public long gettCombineInputRecords() {
        return tCombineInputRecords;
    }

    
    public void setSpillNum(int spillNum) {
        this.spillNum = spillNum;
    }

    public void setPartitionId(int partitionId) {
        this.partitionId = partitionId;
    }

    public void setStartInputRecord(long startInputRecord) {
        this.startInputRecord = startInputRecord;
    }

    public void setcCombineInputRecords(long cCombineInputRecords) {
        this.cCombineInputRecords = cCombineInputRecords;
    }

    public void setcCombineOutputRecords(long cCombineOutputRecords) {
        this.cCombineOutputRecords = cCombineOutputRecords;
    }

    public void settCombineInputRecords(long tCombineInputRecords) {
        this.tCombineInputRecords = tCombineInputRecords;
    }

    public String toString() {
	StringBuilder sb = new StringBuilder();
	DecimalFormat f = new DecimalFormat(",###");
	
	sb.append("[Spill number] " + spillNum + "\n");
	sb.append("[PartitionId]  " + partitionId + "\n");
	sb.append("[Combine input records]  " + f.format(cCombineInputRecords) + " | " + f.format(tCombineInputRecords) + "\n");
	sb.append("[Combine output records] " + f.format(cCombineOutputRecords) + "\n");
	
	return sb.toString();
    }
    
    
    
    /*
    private long cCombineInputRecords = -1;
    
    private long cCombineOutputRecords = -1;

    private long tCombineInputRecords = -1;

    private long inputRecsInPreviousSpills = -1;

    public long getcCombineInputRecords() {
	return cCombineInputRecords;
    }

    public void setcCombineInputRecords(long cCombineInputRecords) {
	this.cCombineInputRecords = cCombineInputRecords;
    }

    public long getcCombineOutputRecords() {
	return cCombineOutputRecords;
    }

    public void setcCombineOutputRecords(long cCombineOutputRecords) {
	this.cCombineOutputRecords = cCombineOutputRecords;
    }

    public long gettCombineInputRecords() {
	return tCombineInputRecords;
    }

    public void settCombineInputRecords(long tCombineInputRecords) {
	this.tCombineInputRecords = tCombineInputRecords;
    }
    
    public void setInputRecsInPreviousSpills(long inputRecsInPreviousSpills) {
	this.inputRecsInPreviousSpills = inputRecsInPreviousSpills;
	
    }

    public long getInputRecsInPreviousSpills() {
        return inputRecsInPreviousSpills;
    }
    
    public String toString() {
	StringBuilder sb = new StringBuilder();
	DecimalFormat f = new DecimalFormat(",###");
	
	sb.append("[Combine input records]  " + f.format(cCombineInputRecords) + " | " + f.format(tCombineInputRecords) + "\n");
	sb.append("[Combine output records] " + f.format(cCombineOutputRecords) + "\n");
	
	return sb.toString();
    }

    */
}
