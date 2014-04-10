package object.model.reducer;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dataflow.model.mapper.Segment;
import dataflow.model.reducer.Reducer;
import object.model.job.SegmentObj;
import object.model.job.UserObjectModel;
import object.model.job.UserObjectsPerDump;

public class ReducerObjectModel {

    private Reducer reducer;

    // In-memory framework objects
    List<SegmentObj> segsInCopy;
    List<SegmentObj> segsInList;
    List<SegmentObj> segsInMerge;
    
    List<SegmentObj> segsInBuffer;
    
    List<Segment> totalSegs;
    List<Integer> processedSegs;
    
    List<Integer> toBeAllocatedSegs;

    // Allocated user objects
    private List<UserObjectsPerDump> userObjsPerDumpList;
    
    private UserObjectModel combineObjModel;
    private UserObjectModel reduceObjModel;
    
    public ReducerObjectModel(ReducerObject reducerObj) {
	this.reducer = reducerObj.getReducer();
	
	segsInCopy = reducerObj.getSegsInCopy();
	segsInList = reducerObj.getSegsInList();
	segsInMerge = reducerObj.getSegsInMerge();
	
	totalSegs = reducerObj.getTotalSegs();
    	processedSegs = reducerObj.getProcessedSegs();
    	
    	userObjsPerDumpList = reducerObj.getUserObjsPerDumpList();
    }
    

    
    public void buildModel() {
	analyzeCurrentMemoryStatus();
	predictFutureMemoryStatus();
    }

    private void analyzeCurrentMemoryStatus() {
	System.out.println("================= Current Memory Status =================");
	
	System.out.println("## Current framework objects (TODO: add configurations)");
	    
	System.out.println("| Location \t | FrameworkObj \t| Inner object \t| retainedHeap \t| Reason (from mapper) \t|");
	System.out.println("| :----------- | :----------- | :----------- | -----------: | :----------- |");
		
	for(SegmentObj sObj : segsInCopy) 
	    System.out.println(sObj);
	for(SegmentObj sObj : segsInList)
	    System.out.println(sObj);
	for(SegmentObj sObj : segsInMerge)
	    System.out.println(sObj);
	for(SegmentObj sObj : segsInBuffer)
	    System.out.println(sObj);
	
	
	
	System.out.println("\n## Current user objects");
	
	
	List<UserObjectsPerDump> combineObjsList = new ArrayList<UserObjectsPerDump>();
	List<UserObjectsPerDump> reduceObjsList = new ArrayList<UserObjectsPerDump>();
	// analyze user objects
	
	for(UserObjectsPerDump dump : userObjsPerDumpList) {
	    if(dump.getCounterName().contains("combine")) 
		combineObjsList.add(dump);
	    else
		reduceObjsList.add(dump);
	}
	
	if(!combineObjsList.isEmpty()) {
	    computeRealCombineInputRecords(combineObjsList);
	    combineObjModel = new UserObjectModel(combineObjsList, "combine");
	    combineObjModel.buildModel();
	    combineObjModel.display();
	}
	
	if(!reduceObjsList.isEmpty()) {
	    reduceObjModel = new UserObjectModel(reduceObjsList, "reduce");
	    reduceObjModel.buildModel();
	    reduceObjModel.display();
	}
	
    }

    private void computeRealCombineInputRecords(List<UserObjectsPerDump> combineObjsList) {
	
	long inputRecsInPreviousMerges = reducer.getMergeCombineFunc().getInputRecsInPreviousMerges();
	    
	for(UserObjectsPerDump objsPerDump : combineObjsList) 
	    objsPerDump.setCounter(objsPerDump.getCounter() - inputRecsInPreviousMerges);    
    }

    private void predictFutureMemoryStatus() {
	
	System.out.println("================= Future Memory Status =================");
	
	//
	// predict framework objects
	//
	
	System.out.println("## To-be-allocated framework objects");
	if(reducer.getRunningPhase().equals("shuffle")) {
	    computeToBeAllocatedObjs();
	}
	
	//
	// predict user objects
	//
	
	System.out.println("\n## Total user objects");
	
	if(reducer.getRunningPhase().equals("shuffle") && reducer.getMergeCombineFunc() != null) {
	    long tCombineInputRecords = reducer.getMergeCombineFunc().gettCombineInputRecords();
	    combineObjModel.predict(tCombineInputRecords);
	}
	
	else if(reducer.getRunningPhase().equals("reduce")) {
	    long tReduceInputRecords = reducer.getReduceFunc().gettReduceInputRecords();
	    reduceObjModel.predict(tReduceInputRecords);
	}
    }

    // if ShuffleBound not full && some segments has not been put into memory 
    
    public void computeToBeAllocatedObjs() {
	long shuffleBound = reducer.getSegsInShuffle().getShuffleBound();

	Set<Integer> inMemorySegIds = new HashSet<Integer>();
	inMemorySegIds.addAll(processedSegs);
	
	long inMemSegsBytes = 0;
	
	for(SegmentObj seg : segsInCopy) {
	    inMemorySegIds.add(seg.getTaskId());
	    inMemSegsBytes += seg.getRetainedHeap();
	}
	
	for(SegmentObj seg : segsInList) {
	    inMemorySegIds.add(seg.getTaskId());
	    inMemSegsBytes += seg.getRetainedHeap();
	}
	
	for(SegmentObj seg : segsInMerge) {
	    inMemorySegIds.add(seg.getTaskId());
	    inMemSegsBytes += seg.getRetainedHeap();
	}
	
	long leftShuffleBound = shuffleBound - inMemSegsBytes;
	
	
	long toBeAllocatedSegsTotalBytes = 0;
	for(Segment seg : totalSegs) {
	    if(!inMemorySegIds.contains(seg.getTaskId())) {
		toBeAllocatedSegs.add(seg.getTaskId());
		toBeAllocatedSegsTotalBytes += seg.getBytes();
	    }
	}
	
	long totalSegsBytesInShuffleBound;
	if(toBeAllocatedSegsTotalBytes >= leftShuffleBound)
	    totalSegsBytesInShuffleBound = shuffleBound;
	else
	    totalSegsBytesInShuffleBound = inMemSegsBytes + toBeAllocatedSegsTotalBytes;
	

	System.out.println("## To-be-allocated framework objects (totally occupy " 
		+ new DecimalFormat(",###").format(totalSegsBytesInShuffleBound) + " Bytes)");
	    
	System.out.println("| Location \t | FrameworkObj \t| Inner object \t| retainedHeap \t| Reason (from mapper) \t|");
	System.out.println("| :----------- | :----------- | :----------- | -----------: | :----------- |");
		
	for(Integer sId : toBeAllocatedSegs) 
	    System.out.println(totalSegs.get(sId));
	
	System.out.println();
    }
    
    public void displayReport() {
	// TODO Auto-generated method stub
	
    }
}
