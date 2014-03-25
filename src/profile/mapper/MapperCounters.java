package profile.mapper;

import java.io.Serializable;

public class MapperCounters implements Serializable {
    private long file_bytes_read; // FILE_BYTES_READ
    private long hdfs_bytes_read; // HDFS_BYTES_READ
    private long file_bytes_written; // FILE_BYTES_WRITTEN

    private long map_input_records; // Map input records
    private long map_output_records; // Map output records
    private long map_output_bytes; // Map output bytes

    private long combine_input_records; // Combine input records
    private long combine_output_records; // Combine output records

    private long physical_memory_bytes; // Physical memory (bytes) snapshot
    private long total_committed_bytes; // Total committed heap usage (bytes)
    

    public void setCounter(String name, long value) {
	switch (name) {
	case "FILE_BYTES_READ":
	    file_bytes_read = value;
	    break;

	case "HDFS_BYTES_READ":
	    hdfs_bytes_read = value;
	    break;

	case "FILE_BYTES_WRITTEN":
	    file_bytes_written = value;
	    break;

	case "Map input records":
	    map_input_records = value;
	    break;

	case "Map output records":
	    map_output_records = value;
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