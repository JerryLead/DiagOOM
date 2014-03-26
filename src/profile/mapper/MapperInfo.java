package profile.mapper;

import java.util.ArrayList;
import java.util.List;

import profile.commons.metrics.JvmUsage;

public class MapperInfo {

    private String taskId;
    private String machine;
    private boolean isMapRunning;
    private String runningPhase;

    // map phase information
    private Input input;

    private MapperBuffer buffer;
    private Spill spill;
    private Merge merge;

    private MapperCounters counters;

    private JvmUsage jvmUsage;

    private List<String> heapdumps;
    
    public MapperInfo() {
	this.runningPhase = "init";
	this.isMapRunning = false;
	
	this.input = new Input();
	this.buffer = new MapperBuffer();
	this.spill = new Spill();
	this.merge = new Merge();
	this.counters = new MapperCounters();
	this.jvmUsage = new JvmUsage();
	this.heapdumps = new ArrayList<String>();
    }
    
    public String toString() {
	StringBuilder sb = new StringBuilder();

	sb.append("------------ RunningStatus ------------\n");
	sb.append("[taskId] " + machine + "/" + taskId + "\n");
	sb.append("[RunningPhase] " + runningPhase + "\n");
	sb.append("[is map() running] " + isMapRunning + "\n");
	
	sb.append("------------ InputSplit ------------\n");
	sb.append(input + "\n");
	
	sb.append("------------ SpillBuffer ------------\n");
	sb.append(buffer + "\n");
	
	if(spill.getHasCombine())
	    sb.append("----- Spills without combine -----\n");
	else
	    sb.append("----- Spills with combine --------\n");
	sb.append(spill + "\n");
	
	sb.append("------------ Merge ------------\n");
	sb.append(merge + "\n");
	
	sb.append("------------ Counters ------------\n");
	sb.append(counters + "\n");
	
	sb.append("------------ JvmUsage ------------\n");
	sb.append(jvmUsage + "\n");
	
	sb.append("------------ HeapDumps ------------\n");
	for(String heapdump : heapdumps)
	    sb.append("[HeapDump] " + heapdump + "\n");
	
	return sb.toString();
	
    }
    
    // set task id
    public void setTaskId(String taskId) {
	this.taskId = taskId;
    }

    public void setMachine(String machine) {
	this.machine = machine;
    }
    
    public void setRunningPhase(String runningPhase) {
	this.runningPhase = runningPhase;
    }
    
    public void setIsMapRunning(boolean isMapRunning) {
	this.isMapRunning = isMapRunning;
    }
    
    public Input getInput() {
	return input;
    }

    public void setInput(Input input) {
	this.input = input;
    }

    public MapperBuffer getBuffer() {
	return buffer;
    }

    public void setBuffer(MapperBuffer buffer) {
	this.buffer = buffer;
    }

    public Spill getSpill() {
	return spill;
    }

    public void setSpill(Spill spill) {
	this.spill = spill;
    }

    public Merge getMerge() {
	return merge;
    }

    public void setMerge(Merge merge) {
	this.merge = merge;
    }

    public MapperCounters getCounters() {
	return counters;
    }


    public JvmUsage getJvmUsage() {
	return jvmUsage;
    }

    public void setJvmUsage(JvmUsage jvmUsage) {
	this.jvmUsage = jvmUsage;
    }

    public String getTaskId() {
	return taskId;
    }

    public String getRunningPhase() {
        return runningPhase;
    }

    public boolean isMapRunning() {
        return isMapRunning;
    }

    public void addHeapDump(String heapdump) {
	heapdumps.add(heapdump);
    }
    

}
