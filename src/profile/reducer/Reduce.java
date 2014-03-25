package profile.reducer;

import java.io.Serializable;

public class Reduce implements Serializable {
	private long reduce_input_groups; // equals "Reduce input groups" normally
	private long inputKeyValuePairsNum; // equals "Reduce input records"
	private long inputBytes; // equals rawLength after merge in Sort
	private long computedReduceInputRecords; //computed from MixSortMerge and FinalSortMerge

	private long outputKeyValuePairsNum; // equals "Reduce output records"
	private long outputBytes; // depends on "HDFS_BYTES_WRITTEN" and dfs.replication


}
