package dataflow.model.mapper;

public class Segment {

    int partitionId;
    int taskId;
    
    long records;
    long bytes;

    public Segment(int taskId, int partitionId, long records, long bytes) {
	this.taskId = taskId;
	this.partitionId = partitionId;
	this.records = records;
	this.bytes = bytes;
    }

    public long getRecords() {
	return records;
    }

    public long getBytes() {
	return bytes;
    }

    public int getPartitionId() {
        return partitionId;
    }

    public int getTaskId() {
        return taskId;
    }

    
}
