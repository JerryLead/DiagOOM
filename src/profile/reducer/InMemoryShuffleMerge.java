package profile.reducer;

import java.io.Serializable;

public class InMemoryShuffleMerge implements Serializable {

    private int segmentsNum;
    private long recordsBeforeMergeAC;
    private long bytesBeforeMergeAC;
    private long recordsAfterCombine;
    private long rawLength;
    private long compressedLength;

    public InMemoryShuffleMerge(int segmentsNum,
	    long recordsBeforeMergeAC, long bytesBeforeMergeAC,
	    long recordsAfterCombine, long rawLength, long compressedLength) {

	this.segmentsNum = segmentsNum;
	this.recordsBeforeMergeAC = recordsBeforeMergeAC;
	this.bytesBeforeMergeAC = bytesBeforeMergeAC;
	this.recordsAfterCombine = recordsAfterCombine;
	this.rawLength = rawLength;
	this.compressedLength = compressedLength;
    }

}
