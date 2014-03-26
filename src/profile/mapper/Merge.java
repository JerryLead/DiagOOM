package profile.mapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Merge implements Serializable {


    private static final long serialVersionUID = 3128954196895626833L;
    
    private List<MergeInfo> mergeInfoList = new ArrayList<MergeInfo>();

    public void addBeforeMergeItem(
	    int partitionId,
	    int segmentsNum, 
	    long rawLengthBeforeMerge,
	    long compressedLengthBeforeMerge) {

	MergeInfo mergeInfo = new MergeInfo(partitionId, segmentsNum,
		rawLengthBeforeMerge, compressedLengthBeforeMerge);
	mergeInfoList.add(mergeInfo);
    }

    public void addAfterMergeItem(
	    int partitionId,
	    long recordsBeforeMerge, 
	    long recordsAfterMerge, 
	    long rawLengthEnd,
	    long compressedLengthEnd) {
	
	MergeInfo mergeInfo = mergeInfoList.get(partitionId);
	mergeInfo.setAfterMergeItem(recordsBeforeMerge, recordsAfterMerge,
		rawLengthEnd, compressedLengthEnd);

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
