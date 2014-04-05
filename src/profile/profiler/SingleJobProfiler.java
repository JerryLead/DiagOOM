package profile.profiler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

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
	String jobId = "job_201404031543_0003";
	String oomTaskId = "attempt_201403211644_0007_m_000000_0";
	
	String hostname = "master";
	String serializeDir = "/Users/xulijie/Documents/DiagOOMSpace/PigMapJoin/";

	SingleJobProfiler profiler = new SingleJobProfiler(hostname, jobId);

	JobProfile job = profiler.profile();
	
	System.out.println("## Mapper");
	System.out.println(job.getMapperInfoList().get(0));
	System.out.println("\n## Reducer");
	System.out.println(job.getReducerInfoList().get(1));
	
	serialize(serializeDir, job, jobId);
    }
}
