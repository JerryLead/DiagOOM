package dataflow.model.reducer;

import java.text.DecimalFormat;
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
    
    private List<Integer> shuffledSegments;
    
    private List<Integer> liveSegments;
    
    private List<Integer> processedSegments; // have been added into memory
   
    public SegmentsInShuffle() {
	this.shuffledSegments = new ArrayList<Integer>();
	this.liveSegments = new ArrayList<Integer>();
	this.processedSegments = new ArrayList<Integer>();
    }
    
    public String toString() {
	DecimalFormat f = new DecimalFormat(",###");
	
	StringBuilder sb = new StringBuilder();
	sb.append("[Runtime_heap] = " + f.format(runtime_maxMemory) + "\n");
	sb.append("[ShuffleBound] = " + f.format(shuffleBound) + "\n");
	sb.append("[MergeBound]   = " + f.format(mergeBound) + "\n\n");
	
	sb.append("------------ Total segments ------------\n");
	for(Segment s : totalSegments) 
	    sb.append(s + "\n");
	
	sb.append("\n");
	
	sb.append("[ShuffledSegments] = " + shuffledSegments + "\n");
	sb.append("[addedIntoMemorySegs] = " + processedSegments + "\n");
	
	return sb.toString();
	
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
	    shuffledSegments.add(id);
	    set.add(id);
	}
	
	for(InMemoryShuffleMerge merge : mergeInShuffle.getInMemoryShuffleMerges()) {
	    int[] mergeIds = merge.getSegMapperIds();
	    
	    for(int id : mergeIds) {
		set.remove(id);
		processedSegments.add(id);
	    }
	}
	
	for(Integer id : set) 
	    liveSegments.add(id);
    }

    public long getRecordsBeforeLastMerge(int[] ids) {
	
	long records = 0;
	
	Set<Integer> idSet = new HashSet<Integer>();
	for(int id : ids)
	    idSet.add(id);
	
	for(int id : shuffledSegments) {
	    if(idSet.contains(id)) 
		records += totalSegments.get(id).getRecords();
	}
	
	return records;
    }

    public List<Segment> getSegsInReduceBuffer(int[] ids) {
	List<Segment> list = new ArrayList<Segment>();
	
	Set<Integer> idSet = new HashSet<Integer>();
	for(int id : ids)
	    idSet.add(id);
	
	for(int id : shuffledSegments) {
	    if(idSet.contains(id)) 
		list.add(totalSegments.get(id));
	}
	
	return list;
    }

    public long getShuffledRecords() {
	long records = 0;
	
	for(int id : shuffledSegments) 
	   records += totalSegments.get(id).getRecords();
	
	return records;
    }
    
    public long getShuffleBound() {
	return shuffleBound;
    }

    public List<Segment> getTotalSegments() {
        return totalSegments;
    }

    public List<Integer> getProcessedSegments() {
        return processedSegments;
    }
    
}
