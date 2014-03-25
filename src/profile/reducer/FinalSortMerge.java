package profile.reducer;

import java.io.Serializable;

public class FinalSortMerge implements Serializable {

    private int inMemSegsNum;
    private long inMemSegsBytes;

    public FinalSortMerge(int inMemSegsNum, long inMemSegsBytes) {

	this.inMemSegsNum = inMemSegsNum;
	this.inMemSegsBytes = inMemSegsBytes;
    }

}