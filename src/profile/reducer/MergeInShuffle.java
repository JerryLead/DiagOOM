package profile.reducer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MergeInShuffle implements Serializable {

    private List<InMemoryShuffleMerge> inMemoryShuffleMergeList = new ArrayList<InMemoryShuffleMerge>();


    public void addShuffleAfterMergeItem(int segmentsNum,
	    long recordsBeforeMergeAC, long bytesBeforeMergeAC,
	    long recordsAfterCombine, long rawLength, long compressedLength) {

	InMemoryShuffleMerge merge = new InMemoryShuffleMerge(
		segmentsNum, recordsBeforeMergeAC, bytesBeforeMergeAC,
		recordsAfterCombine, rawLength, compressedLength);
	
	inMemoryShuffleMergeList.add(merge);

    }

}
