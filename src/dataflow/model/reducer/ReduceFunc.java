package dataflow.model.reducer;

import java.text.DecimalFormat;

public class ReduceFunc {
    private long tReduceInputRecords;

    private long cReduceInputRecords;
    private long cReduceInputGroups;

    private long cReduceOutputRecords;

    public String toString() {
	StringBuilder sb = new StringBuilder();
	DecimalFormat f = new DecimalFormat(",###");
	
	sb.append("[Reduce input records] " + f.format(cReduceInputRecords) + " | " + f.format(tReduceInputRecords) + "\n");
	sb.append("[Reduce input groups] " + f.format(cReduceInputGroups) + "\n");
	sb.append("[Reduce output records] " + f.format(cReduceOutputRecords) + "\n");

	return sb.toString();
    }
    
    public long gettReduceInputRecords() {
	return tReduceInputRecords;
    }

    public void settReduceInputRecords(long tReduceInputRecords) {
	this.tReduceInputRecords = tReduceInputRecords;
    }

    public long getcReduceInputRecords() {
	return cReduceInputRecords;
    }

    public void setcReduceInputRecords(long cReduceInputRecords) {
	this.cReduceInputRecords = cReduceInputRecords;
    }

    public long getcReduceInputGroups() {
	return cReduceInputGroups;
    }

    public void setcReduceInputGroups(long cReduceInputGroups) {
	this.cReduceInputGroups = cReduceInputGroups;
    }

    public long getcReduceOutputRecords() {
	return cReduceOutputRecords;
    }

    public void setcReduceOutputRecords(long cReduceOutputRecords) {
	this.cReduceOutputRecords = cReduceOutputRecords;
    }

}
