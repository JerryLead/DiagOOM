package profile.mapper;

import java.io.Serializable;

public class SpillInfo implements Serializable {

    private static final long serialVersionUID = 5204546233874035223L;
    
    private boolean hasCombine;
    private String reason; // record, buffer, flush

    private long recordsBeforeCombine;
    private long bytesBeforeSpill;
    private long recordsAfterCombine = -1;
    private long rawLength = -1;
    private long compressedLength = -1;

    

    public SpillInfo(String reason, long recordsBefore, long bytesBefore) {
	this.reason = reason;
	this.recordsBeforeCombine = recordsBefore;
	this.bytesBeforeSpill = bytesBefore;
    }

    public void setAfterSpillInfo(boolean hasCombine, long recordAfterCombine,
	    long rawLength, long compressedLength) {
	this.hasCombine = hasCombine;
	this.recordsAfterCombine = recordAfterCombine;
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