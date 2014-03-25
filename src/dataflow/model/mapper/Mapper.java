package dataflow.model.mapper;

import java.util.List;

public class Mapper {

    // e.g., attempt_201403211644_0002_m_000013_0
    private String taskId;

    private InputSplit split;
    private MapFunc mapFunc;
    private SpillBuffer spillBuffer;
    private MemCombineFunc memCombineFunc;
    private List<Spill> spills;
    private DiskCombineFunc diskCombineFunc;
    private List<Segment> mapOutputs;
    private List<Merge> merges;

    private long file_bytes_read;
    private long file_bytes_written;
    private long physical_memory_bytes;
    private long total_committed_bytes;

    public String getTaskId() {
	return taskId;
    }

    public void setTaskId(String taskId) {
	this.taskId = taskId;
    }

    public InputSplit getSplit() {
	return split;
    }

    public void setSplit(InputSplit split) {
	this.split = split;
    }

    public MapFunc getMapFunc() {
	return mapFunc;
    }

    public void setMapFunc(MapFunc mapFunc) {
	this.mapFunc = mapFunc;
    }

    public SpillBuffer getSpillBuffer() {
	return spillBuffer;
    }

    public void setSpillBuffer(SpillBuffer spillBuffer) {
	this.spillBuffer = spillBuffer;
    }

    public MemCombineFunc getMemCombineFunc() {
	return memCombineFunc;
    }

    public void setMemCombineFunc(MemCombineFunc memCombineFunc) {
	this.memCombineFunc = memCombineFunc;
    }

    public List<Spill> getSpills() {
	return spills;
    }

    public void setSpills(List<Spill> spills) {
	this.spills = spills;
    }

    public DiskCombineFunc getDiskCombineFunc() {
	return diskCombineFunc;
    }

    public void setDiskCombineFunc(DiskCombineFunc diskCombineFunc) {
	this.diskCombineFunc = diskCombineFunc;
    }

    public List<Segment> getMapOutputs() {
	return mapOutputs;
    }

    public void setMapOutputs(List<Segment> mapOutputs) {
	this.mapOutputs = mapOutputs;
    }
    
    public List<Merge> getMerges() {
	return merges;
    }

    public void setCounter(String name, long value) {
	switch (name) {
	case "FILE_BYTES_READ":
	    file_bytes_read = value;
	    break;

	case "HDFS_BYTES_READ":
	    mapFunc.setMapInputBytes(value);
	    break;

	case "FILE_BYTES_WRITTEN":
	    file_bytes_written = value;
	    break;

	case "Map input records":
	    mapFunc.setMapInputRecords(value);
	    break;

	case "Map output records":
	    mapFunc.setMapOutputRecords(value);
	    break;

	case "Physical memory (bytes) snapshot":
	    physical_memory_bytes = value;
	    break;

	case "Total committed heap usage (bytes)":
	    total_committed_bytes = value;
	    break;
	}

    }

}
