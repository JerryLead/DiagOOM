package dataflow.model.reducer;

public class ReduceFunc {
    private long tReduceInputRecords;

    private long cReduceInputRecords;
    private long cReduceInputGroups;

    private long cReduceOutputRecords;

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
