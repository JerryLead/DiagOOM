package profile.reducer;

import java.io.Serializable;

public class ShuffleCombine implements Serializable {
    
    private static final long serialVersionUID = 7864286923785524496L;
    
    private int[] segMapperIds;
    private long currentCombineInputRecords;
    private long currentCombineOutputRecords; 
    
    public ShuffleCombine(int[] segMapperIds, long currentCombineInputRecords, long currentCombineOutputRecords) {
	this.segMapperIds = segMapperIds;
	this.currentCombineInputRecords = currentCombineInputRecords;
	this.currentCombineOutputRecords = currentCombineOutputRecords;
    }

    public int[] getSegMapperIds() {
        return segMapperIds;
    }

    public long getCurrentCombineInputRecords() {
        return currentCombineInputRecords;
    }

    public long getCurrentCombineOutputRecords() {
        return currentCombineOutputRecords;
    }
    
    
}
