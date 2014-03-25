package profile.reducer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Shuffle implements Serializable {

    private List<ShuffleInfo> shuffleInfoList = new ArrayList<ShuffleInfo>();
    
    private int inMemSegsNumAfterShuffle;
    private int onDiskSegsNumAfterShuffle;

    // add shuffle infos (shuffling * from map task)
    // for log parser
    public void addShuffleItem(
	    String sourceTaskId, 
	    String storeLoc,
	    long decompressedLen, 
	    long compressedLen) {

	ShuffleInfo shuffleInfo = new ShuffleInfo(sourceTaskId, storeLoc,
		decompressedLen, compressedLen);
	
	shuffleInfoList.add(shuffleInfo);
    }


    public void setOnDiskSegmentsAfterShuffle(int onDiskSegsNumAfterShuffle) {
	this.onDiskSegsNumAfterShuffle = onDiskSegsNumAfterShuffle;
    }


    public void setInMemorySegmentsAfterShuffle(int inMemSegsNumAfterShuffle) {
	this.inMemSegsNumAfterShuffle = inMemSegsNumAfterShuffle;	
    }

}