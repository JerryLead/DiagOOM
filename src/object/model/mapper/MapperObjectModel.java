package object.model.mapper;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dataflow.model.mapper.Mapper;
import object.model.job.BufferObject;
import object.model.job.SizeModel;
import object.model.job.UserObjectModel;
import object.model.job.UserObjectsPerDump;


public class MapperObjectModel {
    
    private List<BufferObject> allocatedBuffers;
    private List<BufferObject> allBuffers;

    //private List<Long> mapInputRecordsPerDumpList;
    //private List<Long> combineInputRecordsPerDumpList;
    
    private List<UserObjectsPerDump> userObjsList;
    
    private String runningPhase;
    
    //
    private UserObjectModel mapObjModel;
    private UserObjectModel combineObjModel;
    
    private Set<String> allocatedObjNames;
    
    private Mapper mapper;
    
    public MapperObjectModel(MapperObject mapperObj) {
	
	// get the allocated framework objects
	allocatedBuffers = mapperObj.getBufferObjs();
	
	// compute all the framework objects
	allBuffers = new ArrayList<BufferObject>();

	if (mapperObj.getMapper().hasReducer() == true) {

	    long kvbufferBytes = mapperObj.getMapper().getSpillBuffer().getKvbuffer();
	    long kvoffsetsBytes = mapperObj.getMapper().getSpillBuffer().getKvoffsets();
	    long kvindicesBytes = mapperObj.getMapper().getSpillBuffer().getKvindices();

	    BufferObject kvbuffer = new BufferObject("kvbuffer", "byte[]", 0, kvbufferBytes);
	    BufferObject kvoffsets = new BufferObject("kvoffsets", "int[]", 0, kvoffsetsBytes);
	    BufferObject kvindices = new BufferObject("kvindices", "int[]", 0, kvindicesBytes);
	    
	    allBuffers.add(kvbuffer);
	    allBuffers.add(kvoffsets);
	    allBuffers.add(kvindices);
	}

	// get the user objects
	userObjsList = mapperObj.getUserObjs();
	
	runningPhase = mapperObj.getMapper().getRunningPhase();
	
	allocatedObjNames = new HashSet<String>();
	
	this.mapper = mapperObj.getMapper();
    }
    
    public void analyzeCurrentMemoryStatus() {
	
	System.out.println("================= Current Memory Status =================");
	
	System.out.println("## Current framework objects");
	// analyze framework objects
	if(!allocatedBuffers.isEmpty()) {
	    System.out.println("| FrameworkObj | InnerObj | bytes | Reason |");
	    System.out.println("|:------------|------------:|-------------:|:------------|");
	}
	
	for(BufferObject buffer : allocatedBuffers) {
	    displayBufferObject(buffer);
	    allocatedObjNames.add(buffer.getName());
	}
	
	System.out.println("\n## Current user objects");
	
	
	List<UserObjectsPerDump> mapObjsList = new ArrayList<UserObjectsPerDump>();
	List<UserObjectsPerDump> combineObjsList = new ArrayList<UserObjectsPerDump>();
	// analyze user objects
	
	for(UserObjectsPerDump dump : userObjsList) {
	    if(dump.getCounterName().contains("map")) 
		mapObjsList.add(dump);
	    else
		combineObjsList.add(dump);
	}
	
	if(!mapObjsList.isEmpty()) {
	    mapObjModel = new UserObjectModel(mapObjsList, "map");
	    mapObjModel.buildModel();
	    mapObjModel.display();
	}
	
	if(!combineObjsList.isEmpty()) {
	    computeRealCombineInputRecords(combineObjsList);
	    
	    combineObjModel = new UserObjectModel(combineObjsList, "combine");
	    combineObjModel.buildModel();
	    combineObjModel.display();
	}
	    
    }
    
    private void computeRealCombineInputRecords(List<UserObjectsPerDump> combineObjsList) {
	if(runningPhase.equals("spill")) {
	    long inputRecsInPreviousSpills = mapper.getMemCombineFunc().getInputRecsInPreviousSpills();
	    
	    for(UserObjectsPerDump objsPerDump : combineObjsList) 
		objsPerDump.setCounter(objsPerDump.getCounter() - inputRecsInPreviousSpills);
	    
	}
	else {
	    long inputRecsInPreviousMerges = mapper.getDiskCombineFunc().getInputRecsInPreviousMerges();
	    
	    for(UserObjectsPerDump objsPerDump : combineObjsList) 
		objsPerDump.setCounter(objsPerDump.getCounter() - inputRecsInPreviousMerges);
	}
	
    }

    private void displayBufferObject(BufferObject buffer) {
	DecimalFormat f = new DecimalFormat(",###");
	
	String reason = "";
	if(buffer.getName().equals("kvbuffer"))
	    reason = "io.sort.mb * (1 - io.sort.record.percent)";
	else if(buffer.getName().equals("kvoffsets")) 
	    reason = "io.sort.mb * io.sort.record.percent / 4";
	else if(buffer.getName().equals("kvindices"))
	    reason = "3 * kvoffsets";
	
	// | FrameworkObj | InnerObj | bytes | Reason |
	System.out.println("| " + buffer.getName() + " | " + buffer.getInnerObject() + " | " 
		+ f.format(buffer.getRetainedHeap()) + " | " + reason + " |");
	
    }

    public void predictFutureMemoryStatus() {
	
	System.out.println("================= Future Memory Status =================");
	
	//
	// predict framework objects
	//
	
	System.out.println("## To-be-allocated framework objects");
	if(runningPhase.equals("init") || runningPhase.equals("map") || runningPhase.equals("spill")) {
	    for(BufferObject buffer : allBuffers) {
		if(!allocatedObjNames.contains(buffer.getName())) 
		    displayBufferObject(buffer);
	    }
	}
	
	//
	// predict user objects
	//
	
	System.out.println("\n## Total user objects");
	
	if(runningPhase.equals("map")) {
	    long etotalMapInRecords = mapper.getMapFunc().predictTotalInputRecords();
	    mapObjModel.predict(etotalMapInRecords);
	    
	}
	
	else if(runningPhase.equals("spill")) {

	    // no combine()
	    if(mapper.getMemCombineFunc() == null) {		    
		long etotalMapInRecords = mapper.getMapFunc().predictTotalInputRecords();
		mapObjModel.predict(etotalMapInRecords);
	    }
	    
	    else {
		// only combine() 
		if(mapper.isMapRunning() == false) {
		    long tCombineInRecords = mapper.getMemCombineFunc().gettCombineInputRecords();
		    combineObjModel.predict(tCombineInRecords);
		}
		
		// combine() and map()
		else {
		    long etotalMapInRecords = mapper.getMapFunc().predictTotalInputRecords();
		    long tCombineInRecords = mapper.getMemCombineFunc().gettCombineInputRecords();
		    
		    mapObjModel.predict(etotalMapInRecords);
		    combineObjModel.predict(tCombineInRecords);
		}
	    }
	    
	}
	

	else if(runningPhase.equals("merge")) {
	    if(mapper.getDiskCombineFunc() != null) {
		long tCombineInRecords = mapper.getDiskCombineFunc().gettCombineInputRecords();
		
		combineObjModel.predict(tCombineInRecords);
	    }
	}
    }

    public void displayReport() {
	
	
    }

    public void buildModel() {
	analyzeCurrentMemoryStatus();
	predictFutureMemoryStatus();	
    }

    
}
