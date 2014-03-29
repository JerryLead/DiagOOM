package profile.mapper;

import java.io.Serializable;
import java.text.DecimalFormat;

public class Input implements Serializable {

    private static final long serialVersionUID = 4664291315451417380L;
    
    private long splitBytes = -1;

    public long getSplitBytes() {
	return splitBytes;
    }

    public void setSplitBytes(long splitBytes) {
	this.splitBytes = splitBytes;
    }
    
    public String toString() {
	DecimalFormat format = new DecimalFormat(",###");
	return "[InputSplit] " + format.format(splitBytes) + " B\n";
    }
}
