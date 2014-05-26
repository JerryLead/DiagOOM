package profile.profiler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import profile.job.JobProfile;
import profile.job.JobTasksParser;
import profile.mapper.MapperInfo;
import profile.reducer.ReducerInfo;
import html.parser.job.JobConfigurationParser;
import html.parser.link.LinksSaver;
import html.parser.task.MapTaskParser;
import html.parser.task.ReduceTaskParser;

public class SingleJobProfiler {
    private JobProfile job;
    private LinksSaver linksSaver;
    private String oomTaskId;

    public SingleJobProfiler(String hostname, String jobId) {
	job = new JobProfile();
	linksSaver = new LinksSaver(hostname, jobId);
    }

    public SingleJobProfiler(String hostname, String jobId, String oomTaskId) {
	this(hostname, jobId);
	this.oomTaskId = oomTaskId;
    }

    public JobProfile profile() {


	JobConfigurationParser.parseJobConf(linksSaver, job); // get the
							      // configuration
							      // of this job
	JobTasksParser.parseJobTasks(linksSaver); // initiate map/reduce tasks
						  // link list

	parseMapperTasks();
	parseReducerTasks();

	return job;

    }

    private void parseMapperTasks() {

	for (int i = 0; i < linksSaver.getMap_tasks_list().size(); i++) {
	    MapperInfo newMapper = MapTaskParser.parseMapTask(linksSaver
		    .getMap_tasks_list().get(i));
	    job.addMapperInfo(newMapper);
	}

    }

    private void parseReducerTasks() {

	for (int i = 0; i < linksSaver.getReduce_tasks_list().size(); i++) {
	    ReducerInfo newReducer = ReduceTaskParser
		    .parseReduceTask(linksSaver.getReduce_tasks_list().get(i));
	    job.addReducerInfo(newReducer);
	}
    }

    private static void serialize(String serializeDir, JobProfile job, String jobId) {
	File jobFile = new File(serializeDir, jobId + ".out");
	if (!jobFile.getParentFile().exists())
	    jobFile.getParentFile().mkdirs();

	try {
	    FileOutputStream fos = new FileOutputStream(jobFile);
	    ObjectOutputStream oos = new ObjectOutputStream(fos);
	    oos.writeObject(job);
	    oos.flush();
	    oos.close();
	} catch (FileNotFoundException e) {    
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

    public static void main(String[] args) {
	String jobId = "job_201404282052_0002";
	String oomTaskId = "attempt_201404282052_0002_m_000000_0";
	
	String hostname = "master";
	
	String serializeDir = "/Users/xulijie/Documents/OOMCases/Mahout-classifier/first-oom-job/serialized";
	//String serializeDir = "/Users/xulijie/Documents/OOMCases/CooccurMatrix/heapdump-oom-job/serialized";

	SingleJobProfiler profiler = new SingleJobProfiler(hostname, jobId);

	JobProfile job = profiler.profile();
	
	outputDataflowInfo(serializeDir, job, oomTaskId);
	
	serialize(serializeDir, job, jobId);
    }

    private static void outputDataflowInfo(String serializeDir, JobProfile job, String oomTaskId) {
	
	int mapperoomId = -1;
	int reduceroomId = -1;
	
	if(oomTaskId != null) {
	    if(oomTaskId.contains("_m_"))
		mapperoomId = Integer.parseInt(oomTaskId.substring(oomTaskId.indexOf("_m_") + 3, oomTaskId.lastIndexOf('_')));
	    else if(oomTaskId.contains("_r_"))
		reduceroomId = Integer.parseInt(oomTaskId.substring(oomTaskId.indexOf("_r_") + 3, oomTaskId.lastIndexOf('_')));
	}
	
	if(oomTaskId == null)
	    oomTaskId = "mapper-0-reducer-0";
	
	File jobFile = new File(serializeDir, oomTaskId + ".txt");
	if (!jobFile.getParentFile().exists())
	    jobFile.getParentFile().mkdirs();

	if(mapperoomId == -1 && reduceroomId == -1) {
	    mapperoomId = 0;
	    reduceroomId = 0;
	}
	
	try {
	    PrintWriter writer = new PrintWriter(new FileWriter(jobFile));
	    
	    for(MapperInfo mapperInfo : job.getMapperInfoList()) {
		
		String taskId = mapperInfo.getTaskId();
		int id = Integer.parseInt(taskId.substring(taskId.indexOf("_m_") + 3, taskId.lastIndexOf('_')));
		
		if(id == mapperoomId) 
		    writer.println("## Mapper\n" + mapperInfo);
	    }
	    
	    for(ReducerInfo reducerInfo : job.getReducerInfoList()) {
		if(reducerInfo != null) {
		    String taskId = reducerInfo.getTaskId();
		    int id = Integer.parseInt(taskId.substring(taskId.indexOf("_r_") + 3, taskId.lastIndexOf('_')));
		
		    if(id == reduceroomId) 
			writer.println("## Reducer\n" + reducerInfo);
		}
	    }
	    
	    writer.close();
	    
	} catch (FileNotFoundException e) {    
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
