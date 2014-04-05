package object.model.reducer;

import java.util.List;

import dataflow.model.reducer.Reducer;
import object.model.job.SegmentObj;
import object.model.job.UserObject;

public class ReducerObject {
    private Reducer reducer;

    private List<SegmentObj> totalSegs;
    
    private List<Integer> segsInCopy;
    private List<Integer> segsInList;
    private List<Integer> segsInMerge;
    
    private List<Integer> segsInBuffer;

    private List<Integer> processedSegs;
    
    private List<UserObject> userObjs;
    
    public ReducerObject() {
	
    }
    
    public Reducer getReducer() {
	return reducer;
    }

    public List<Integer> getSegsInCopy() {
        return segsInCopy;
    }

    public List<Integer> getSegsInList() {
        return segsInList;
    }

    public List<Integer> getSegsInMerge() {
        return segsInMerge;
    }

    public List<Integer> getSegsInBuffer() {
        return segsInBuffer;
    }

    public List<UserObject> getUserObjs() {
	return userObjs;
    }
    
    public List<Integer> getProcessedSegs() {
	return processedSegs;
    }
    
    public List<SegmentObj> getTotalSegs() {
        return totalSegs;
    }
    
    
}
