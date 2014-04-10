package dataflow.model.mapper;

import java.text.DecimalFormat;

public class DiskCombineFunc {

    private long cCombineInputRecords = -1;

    private long cCombineOutputRecords = -1;

    private long tCombineInputRecords = -1;

    private long inputRecsInPreviousMerges = -1;

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
    
    public void setInputRecsInPreviousMerges(long inputRecsInPreviousMerges) {
	this.inputRecsInPreviousMerges  = inputRecsInPreviousMerges;
	
    }

    public long getInputRecsInPreviousMerges() {
        return inputRecsInPreviousMerges;
    }

    public String toString() {
	StringBuilder sb = new StringBuilder();
	DecimalFormat f = new DecimalFormat(",###");
	
	sb.append("[Combine input records]  " + f.format(cCombineInputRecords) + " | " + f.format(tCombineInputRecords) + "\n");
	sb.append("[Combine output records] " + f.format(cCombineOutputRecords) + "\n");
	
	return sb.toString();
    }


    
    
}
