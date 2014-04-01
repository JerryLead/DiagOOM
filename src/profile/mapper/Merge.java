package profile.mapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Merge implements Serializable {


    private static final long serialVersionUID = 3128954196895626833L;
    
    private List<MergeInfo> mergeInfoList = new ArrayList<MergeInfo>();
    
    boolean hasCombine;

    public void addBeforeMergeItem(
	    int partitionId,
	    boolean hasCombine, 
	    int segmentsNum, 
	    long records,
	    long rawLengthBeforeMerge,
	    long compressedLengthBeforeMerge) {
	this.hasCombine = hasCombine;

	MergeInfo mergeInfo = new MergeInfo(partitionId, segmentsNum,
		records,
		rawLengthBeforeMerge, compressedLengthBeforeMerge);
	mergeInfoList.add(mergeInfo);
    }

    public void addAfterMergeItem(
	    int partitionId,
	    long recordsAfterMerge, 
	    long rawLengthEnd,
	    long compressedLengthEnd) {
	
	MergeInfo mergeInfo = mergeInfoList.get(partitionId);
	mergeInfo.setAfterMergeItem(recordsAfterMerge,
		rawLengthEnd, compressedLengthEnd);

    }
    
    public List<MergeInfo> getMergeInfoList() {
	return mergeInfoList;
    }
    
    public boolean hasCombine() {
	return hasCombine;
    }
    
    public String toString() {
	StringBuilder sb = new StringBuilder();
	
	for(int i = 0; i < mergeInfoList.size(); i++) {
	    MergeInfo info = mergeInfoList.get(i);
	    sb.append("[Segment " + i + "] " + "records = " + info.getRecordsBeforeMerge() + " | " 
		    + info.getRecordsAfterMerge() + ", bytes = " + info.getRawLengthBeforeMerge()
		    + " | " + info.getRawLengthAfterMerge() + "\n");
	}
	
	return sb.toString();
    }
    
    
}
