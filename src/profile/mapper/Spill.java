package profile.mapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Spill implements Serializable {
 
    private static final long serialVersionUID = 1587498702838220791L;

    private boolean hasCombine = false;

    private List<SpillInfo> spillInfoList = new ArrayList<SpillInfo>();

    private MemCombine currentCombine;
    /* // add spill infos
    public void addSpillItem(boolean hasCombine, String reason,
	    long recordsBeforeCombine, long bytesBeforeSpill,
	    long recordAfterCombine, long rawLength, long compressedLength) {

	this.hasCombine = hasCombine;
	spillInfoList.add(new SpillInfo(hasCombine, reason,
		recordsBeforeCombine, bytesBeforeSpill, recordAfterCombine,
		rawLength, compressedLength));

    }
    */
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
    
    public List<SpillInfo> getSpillInfoList() {
	return spillInfoList;
    }

    public void addBeforeSpillItem(String reason, long records,
	    long bytes) {
	spillInfoList.add(new SpillInfo(reason, records, bytes));
	
    }

    public void addAfterSpillItem(int spillid, boolean hasCombine,
	    long recordAfterCombine, long rawLength, long compressedLength) {
	this.hasCombine = hasCombine;
	spillInfoList.get(spillid).setAfterSpillInfo(hasCombine, recordAfterCombine,
		rawLength, compressedLength);
	
    }

    public MemCombine getCurrentCombine() {
        return currentCombine;
    }

    public void setCurrentCombine(MemCombine currentCombine) {
        this.currentCombine = currentCombine;
    }
    
    

}
