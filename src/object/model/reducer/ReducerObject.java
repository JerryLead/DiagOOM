package object.model.reducer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dataflow.model.mapper.Segment;
import dataflow.model.reducer.Reducer;
import object.model.job.SegmentObj;
import object.model.job.UserObjectsPerDump;

public class ReducerObject {
    
    private Reducer reducer;

    // computed from dataflow
    private List<Segment> totalSegs;
    private List<Integer> processedSegs;
  
    // obtained from heap dump
    private List<SegmentObj> segsInCopy;
    private List<SegmentObj> segsInList;
    private List<SegmentObj> segsInMerge;
    
    private List<SegmentObj> leakedSegs;
    
    private List<SegmentObj> segsInBuffer;

    
    
    private List<UserObjectsPerDump> userObjsPerDumpList;
    
    
    
    public ReducerObject(Reducer reducer, List<SegmentObj> segments, List<UserObjectsPerDump> userObjsPerDump) {
	this.reducer = reducer;
	this.userObjsPerDumpList = userObjsPerDump;
	
	this.totalSegs = reducer.getSegsInShuffle().getTotalSegments();
	this.processedSegs = reducer.getSegsInShuffle().getProcessedSegments();
	
	Set<Integer> taskIdSet = new HashSet<Integer>();
	// SegmentsInCopy SegmentsInList SegmentsInMerge/Buffer minSegment keySegment comparatorSegment
	for(SegmentObj segObj : segments) {
	    
	    String location = segObj.getLocation();

	    if(location.equals("segsInCopy")) {
		segsInCopy.add(segObj);
		taskIdSet.add(segObj.getTaskId());
	    }
	    
	    else if(location.equals("SegmentsInList")) {
		segsInList.add(segObj);
		taskIdSet.add(segObj.getTaskId());
	    }
	    
	    else if(location.equals("SegmentsInMerge/Buffer")) {
		
		if(reducer.getRunningPhase().equals("shuffle")) 
		    segsInMerge.add(segObj);
		else
		    segsInBuffer.add(segObj);
		taskIdSet.add(segObj.getTaskId());
	    }
	}
	
	for(SegmentObj segObj : segments) {
	    
	    String location = segObj.getLocation();
	    
	    if(location.equals("minSegment") && !taskIdSet.contains(segObj.getTaskId()))
		leakedSegs.add(segObj);
	    else if(location.equals("keySegment") && !taskIdSet.contains(segObj.getTaskId()))
		leakedSegs.add(segObj);
	    else if(location.equals("comparatorSegment") && !taskIdSet.contains(segObj.getTaskId()))
		leakedSegs.add(segObj);
		
	}
    }
    
    public Reducer getReducer() {
	return reducer;
    }

    public List<Segment> getTotalSegs() {
        return totalSegs;
    }

    public List<SegmentObj> getSegsInCopy() {
        return segsInCopy;
    }

    public List<SegmentObj> getSegsInList() {
        return segsInList;
    }

    public List<SegmentObj> getSegsInMerge() {
        return segsInMerge;
    }

    public List<SegmentObj> getLeakedSegs() {
        return leakedSegs;
    }

    public List<SegmentObj> getSegsInBuffer() {
        return segsInBuffer;
    }

    public List<UserObjectsPerDump> getUserObjsPerDumpList() {
        return userObjsPerDumpList;
    }

    public List<Integer> getProcessedSegs() {
        return processedSegs;
    }
    
    
}
