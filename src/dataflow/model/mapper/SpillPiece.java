package dataflow.model.mapper;

import profile.mapper.SpillInfo;

public class SpillPiece {

   
    private boolean hasCombine;
    private String reason; // record, buffer, flush

    private long recordsBefore;
    private long bytesBefore;
    private long recordsAfter;
    private long bytesAfter;

    
    public SpillPiece(boolean hasCombine, String reason, long recordsBefore,
	    long bytesBefore, long recordsAfter, long bytesAfter
	    ) {
	this.hasCombine = hasCombine;
	this.reason = reason;
	this.recordsBefore = recordsBefore;
	this.bytesBefore = bytesBefore;
	this.recordsAfter = recordsAfter;
	this.bytesAfter = bytesAfter;

    }

    public SpillPiece(SpillInfo s) {
	this(s.isHasCombine(),
		s.getReason(),
		s.getRecordsBeforeCombine(),
		s.getBytesBeforeSpill(),
		s.getRecordsAfterCombine(),
		s.getRawLength()
	);
    }

    public boolean isHasCombine() {
        return hasCombine;
    }

    public String getReason() {
        return reason;
    }

    public long getRecordsBefore() {
        return recordsBefore;
    }

    public long getBytesBefore() {
        return bytesBefore;
    }

    public long getRecordsAfter() {
        return recordsAfter;
    }

    public long getBytesAfter() {
        return bytesAfter;
    }
    
    
}
