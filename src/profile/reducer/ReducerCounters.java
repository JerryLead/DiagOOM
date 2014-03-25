package profile.reducer;

public class ReducerCounters {

    private long file_bytes_read; // FILE_BYTES_READ
    private long hdfs_bytes_written; // HDFS_BYTES_WRITTEN
    private long file_bytes_written; // FILE_BYTES_WRITTEN

    private long reduce_shuffle_bytes; // Reduce shuffle bytes
    private long reduce_input_groups; // Reduce input groups
    private long reduce_input_records; // Reduce input records
    private long reduce_output_records; // Reduce output records

    private long combine_input_records; // Combine input records
    private long combine_output_records; // Combine output records

    private long physical_memory_bytes; // Physical memory (bytes) snapshot
    private long total_committed_bytes; // Total committed heap usage (bytes)

    public void setCounter(String name, long value) {
	switch (name) {
	case "FILE_BYTES_READ":
	    file_bytes_read = value;
	    break;

	case "HDFS_BYTES_WRITTEN":
	    hdfs_bytes_written = value;
	    break;

	case "FILE_BYTES_WRITTEN":
	    file_bytes_written = value;
	    break;

	case "Reduce shuffle bytes":
	    reduce_shuffle_bytes = value;
	    break;

	case "Reduce input groups":
	    reduce_input_groups = value;
	    break;

	case "Reduce input records":
	    reduce_input_records = value;
	    break;

	case "Reduce output records":
	    reduce_output_records = value;
	    break;

	case "Combine input records":
	    combine_input_records = value;
	    break;

	case "Combine output records":
	    combine_output_records = value;
	    break;

	case "Physical memory (bytes) snapshot":
	    physical_memory_bytes = value;
	    break;

	case "Total committed heap usage (bytes)":
	    total_committed_bytes = value;
	    break;
	}
    }
}
