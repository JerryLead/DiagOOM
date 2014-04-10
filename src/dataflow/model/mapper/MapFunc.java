package dataflow.model.mapper;

import java.io.Serializable;
import java.text.DecimalFormat;

public class MapFunc implements Serializable {

    private static final long serialVersionUID = 3972332854774013306L;

    private long cmapInputRecords; // current "Map input records"
    private long cmapInputBytes; // current "HDFS_BYTES_READ"

    private long cmapOutputRecords; // current "Map output records"
    private long cmapOutputBytes; // current "Map output bytes"

    private long tmapInputBytes; // total "HDFS_BYTES_READ"

    
    public long predictTotalInputRecords() {
	return (long) ((double)tmapInputBytes / cmapInputBytes * cmapInputRecords);
    }
    
    public String toString() {
	StringBuilder sb = new StringBuilder();
	DecimalFormat f = new DecimalFormat(",###");
	
	sb.append("[Map input bytes]    " + f.format(cmapInputBytes) + " | " + f.format(tmapInputBytes) + "\n");
	sb.append("[Map input records]  " + f.format(cmapInputRecords) + "\n");
	sb.append("[Map output records] " + f.format(cmapOutputRecords) + "\n");
	sb.append("[Map output bytes]   " + f.format(cmapOutputBytes) + "\n");
	
	return sb.toString();
    }
    
    public long getCmapInputRecords() {
	return cmapInputRecords;
    }

    public void setCmapInputRecords(long cmapInputRecords) {
	this.cmapInputRecords = cmapInputRecords;
    }

    public long getCmapInputBytes() {
	return cmapInputBytes;
    }

    public void setCmapInputBytes(long cmapInputBytes) {
	this.cmapInputBytes = cmapInputBytes;
    }

    public long getCmapOutputRecords() {
	return cmapOutputRecords;
    }

    public void setCmapOutputRecords(long cmapOutputRecords) {
	this.cmapOutputRecords = cmapOutputRecords;
    }

    public long getCmapOutputBytes() {
	return cmapOutputBytes;
    }

    public void setCmapOutputBytes(long cmapOutputBytes) {
	this.cmapOutputBytes = cmapOutputBytes;
    }

    public long getTmapInputBytes() {
	return tmapInputBytes;
    }

    public void setTmapInputBytes(long tmapInputBytes) {
	this.tmapInputBytes = tmapInputBytes;
    }

}
