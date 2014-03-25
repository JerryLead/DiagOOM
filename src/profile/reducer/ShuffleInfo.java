package profile.reducer;

import java.io.Serializable;

public class ShuffleInfo implements Serializable {
	//ReduceTask: Shuffling 34482224 bytes (5051724 raw bytes) into RAM from attempt_201210101630_0001_m_000002_0
	
	private String sourceTaskId; //attempt_201209262155_0152_m_000009_0
	private String storeLoc;  //RAM
	private long compressedLen; //5051724
	private long decompressedLen; //34482224
	
	public ShuffleInfo(
		String sourceTaskId,
		String storeLoc, 
		long decompressedLen, 
		long compressedLen) {
		
		this.sourceTaskId = sourceTaskId;
		this.storeLoc = storeLoc;
		this.decompressedLen = decompressedLen;
		this.compressedLen = compressedLen;
	}
}
