package profile.reducer;

import java.io.Serializable;

public class InMemoryShuffleMerge implements Serializable {


    private static final long serialVersionUID = -7901371762296157181L;
    
  
    private int[] segMapperIds;
    
    private int segmentsNum;
    private long recordsBeforeMergeAC;
    private long bytesBeforeMergeAC;
    private long recordsAfterCombine;
    private long rawLength;
    private long compressedLength;

    public InMemoryShuffleMerge(int[] segMapperIds) {
	this.segMapperIds = segMapperIds;
    }

    public void setAfterMergeInfo(int segmentsNum,
	    long recordsBeforeMergeAC, long bytesBeforeMergeAC,
	    long recordsAfterCombine, long rawLength, long compressedLength) {

	this.segmentsNum = segmentsNum;
	this.recordsBeforeMergeAC = recordsBeforeMergeAC;
	this.bytesBeforeMergeAC = bytesBeforeMergeAC;
	this.recordsAfterCombine = recordsAfterCombine;
	this.rawLength = rawLength;
	this.compressedLength = compressedLength;
	
    }

    public int[] getSegMapperIds() {
	return segMapperIds;
    }
    
    public String segMapperIdsToString() {
	StringBuilder sb = new StringBuilder();
	for(int i = 0; i < segMapperIds.length; i++) {
	    sb.append(segMapperIds[i]);
	    if(i != segMapperIds.length - 1)
		sb.append(',');
	}
	return sb.toString();
    }
    
    public int getSegmentsNum() {
        return segmentsNum;
    }

    public long getRecordsBeforeMergeAC() {
        return recordsBeforeMergeAC;
    }

    public long getBytesBeforeMergeAC() {
        return bytesBeforeMergeAC;
    }

    public long getRecordsAfterCombine() {
        return recordsAfterCombine;
    }

    public long getRawLength() {
        return rawLength;
    }

    public long getCompressedLength() {
        return compressedLength;
    }

 
    
    

}
