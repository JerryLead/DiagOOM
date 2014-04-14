package dataflow.model.job;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import profile.job.JobProfile;
import profile.reducer.ReducerInfo;
import dataflow.model.mapper.Mapper;
import dataflow.model.reducer.Reducer;


public class ReducerDataflowBuilder {

    public static List<Reducer> build(JobProfile jobProfile, List<Mapper> mappers) {
	List<Reducer> reducers = new ArrayList<Reducer>();
	
	for(ReducerInfo info : jobProfile.getReducerInfoList()) {
	
	    Reducer reducer = new Reducer(jobProfile.getJobConfiguration());
	    
	    reducer.setBasicInfo(info.getTaskId(), info.getRunningPhase(), info.isInMemMergeRunning());
	    reducer.setShuffle(mappers, info.getShuffle(), info.getShuffleBuffer(), info.getMergeInShuffle());
	    reducer.setMergeCombineFunc(info.getMergeInShuffle(), info.getReducerCounters());
	    reducer.setSegmentInRedeuceBuffer(info.getSort());
	    reducer.setReduce(info.getReducerCounters());
	    
	    reducers.add(reducer);
	}
	
	Collections.sort(reducers);
	
	return reducers;
    }

}
