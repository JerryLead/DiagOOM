package dataflow.model.mapper;

public class Spill {

   
    private boolean hasCombine;
    private String reason; // record, buffer, flush

    private long recordsBeforeCombine;
    private long bytesBeforeSpill;
    private long recordsAfterCombine;
    private long rawLength;
    private long compressedLength;
    
    public Spill(boolean hasCombine, String reason, long recordsBeforeCombine,
	    long bytesBeforeSpill, long recordsAfterCombine, long rawLength,
	    long compressedLength) {
	this.hasCombine = hasCombine;
	this.reason = reason;
	this.recordsBeforeCombine = recordsBeforeCombine;
	this.bytesBeforeSpill = bytesBeforeSpill;
	this.recordsAfterCombine = recordsAfterCombine;
	this.rawLength = rawLength;
	this.compressedLength = compressedLength;
    }
    
}
