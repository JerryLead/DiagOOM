package diagnosis.job;


import object.model.job.DumpedObjectsAnalyzer;
import object.model.mapper.MapperObject;
import object.model.mapper.MapperObjectModel;
import object.model.reducer.ReducerObject;
import object.model.reducer.ReducerObjectModel;
import dataflow.model.job.Job;
import dataflow.model.job.JobDataflowModelBuilder;
import dataflow.model.mapper.Mapper;
import dataflow.model.reducer.Reducer;
import profile.job.JobProfile;
import profile.profiler.JobProfileFromSerialization;


public class OOMAnalyzer {

    public static void main(String[] args) {
	String jobId = "job_201404061331_0008";
	String oomTaskId = "attempt_201403211644_0007_m_000000_0";
	
	String serializeDir = "/Users/xulijie/Documents/DiagOOMSpace/PigMapJoin/";

	// profile obtained from logs, counters, JVM usage and etc.
	JobProfile jobProfile = JobProfileFromSerialization.deserialization(serializeDir, jobId);

	// dataflow
	Job job = JobDataflowModelBuilder.buildDataflow(jobProfile);
	
	Mapper mapper = null;
	Reducer reducer = null;
	
	if(oomTaskId.contains("_m_")) {
	    int mapperId = Integer.parseInt(oomTaskId.substring(oomTaskId.indexOf("_m_") + 3, oomTaskId.lastIndexOf('_')));
	    mapper = job.getMappers().get(mapperId);
	}
	
	else {
	    int reducerId = Integer.parseInt(oomTaskId.substring(oomTaskId.indexOf("_r_") + 3, oomTaskId.lastIndexOf('_')));
	    reducer = job.getReducers().get(reducerId);
	}
	
	// in-memory framework and user objects
	DumpedObjectsAnalyzer analyzer = new DumpedObjectsAnalyzer(serializeDir);
	analyzer.parseEachDump();	
	
	if(mapper != null)
	    diagnoseMapper(mapper, analyzer);
	else if(reducer != null)
	    diagnoseReducer(reducer, analyzer);
	    
    }
    

    private static void diagnoseMapper(Mapper mapper, DumpedObjectsAnalyzer analyzer) {
	
	// put the dataflow and object information into an integrated class ==> MapperObject
	MapperObject mapperObj = new MapperObject(mapper, analyzer.getBufferObjs(), 
		analyzer.getUserObjsPerDumpList());
	
	MapperObjectModel mapperModel = new MapperObjectModel(mapperObj);
	mapperModel.buildModel();
	mapperModel.displayReport();
	
    }
    
    private static void diagnoseReducer(Reducer reducer, DumpedObjectsAnalyzer analyzer) {
	
	ReducerObject reducerObj = new ReducerObject(reducer, analyzer.getSegments(),
		analyzer.getUserObjsPerDumpList());
	
	ReducerObjectModel reducerModel = new ReducerObjectModel(reducerObj);
	reducerModel.buildModel();
	reducerModel.displayReport();
    }


    public void displayDataflow(Job job, int mapperId, int reducerId) {
	System.out.println("## Mapper");
	System.out.println(job.getMappers().get(mapperId));
	
	System.out.println("\n## Reducer");
	System.out.println(job.getReducers().get(reducerId));
    }
    
   
}
