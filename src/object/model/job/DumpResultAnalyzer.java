package object.model.job;

import java.util.List;


public class DumpResultAnalyzer {
    // e.g., /Users/xulijie/Documents/DiagOOMSpace/PigMapJoin
    // mapFileBytesRead-48205824-pid-25687.md
    // mapFileBytesRead-97026048-pid-25687.md
    // mapFileBytesRead-143392768-pid-25687.md
    // mapFileBytesRead-182734848-pid-25687.md
    // mapFileBytesRead-213135360-pid-25687.md
    // mapFileBytesRead-245886976-pid-25687.md
  
    
    private String dir; 
    
    // e.g., mapFileBytesRead
    private String counterName; 
    
    // e.g., 48205824, 97026048, 143392768, 182734848, 213135360, 245886976
    private List<Long> inputRecordsPerDumpList;
    // e.g, 48205824 ==> object1, object2, object3
    private List<UserObjectsPerDump> userObjsPerDumpList;
    
    // only extract the framework objects in the last dump when OOM occurs
    private List<BufferObject> bufferObjs;
    private List<SegmentObj> segments; 
}
