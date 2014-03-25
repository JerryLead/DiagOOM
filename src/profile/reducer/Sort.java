package profile.reducer;

import java.io.Serializable;

public class Sort implements Serializable {
    private int inMemSegsAfterShuffle;
    private int onDiskSegsAfterShuffle;

    private InMemorySortMerge inMemorySortMerge; // Segments in [ShuffleBound - ReduceBuffer] ==> onDiskSeg
    						 // if(count(onDiskSegs) < io.sort.factor)
    
    private MixSortMerge mixSortMerge; // Segments in ShuffleBound (not merged in InMemorySortMerge) + onDiskSegs ==> logical big segment
    					
    private FinalSortMerge finalSortMerge; // Segments in ReduceBuffer + onDiskSeg(s) ==> logical big segment


    public void setInMemorySortMerge(
	    int segmentsNum, 
	    long records, 
	    long bytesBeforeMerge,
	    long rawLength, 
	    long compressedLength) {
	
	inMemorySortMerge = new InMemorySortMerge(segmentsNum, records, 
		bytesBeforeMerge, rawLength, compressedLength);
    }
    
    public void setFinalSortMerge(int inMemSegsNum, long inMemBytes) {
	finalSortMerge = new FinalSortMerge(inMemSegsNum, inMemBytes);
    }

    public void setMixSortMerge(
	    int inMemSegsNum, 
	    long inMemSegsBytes,
	    int onDiskSegsNum, 
	    long onDiskSegsBytes) {
	
	mixSortMerge = new MixSortMerge(inMemSegsNum, inMemSegsBytes, onDiskSegsNum, onDiskSegsBytes);
    }


}
