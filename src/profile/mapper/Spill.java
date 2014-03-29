package profile.mapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Spill implements Serializable {
 
    private static final long serialVersionUID = 1587498702838220791L;

    private boolean hasCombine = false;

    private List<SpillInfo> spillInfoList = new ArrayList<SpillInfo>();

    // add spill infos
    public void addSpillItem(boolean hasCombine, String reason,
	    long recordsBeforeCombine, long bytesBeforeSpill,
	    long recordAfterCombine, long rawLength, long compressedLength) {

	this.hasCombine = hasCombine;
	spillInfoList.add(new SpillInfo(hasCombine, reason,
		recordsBeforeCombine, bytesBeforeSpill, recordAfterCombine,
		rawLength, compressedLength));

    }
    
    public boolean getHasCombine() {
	return hasCombine;
    }
    
    public String toString() {
	StringBuilder sb = new StringBuilder();
	
	for(int i = 0; i < spillInfoList.size(); i++) {
	    SpillInfo info = spillInfoList.get(i);
	    sb.append("[Spill " + i + "] " + "records = " + info.getRecordsBeforeCombine() + " | " 
		    + info.getRecordsAfterCombine() + ", bytes = " + info.getBytesBeforeSpill()
		    + " | " + info.getRawLength() + "\n");
	}
	
	return sb.toString();
    }
    

}
