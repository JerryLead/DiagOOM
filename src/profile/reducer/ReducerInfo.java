package profile.reducer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import profile.commons.metrics.JvmUsage;


public class ReducerInfo implements Serializable {

    private static final long serialVersionUID = 6968486195395022301L;
    // basic infos
    private String taskId;
    private String machine;

    private String runningPhase = "init";
    private boolean isInMemMergeRunning = false;
    
    // reduce phase information
    private ShuffleBuffer buffer;
    private Shuffle shuffle;
    private MergeInShuffle mergeInShuffle;
    private Sort sort;

    // other dimensions
    private ReducerCounters counters;
    private JvmUsage jvmUsage;
    private List<String> heapdumps;

    
    public ReducerInfo() {
	 buffer = new ShuffleBuffer();
	 shuffle = new Shuffle();
	 mergeInShuffle = new MergeInShuffle();
	 sort = new Sort();

	 counters = new ReducerCounters();
	 jvmUsage = new JvmUsage();
	 heapdumps = new ArrayList<String>();
    }
    
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
    
    public void addHeapDump(String heapdump) {
   	heapdumps.add(heapdump);
    }
    
    public String getTaskId() {
        return taskId;
    }

    public String getRunningPhase() {
        return runningPhase;
    }

    public boolean isInMemMergeRunning() {
        return isInMemMergeRunning;
    }

    public String toString() {
  	StringBuilder sb = new StringBuilder();

  	sb.append("------------ RunningStatus ------------\n");
  	sb.append("[taskId] " + machine + "/" + taskId + "\n");
  	sb.append("[RunningPhase] " + runningPhase + "\n");
  	sb.append("[isInMemMergeRunning] " + isInMemMergeRunning + "\n\n");
  	
  	sb.append("------------ ShuffleBuffer ------------\n");
  	sb.append(buffer + "\n");
  	
  	sb.append("------------ Shuffle ------------\n");
  	sb.append(shuffle + "\n");
  	

  	sb.append("-------- MergeInShuffle --------\n");
  	sb.append(mergeInShuffle + "\n");
  	
  	sb.append("------------ Sort ------------\n");
  	sb.append(sort + "\n");
  	
  	sb.append("------------ Counters ------------\n");
  	sb.append(counters + "\n");
  	
  	sb.append("------------ JvmUsage ------------\n");
  	sb.append(jvmUsage + "\n");
  	
  	sb.append("------------ HeapDumps ------------\n");
  	for(String heapdump : heapdumps)
  	    sb.append("[HeapDump] " + heapdump + "\n");
  	
  	return sb.toString();
  	
      }
      
}
