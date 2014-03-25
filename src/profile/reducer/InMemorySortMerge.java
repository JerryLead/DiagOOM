package profile.reducer;

import java.io.Serializable;

public class InMemorySortMerge implements Serializable {

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

}
