package dataflow.model.reducer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import profile.commons.configuration.Configuration;
import profile.reducer.InMemoryShuffleMerge;
import profile.reducer.MergeInShuffle;
import profile.reducer.Shuffle;
import profile.reducer.ShuffleBuffer;
import dataflow.model.mapper.Segment;

public class SegmentsInShuffle {

    private long runtime_maxMemory;     // [---------------Runtime().maxMemory()---------------------]
    private long shuffleBound; 	        // [-------------- ShuffleBound --------------][--for other--]
    private long mergeBound;            // [------- MergeBound ------][--- unmerged --] 
    private long maxSingleRecordBytes; 
    
    private List<Segment> totalSegments;
    
    private List<Segment> shuffledSegments;
    
    private List<Segment> liveSegments;
    private List<Segment> segmentsInCopy;
    private List<Segment> segmentsInList;
    
    private List<Segment> segmentsInMerge;
   
    public SegmentsInShuffle() {
	this.shuffledSegments = new ArrayList<Segment>();
	this.liveSegments = new ArrayList<Segment>();
    }
    
    public void setBuffer(ShuffleBuffer buffer, Configuration conf) {
	shuffleBound = buffer.getShuffleBound();
	runtime_maxMemory = (long) (shuffleBound / conf.getMapred_job_shuffle_input_buffer_percent());
	mergeBound = (long) (shuffleBound * conf.getMapred_job_shuffle_merge_percent());
	
	maxSingleRecordBytes = buffer.getMaxSingleRecordBytes();	
    }

    public void setSegments(Shuffle shuffle, List<Segment> totalSegments, MergeInShuffle mergeInShuffle) {
	this.totalSegments = totalSegments;
	
	int[] shuffledSegIds = shuffle.getShuffledSegIds();
	Set<Integer> set = new HashSet<Integer>();
	
	for(int id : shuffledSegIds) {
	    shuffledSegments.add(totalSegments.get(id));
	    set.add(id);
	}
	
	for(InMemoryShuffleMerge merge : mergeInShuffle.getInMemoryShuffleMerges()) {
	    int[] mergeIds = merge.getSegMapperIds();
	    
	    for(int id : mergeIds) 
		set.remove(id);
	}
	
	for(Integer id : set) 
	    liveSegments.add(totalSegments.get(id));
    }

    public long getRecordsBeforeLastMerge(int[] ids) {
	
	long records = 0;
	
	Set<Integer> idSet = new HashSet<Integer>();
	for(int id : ids)
	    idSet.add(id);
	
	for(Segment s : shuffledSegments) {
	    if(idSet.contains(s.getTaskId())) 
		records += s.getRecords();
	}
	
	return records;
    }

    public List<Segment> getSegsInReduceBuffer(int[] ids) {
	List<Segment> list = new ArrayList<Segment>();
	
	Set<Integer> idSet = new HashSet<Integer>();
	for(int id : ids)
	    idSet.add(id);
	
	for(Segment s : shuffledSegments) {
	    if(idSet.contains(s.getTaskId())) 
		list.add(s);
	}
	
	return list;
    }

    public long getShuffledRecords() {
	long records = 0;
	
	for(Segment s : shuffledSegments) 
	   records += s.getRecords();
	
	return records;
    }
    
    
    
}
