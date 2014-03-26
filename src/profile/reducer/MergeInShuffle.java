package profile.reducer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MergeInShuffle implements Serializable {


    private static final long serialVersionUID = -1972724717160283342L;
    
    private List<InMemoryShuffleMerge> inMemoryShuffleMergeList = new ArrayList<InMemoryShuffleMerge>();


    public void addShuffleAfterMergeItem(int segmentsNum,
	    long recordsBeforeMergeAC, long bytesBeforeMergeAC,
	    long recordsAfterCombine, long rawLength, long compressedLength) {

	InMemoryShuffleMerge merge = new InMemoryShuffleMerge(
		segmentsNum, recordsBeforeMergeAC, bytesBeforeMergeAC,
		recordsAfterCombine, rawLength, compressedLength);
	
	inMemoryShuffleMergeList.add(merge);

    }
    
    public String toString() {
	
	StringBuilder sb = new StringBuilder();
	for(int i = 0; i < inMemoryShuffleMergeList.size(); i++) {
	    InMemoryShuffleMerge merge = inMemoryShuffleMergeList.get(i);
	    sb.append("[InMemMerge " + i + "] " + "num = " + merge.getSegmentsNum() + ", records = " + merge.getRecordsBeforeMergeAC()
		    + " | " + merge.getRecordsAfterCombine() + ", bytes = "
		    + merge.getBytesBeforeMergeAC() + " | " + merge.getRawLength() + "\n");
	}
	
	return sb.toString();
    }

}
