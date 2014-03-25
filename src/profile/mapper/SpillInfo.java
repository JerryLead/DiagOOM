package profile.mapper;

import java.io.Serializable;

public class SpillInfo implements Serializable {

    private boolean hasCombine;
    private String reason; // record, buffer, flush

    private long recordsBeforeCombine;
    private long bytesBeforeSpill;
    private long recordsAfterCombine;
    private long rawLength;
    private long compressedLength;

    public SpillInfo(
	    boolean hasCombine, 
	    String reason,
	    long recordsBeforeCombine, 
	    long bytesBeforeSpill,
	    long recordsAfterCombine, 
	    long rawLength, 
	    long compressedLength) {
	
	this.hasCombine = hasCombine;
	this.reason = reason;
	this.recordsBeforeCombine = recordsBeforeCombine;
	this.bytesBeforeSpill = bytesBeforeSpill;
	this.recordsAfterCombine = recordsAfterCombine;
	this.rawLength = rawLength;
	this.compressedLength = compressedLength;
    }

    public boolean isHasCombine() {
        return hasCombine;
    }

    public void setHasCombine(boolean hasCombine) {
        this.hasCombine = hasCombine;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public long getRecordsBeforeCombine() {
        return recordsBeforeCombine;
    }

    public void setRecordsBeforeCombine(long recordsBeforeCombine) {
        this.recordsBeforeCombine = recordsBeforeCombine;
    }

    public long getBytesBeforeSpill() {
        return bytesBeforeSpill;
    }

    public void setBytesBeforeSpill(long bytesBeforeSpill) {
        this.bytesBeforeSpill = bytesBeforeSpill;
    }

    public long getRecordsAfterCombine() {
        return recordsAfterCombine;
    }

    public void setRecordsAfterCombine(long recordsAfterCombine) {
        this.recordsAfterCombine = recordsAfterCombine;
    }

    public long getRawLength() {
        return rawLength;
    }

    public void setRawLength(long rawLength) {
        this.rawLength = rawLength;
    }

    public long getCompressedLength() {
        return compressedLength;
    }

    public void setCompressedLength(long compressedLength) {
        this.compressedLength = compressedLength;
    }
 
}