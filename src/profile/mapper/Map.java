package profile.mapper;

import java.io.Serializable;

public class Map implements Serializable {

  
    private static final long serialVersionUID = -7273327510460233916L;
    
    private long mapInputRecords = -1; // equals "Map input records"
    private long mapInputBytes = -1; // equals "HDFS_BYTES_READ"

    private long mapOutputRecords = -1; // equals "Map output records"
    private long mapOutputBytes = -1; // equals "Map output bytes"

    public long getMapInputRecords() {
	return mapInputRecords;
    }

    public void setMapInputRecords(long mapInputRecords) {
	this.mapInputRecords = mapInputRecords;
    }

    public long getMapInputBytes() {
	return mapInputBytes;
    }

    public void setMapInputBytes(long mapInputBytes) {
	this.mapInputBytes = mapInputBytes;
    }

    public long getMapOutputRecords() {
	return mapOutputRecords;
    }

    public void setMapOutputRecords(long mapOutputRecords) {
	this.mapOutputRecords = mapOutputRecords;
    }

    public long getMapOutputBytes() {
	return mapOutputBytes;
    }

    public void setMapOutputBytes(long mapOutputBytes) {
	this.mapOutputBytes = mapOutputBytes;
    }
}
