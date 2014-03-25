package profile.mapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Spill implements Serializable {
	private boolean hasCombine;
	
	private List<SpillInfo> spillInfoList = new ArrayList<SpillInfo>();
	
	//add spill infos
	public void addSpillItem(
		boolean hasCombine, 
		String reason, 
		long recordsBeforeCombine, 
		long bytesBeforeSpill,
		long recordAfterCombine, 
		long rawLength, 
		long compressedLength) {
	    
	this.hasCombine = hasCombine;
	spillInfoList.add(new SpillInfo(hasCombine, reason,
		recordsBeforeCombine, bytesBeforeSpill, recordAfterCombine,
		rawLength, compressedLength));

    }
	
	
}
