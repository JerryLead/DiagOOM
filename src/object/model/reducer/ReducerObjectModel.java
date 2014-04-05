package object.model.reducer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import object.model.job.SegmentObj;
import object.model.job.UserObject;

public class ReducerObjectModel {

    private ReducerObject reducerObj;
    private List<Integer> toAllocateObjs;

    private List<UserObject> allocatedUObjs;
    
    public ReducerObjectModel(ReducerObject reducerObj) {
	this.reducerObj = reducerObj;
	this.toAllocateObjs = new ArrayList<Integer>();
    }
    
    // if ShuffleBound not full && some segments has not been put into memory 
    
    public void computeToAllocateFObjs() {
	long shuffleBound = reducerObj.getReducer().getSegsInShuffle().getShuffleBound();
	
	List<Integer> segsInCopy = reducerObj.getSegsInCopy();
	List<Integer> segsInList = reducerObj.getSegsInList();
	List<Integer> segsInMerge = reducerObj.getSegsInMerge();
	
	List<Integer> segsInBuffer = reducerObj.getSegsInBuffer();
	
	List<SegmentObj> totalSegs = reducerObj.getTotalSegs();
	
	List<Integer> processedSegs = reducerObj.getProcessedSegs();
	
	Set<Integer> inMemorySegIds = new HashSet<Integer>();
	inMemorySegIds.addAll(processedSegs);
	inMemorySegIds.addAll(segsInCopy);
	inMemorySegIds.addAll(segsInList);
	inMemorySegIds.addAll(segsInMerge);
	
	
	for(SegmentObj seg : totalSegs) {
	    if(!inMemorySegIds.contains(seg.getTaskId())) {
		toAllocateObjs.add(seg.getTaskId());
	    }
	}
    }
    
    public void computeUserObjects() {
	
    }
}
