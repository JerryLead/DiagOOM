package profile.reducer;

import java.io.Serializable;

public class ReducerCounters implements Serializable {

    private static final long serialVersionUID = 6848051201439325174L;

    private long file_bytes_read = -1; // FILE_BYTES_READ
    private long hdfs_bytes_written = -1; // HDFS_BYTES_WRITTEN
    private long file_bytes_written = -1; // FILE_BYTES_WRITTEN

    private long reduce_shuffle_bytes = -1; // Reduce shuffle bytes
    private long reduce_input_groups = -1; // Reduce input groups
    private long reduce_input_records = -1; // Reduce input records
    private long reduce_output_records = -1; // Reduce output records

    private long combine_input_records = -1; // Combine input records
    private long combine_output_records = -1; // Combine output records

    private long physical_memory_bytes = -1; // Physical memory (bytes) snapshot
    private long total_committed_bytes = -1; // Total committed heap usage (bytes)

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

    public static long getSerialversionuid() {
	return serialVersionUID;
    }

    public long getFile_bytes_read() {
	return file_bytes_read;
    }

    public long getHdfs_bytes_written() {
	return hdfs_bytes_written;
    }

    public long getFile_bytes_written() {
	return file_bytes_written;
    }

    public long getReduce_shuffle_bytes() {
	return reduce_shuffle_bytes;
    }

    public long getReduce_input_groups() {
	return reduce_input_groups;
    }

    public long getReduce_input_records() {
	return reduce_input_records;
    }

    public long getReduce_output_records() {
	return reduce_output_records;
    }

    public long getCombine_input_records() {
	return combine_input_records;
    }

    public long getCombine_output_records() {
	return combine_output_records;
    }

    public long getPhysical_memory_bytes() {
	return physical_memory_bytes;
    }

    public long getTotal_committed_bytes() {
	return total_committed_bytes;
    }

    
    public String toString() {
	StringBuilder sb = new StringBuilder();

	sb.append("[FILE_BYTES_READ]        " + file_bytes_read + "\n");
	sb.append("[HDFS_BYTES_WRITTEN]     " + hdfs_bytes_written + "\n");
	sb.append("[FILE_BYTES_WRITTEN]     " + file_bytes_written + "\n\n");

	sb.append("[Reduce shuffle bytes]   " + reduce_shuffle_bytes + "\n");
	sb.append("[Reduce input groups]    " + reduce_input_groups + "\n");
	sb.append("[Reduce input records]   " + reduce_input_records + "\n\n");
	
	sb.append("[Reduce output records]  " + reduce_output_records + "\n");
	sb.append("[Combine input records]  " + combine_input_records + "\n");
	sb.append("[Combine output records] " + combine_output_records + "\n");

	return sb.toString();
    }
}
