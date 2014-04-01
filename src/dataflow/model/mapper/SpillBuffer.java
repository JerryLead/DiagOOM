package dataflow.model.mapper;

public class SpillBuffer {

    private long kvbuffer;
    private long kvindices;
    private long kvoffsets;
    
    private int io_sort_mb;
    // dataBuffer
    private long spillBytes;
    private long kvbufferBytes;

    // recordBuffer
    private long spillRecords;
    private long kvoffsetsLen;

    // current size of records in the buffer
    private long cachedMapOutputRecrods;
    private long cachedMapOutputBytes;

    // set buffer infos
    public void setDataBuffer(long softBufferLimit, long kvbufferBytes) {
	this.spillBytes = softBufferLimit;
	this.kvbufferBytes = kvbufferBytes;
    }

    public void setRecordBuffer(long softRecordLimit, long kvoffsetsLen) {
	this.spillRecords = softRecordLimit;
	this.kvoffsetsLen = kvoffsetsLen;

    }

    
    public long getSpillBytes() {
        return spillBytes;
    }

    public long getSpillRecords() {
        return spillRecords;
    }

    public long getKvbufferBytes() {
	return kvbufferBytes;
    }


    public long getKvoffsetsLen() {
	return kvoffsetsLen;
    }
    
    public void setIoSortMB(int io_sort_mb) {
	this.io_sort_mb = io_sort_mb;
    }
}
