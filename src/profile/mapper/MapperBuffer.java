package profile.mapper;

import java.io.Serializable;

public class MapperBuffer implements Serializable {
    
    private static final long serialVersionUID = 9147734701929030191L;
    
    // dataBuffer
    private long softBufferLimit;
    private long kvbufferBytes;

    // recordBuffer
    private long softRecordLimit;
    private long kvoffsetsLen;

    // set buffer infos
    public void setDataBuffer(long softBufferLimit, long kvbufferBytes) {
	this.softBufferLimit = softBufferLimit;
	this.kvbufferBytes = kvbufferBytes;
    }

    public void setRecordBuffer(long softRecordLimit, long kvoffsetsLen) {
	this.softRecordLimit = softRecordLimit;
	this.kvoffsetsLen = kvoffsetsLen;

    }

    public long getSoftBufferLimit() {
	return softBufferLimit;
    }

    public long getKvbufferBytes() {
	return kvbufferBytes;
    }

    public long getSoftRecordLimit() {
	return softRecordLimit;
    }

    public long getKvoffsetsLen() {
	return kvoffsetsLen;
    }

    public String toString() {
	
	return "kvbuffer = " + softBufferLimit + " | " + kvbufferBytes + ", "
		+ "kvoffsets = " + softRecordLimit + " | " + kvoffsetsLen + "\n";
	
    }
}
