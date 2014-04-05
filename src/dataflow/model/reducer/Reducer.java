package dataflow.model.reducer;

import java.util.ArrayList;
import java.util.List;

import profile.commons.configuration.Configuration;
import profile.mapper.SpillInfo;
import profile.reducer.FinalSortMerge;
import profile.reducer.InMemoryShuffleMerge;
import profile.reducer.MergeInShuffle;
import profile.reducer.ReducerCounters;
import profile.reducer.Shuffle;
import profile.reducer.ShuffleBuffer;
import profile.reducer.Sort;
import dataflow.model.mapper.Mapper;
import dataflow.model.mapper.Segment;


public class Reducer {

    // e.g., attempt_201404012254_0001_r_000000_0
    private String taskId;
    private String machine;

    private String runningPhase = "init";
    private boolean isInMemMergeRunning = false;
    private Configuration conf;
    private int id;
    
    
    private SegmentsInShuffle segsInShuffle;
  
    private MergeCombineFunc mergeCombineFunc;
    private List<OnDiskSeg> onDiskSegs;
    private List<Segment> segmentsInReduceBuf;
    private ReduceFunc reduceFunc;
    
    
    public Reducer(Configuration jobConfiguration) {
	this.conf = jobConfiguration;
	
	segsInShuffle = new SegmentsInShuffle();
	
	if(conf.getMapreduce_combine_class() != null)
	    mergeCombineFunc = new MergeCombineFunc();
	
	reduceFunc = new ReduceFunc();
    }

    public void setBasicInfo(String taskId, String runningPhase, boolean isInMemMergeRunning) {
	this.taskId = taskId;
	this.runningPhase = runningPhase;
	this.isInMemMergeRunning = isInMemMergeRunning;
	
	id = Integer.parseInt(taskId.substring(taskId.indexOf("_r_") + 3, taskId.lastIndexOf('_')));
    }
    
    public void setShuffle(List<Mapper> mappers, Shuffle shuffle, ShuffleBuffer buffer, MergeInShuffle mergeInShuffle) {
	
	
	List<Segment> totalSegments = new ArrayList<Segment>();
	
	for(Mapper mapper : mappers) {
	    Segment seg = mapper.getMapOutputSegs().get(id);
	    totalSegments.add(seg);
	}
	
	segsInShuffle.setBuffer(buffer, conf);
	segsInShuffle.setSegments(shuffle, totalSegments, mergeInShuffle);
	
    }

    public void setMergeCombineFunc(MergeInShuffle mergeInShuffle, ReducerCounters counters) {
	
	long inputRecsInPreviousMerges = 0;
	long outputRecsInPreviousMerges = 0;
	
	List<InMemoryShuffleMerge> list = mergeInShuffle.getInMemoryShuffleMerges();
	
	InMemoryShuffleMerge info = null;
	for(int i = 0; i < list.size(); i++) {
	    info = list.get(i);
	    
	    if(i != list.size() - 1) {
		inputRecsInPreviousMerges += info.getRecordsBeforeMergeAC();
		outputRecsInPreviousMerges += info.getRecordsAfterCombine();
	    }
	 
	}
	
	if(mergeCombineFunc != null && info != null) {
	    
	    InMemoryShuffleMerge lastMerge = list.get(list.size() - 1);
	    int ids[] = lastMerge.getSegMapperIds();
	    long recordsBeforeLastMerge = segsInShuffle.getRecordsBeforeLastMerge(ids);
	    
	    mergeCombineFunc.settCombineInputRecords(recordsBeforeLastMerge);
	    
	    mergeCombineFunc.setcCombineInputRecords(counters.getCombine_input_records() - inputRecsInPreviousMerges);
	    mergeCombineFunc.setcCombineOutputRecords(counters.getCombine_output_records() - outputRecsInPreviousMerges);
	    
	}
    }

    public void setSegmentInRedeuceBuffer(Sort sort) {
	FinalSortMerge finalSortMerge = sort.getFinalSortMerge();
	if(finalSortMerge != null) {
	    int[] taskIds = finalSortMerge.getSegIdsInReduceBuffer();
	    
	    segmentsInReduceBuf = segsInShuffle.getSegsInReduceBuffer(taskIds);
	}
	
    }

    public void setReduce(ReducerCounters counters) {
	
	reduceFunc = new ReduceFunc();
	
	long tReduceInputRecords = segsInShuffle.getShuffledRecords() - counters.getCombine_input_records()
		+ counters.getCombine_output_records();
	
	reduceFunc.settReduceInputRecords(tReduceInputRecords);
	
	reduceFunc.setcReduceInputRecords(counters.getReduce_input_records());
	reduceFunc.setcReduceInputGroups(counters.getReduce_input_groups());
	reduceFunc.setcReduceOutputRecords(counters.getReduce_output_records());
    }
    
    public SegmentsInShuffle getSegsInShuffle() {
	return segsInShuffle;
    }
   
    
}
