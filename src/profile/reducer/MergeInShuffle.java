package profile.reducer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class MergeInShuffle implements Serializable {


    private static final long serialVersionUID = -1972724717160283342L;
    
    
    private List<InMemoryShuffleMerge> inMemoryShuffleMergeList = new ArrayList<InMemoryShuffleMerge>();
    
    private ShuffleCombine currentCombine;
    
    public void addShuffleBeforeMergeItem(String[] taskIds) {
	int[] segMapperIds = new int[taskIds.length];
	for(int i = 0; i < segMapperIds.length; i++)
	    segMapperIds[i] = Integer.parseInt(taskIds[i]);
	InMemoryShuffleMerge merge = new InMemoryShuffleMerge(segMapperIds);
	inMemoryShuffleMergeList.add(merge);
    }
    
    public void addShuffleAfterMergeItem(int segmentsNum,
	    long recordsBeforeMergeAC, long bytesBeforeMergeAC,
	    long recordsAfterCombine, long rawLength, long compressedLength) {

	inMemoryShuffleMergeList.get(inMemoryShuffleMergeList.size() - 1).setAfterMergeInfo(
		segmentsNum, recordsBeforeMergeAC, bytesBeforeMergeAC,
		recordsAfterCombine, rawLength, compressedLength);

    }
    
    public List<InMemoryShuffleMerge> getInMemoryShuffleMerges() {
	return inMemoryShuffleMergeList;
    }
    
    
    
    public ShuffleCombine getCurrentCombine() {
        return currentCombine;
    }

    public void setCurrentCombine(ShuffleCombine currentCombine) {
        this.currentCombine = currentCombine;
    }

    public String toString() {
	
	StringBuilder sb = new StringBuilder();
	for(int i = 0; i < inMemoryShuffleMergeList.size(); i++) {
	    InMemoryShuffleMerge merge = inMemoryShuffleMergeList.get(i);
	    sb.append("[InMemMerge " + i + "] " + "ids [" + merge.getSegmentsNum() + "](" + merge.segMapperIdsToString() 
		    + "), records = " + merge.getRecordsBeforeMergeAC()
		    + " | " + merge.getRecordsAfterCombine() + ", bytes = "
		    + merge.getBytesBeforeMergeAC() + " | " + merge.getRawLength() + "\n");
	}
	
	return sb.toString();
    }

}
