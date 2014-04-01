package profile.reducer;

import java.io.Serializable;

public class FinalSortMerge implements Serializable {


    private static final long serialVersionUID = 946752656921672107L;
    
    private int inMemSegsNum;
    private long inMemSegsBytes;
    private int[] segIdsInReduceBuffer;

    public FinalSortMerge(int inMemSegsNum, long inMemSegsBytes, int[] segMapperIds) {

	this.inMemSegsNum = inMemSegsNum;
	this.inMemSegsBytes = inMemSegsBytes;
	
	this.segIdsInReduceBuffer = segMapperIds;
    }

    public int getInMemSegsNum() {
        return inMemSegsNum;
    }

    public long getInMemSegsBytes() {
        return inMemSegsBytes;
    }
    
    public int[] getSegIdsInReduceBuffer() {
        return segIdsInReduceBuffer;
    }

    public String getSegIdsToString() {
	StringBuilder sb = new StringBuilder();
	for(int i = 0; i < segIdsInReduceBuffer.length; i++) {
	    sb.append(segIdsInReduceBuffer[i]);
	    if(i != segIdsInReduceBuffer.length - 1)
		sb.append(',');
	}
	return sb.toString();
    }
    
}