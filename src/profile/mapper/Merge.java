package profile.mapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Merge implements Serializable {

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
}
