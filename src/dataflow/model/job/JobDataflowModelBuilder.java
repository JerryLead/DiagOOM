package dataflow.model.job;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import dataflow.model.mapper.Mapper;
import dataflow.model.reducer.Reducer;
import profile.job.JobProfile;
import profile.profiler.JobProfileFromSerialization;

public class JobDataflowModelBuilder {
   
    
    public static void main(String[] args) {
	String jobId = "job_201404152256_0003";

	String serializeDir = "/Users/xulijie/Documents/DiagOOMSpace/Count-distinct-reducer/heapdump-oom-job/serialized/";

	String outputDir = "/Users/xulijie/Documents/DiagOOMSpace/Count-distinct-reducer/diagnosis/dataflow";
	
	JobProfile jobProfile = JobProfileFromSerialization.deserialization(serializeDir, jobId);

	Job job = buildDataflow(jobProfile);
	

	outputDataflow(job, outputDir + File.separatorChar + jobId + "-dataflow.txt");
	
    }

    private static void outputDataflow(Job job, String outputFile) {
	File file = new File(outputFile);
	
	if (!file.getParentFile().exists())
	    file.getParentFile().mkdirs();
	
	try {
	    PrintWriter writer = new PrintWriter(new FileWriter(file));
	    
	    writer.println("## Mapper");
	    writer.println(job.getMappers().get(0));
	    
	    writer.println("## Reducer");
	    writer.println(job.getReducers().get(2));
	    
	    writer.close();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	
	
	
    }

    public static Job buildDataflow(JobProfile jobProfile) {
	List<Mapper> mappers = MapperDataflowBuilder.build(jobProfile);
	List<Reducer> reducers = ReducerDataflowBuilder.build(jobProfile, mappers);
	
	Job job = new Job();
	job.setMappsers(mappers);
	job.setReducers(reducers);
	
	return job;
    }

}
