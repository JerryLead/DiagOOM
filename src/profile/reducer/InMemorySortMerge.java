package profile.reducer;

import java.io.Serializable;

public class InMemorySortMerge implements Serializable {


    private static final long serialVersionUID = 7933091074472003281L;
    
    private int segmentsNum;
    private long records;
    private long bytesBeforeMerge;
    private long rawLength;
    private long compressedLength;

    public InMemorySortMerge(int segmentsNum, long records,
	    long bytesBeforeMerge, long rawLength, long compressedLength) {

	this.segmentsNum = segmentsNum;
	this.records = records;
	this.bytesBeforeMerge = bytesBeforeMerge;
	this.rawLength = rawLength;
	this.compressedLength = compressedLength;
    }

    public int getSegmentsNum() {
        return segmentsNum;
    }

    public long getRecords() {
        return records;
    }

    public long getBytesBeforeMerge() {
        return bytesBeforeMerge;
    }

    public long getRawLength() {
        return rawLength;
    }

    public long getCompressedLength() {
        return compressedLength;
    }

    
}
