package profile.mapper;

import java.io.Serializable;

public class MapperCounters implements Serializable {
    

    private static final long serialVersionUID = -6361969676093056214L;
    
    private long file_bytes_read = -1; // FILE_BYTES_READ
    private long hdfs_bytes_read = -1; // HDFS_BYTES_READ
    private long file_bytes_written = -1; // FILE_BYTES_WRITTEN

    private long map_input_records = -1; // Map input records
    private long map_output_records = -1; // Map output records
    private long map_output_bytes = -1; // Map output bytes

    private long combine_input_records = -1; // Combine input records
    private long combine_output_records = -1; // Combine output records

    private long physical_memory_bytes = -1; // Physical memory (bytes) snapshot
    private long total_committed_bytes = -1; // Total committed heap usage (bytes)
    

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
	    
	case "Map output bytes":
	    map_output_bytes = value;
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


    public long getHdfs_bytes_read() {
        return hdfs_bytes_read;
    }


    public long getFile_bytes_written() {
        return file_bytes_written;
    }


    public long getMap_input_records() {
        return map_input_records;
    }


    public long getMap_output_records() {
        return map_output_records;
    }


    public long getMap_output_bytes() {
        return map_output_bytes;
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
	
	sb.append("[FILE_BYTES_READ]    " + file_bytes_read + "\n");
	sb.append("[HDFS_BYTES_READ]    " + hdfs_bytes_read + "\n");
	sb.append("[FILE_BYTES_WRITTEN] " + file_bytes_written + "\n\n");
	
	sb.append("[Map input records]  " + map_input_records + "\n");
	sb.append("[Map output records] " + map_output_records + "\n");
	sb.append("[Map output bytes]   " + map_output_bytes + "\n\n");

	sb.append("[Combine input records]  " + combine_input_records + "\n");
	sb.append("[Combine output records] " + combine_output_records + "\n");
	
	return sb.toString();
    }
    
}