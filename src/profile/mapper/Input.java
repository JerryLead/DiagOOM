package profile.mapper;

import java.io.Serializable;

public class Input implements Serializable {
    private long splitBytes;

    public long getSplitBytes() {
	return splitBytes;
    }

    public void setSplitBytes(long splitBytes) {
	this.splitBytes = splitBytes;
    }
}
