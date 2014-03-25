package profile.mapper;

import profile.commons.metrics.JvmUsage;

public class MapperInfo {

    private String taskId;
    private boolean isMapRunning;
    private String runningPhase;

    // map phase information
    private Input input = new Input();
    private Map map = new Map();

    private MapperBuffer buffer = new MapperBuffer();
    private Spill spill = new Spill();
    private Merge merge = new Merge();

    private MapperCounters counters = new MapperCounters();

    private JvmUsage jvmUsage = new JvmUsage();

    
    // set task id
    public void setTaskId(String taskId) {
	this.taskId = taskId;
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

    public Map getMap() {
	return map;
    }

    public void setMap(Map map) {
	this.map = map;
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

}
