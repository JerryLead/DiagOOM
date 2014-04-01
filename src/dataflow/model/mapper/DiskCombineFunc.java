package dataflow.model.mapper;

public class DiskCombineFunc {

    private long cCombineInputRecords;

    private long cCombineOutputRecords;

    private long tCombineInputRecords;

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

}
