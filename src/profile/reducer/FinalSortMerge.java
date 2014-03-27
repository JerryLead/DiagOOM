package profile.reducer;

import java.io.Serializable;

public class FinalSortMerge implements Serializable {


    private static final long serialVersionUID = 946752656921672107L;
    
    private int inMemSegsNum;
    private long inMemSegsBytes;

    public FinalSortMerge(int inMemSegsNum, long inMemSegsBytes) {

	this.inMemSegsNum = inMemSegsNum;
	this.inMemSegsBytes = inMemSegsBytes;
    }

    public int getInMemSegsNum() {
        return inMemSegsNum;
    }

    public long getInMemSegsBytes() {
        return inMemSegsBytes;
    }
    
}