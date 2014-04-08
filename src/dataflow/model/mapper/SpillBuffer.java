package dataflow.model.mapper;

import java.text.DecimalFormat;

import profile.commons.configuration.Configuration;

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

    
    public SpillBuffer(Configuration conf) {
	// float spillper = conf.getIo_sort_spill_percent();
	float recper = conf.getIo_sort_record_percent();
	this.io_sort_mb = conf.getIo_sort_mb();

	// buffers and accounting
	long maxMemUsage = io_sort_mb << 20;
	long recordCapacity = (long) (maxMemUsage * recper);
	recordCapacity -= recordCapacity % 16;
	this.kvbuffer = maxMemUsage - recordCapacity;

	recordCapacity /= 4;
	//each kvoffsets/kvindices is a integer, kvindices has three elements while kvoffsets has only one
	this.kvoffsets = recordCapacity; 
	this.kvindices = recordCapacity * 3;

    }
    
    public String toString() {
	DecimalFormat f = new DecimalFormat(",###");
	StringBuilder sb = new StringBuilder();
	
	sb.append("[io.sort.mb] = " + io_sort_mb + "\n");
	sb.append("[kvbuffer]   = " + f.format(kvbuffer) + "\n");
	sb.append("[kvindices]  = " + f.format(kvindices) + "\n");
	sb.append("[kvoffsets]  = " + f.format(kvoffsets) + "\n\n");
	sb.append("[spillBytes   | kvbufferBytes] = " + f.format(spillBytes) 
		+ " | " + f.format(kvbufferBytes) + "\n");
	sb.append("[spillRecords | kvoffsetsLen]  = " + f.format(spillRecords) 
		+ " | " + f.format(kvoffsetsLen) + "\n");
	
	return sb.toString();
    }
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
