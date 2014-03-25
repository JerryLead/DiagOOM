package profile.reducer;

import java.io.Serializable;

import profile.commons.metrics.JvmUsage;


public class ReducerInfo implements Serializable {

    // basic infos
    private String taskId;
    private String machine;

    private String runningPhase;
    private boolean isInMemMergeRunning = false;
    
    // reduce phase information
    private ShuffleBuffer buffer = new ShuffleBuffer();
    private Shuffle shuffle = new Shuffle();
    private MergeInShuffle mergeInShuffle = new MergeInShuffle();
    private Sort sort = new Sort();
    private Reduce reduce = new Reduce();

    // other dimensions
    private ReducerCounters counters = new ReducerCounters();
    private JvmUsage jvmUsage = new JvmUsage();

    public ShuffleBuffer getShuffleBuffer() {
	return buffer;
    }
    
    public Shuffle getShuffle() {
	return shuffle;
    }
   
    public MergeInShuffle getMergeInShuffle() {
	return mergeInShuffle;
    }
    
    public Sort getSort() {
	return sort;
    }

    public void setRunningPhase(String runningPhase) {
	this.runningPhase = runningPhase;
    }
    
    public void setInMemMergeRunning(boolean isInMemMergeRunning) {
	
	this.isInMemMergeRunning = isInMemMergeRunning;
    }

    public void setTaskId(String taskId) {
	this.taskId = taskId;
	
    }

    public void setMachine(String machine) {
	this.machine = machine;
    }
    
    public JvmUsage getJvmUsage() {
	return jvmUsage;
    }
    
    public ReducerCounters getReducerCounters() {
	return counters;
    }
}
