package dataflow.model.job;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dataflow.model.mapper.Mapper;
import profile.job.JobProfile;
import profile.mapper.MapperInfo;

public class MapperDataflowBuilder {

    public static List<Mapper> build(JobProfile jobProfile) {
	
	List<Mapper> mappers = new ArrayList<Mapper>();
	
	for(MapperInfo info : jobProfile.getMapperInfoList()) {
	    Mapper mapper = new Mapper(jobProfile.getJobConfiguration());
	    
	    mapper.setBasicInfo(info.getTaskId(), info.isMapRunning(), info.getRunningPhase());
	    mapper.setInputSplit(info.getInput());
	    mapper.setMapFunc(info.getCounters());
	    mapper.setSpillBuffer(info.getBuffer());
	    mapper.setSpills(info.getSpill(), info.getCounters());
	    mapper.setMerges(info.getMerge(), info.getCounters());
	    
	    mappers.add(mapper);
	}
	
	Collections.sort(mappers);
	return mappers;
    }

}
 