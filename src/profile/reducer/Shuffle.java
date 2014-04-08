package profile.reducer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Shuffle implements Serializable {

    private static final long serialVersionUID = 7661093817522537089L;

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
    
    public String toString() {
	StringBuilder sb = new StringBuilder();
	
	for(int i = 0; i < shuffleInfoList.size(); i++) {
	    ShuffleInfo info = shuffleInfoList.get(i);
	    sb.append("[Segment " + i + "] " + info.getCompressedLen() + ", " 
		    + info.getStoreLoc() + ", " + info.getSourceTaskId() + "\n");
	}
	
	return sb.toString();
    }

    public int[] getShuffledSegIds() {
	int[] ids = new int[shuffleInfoList.size()];
	
	for(int i = 0; i < shuffleInfoList.size(); i++) {
	    ShuffleInfo info = shuffleInfoList.get(i);
	    ids[i] = getTaskId(info.getSourceTaskId());
	}
	
	return ids;
    }
    
    public int getTaskId(String taskId) {
	return Integer.parseInt(taskId.substring(taskId.indexOf("_m_") + 3, taskId.lastIndexOf('_')));
    }
    
}