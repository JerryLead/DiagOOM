package object.model.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * If OOM occurs while user code is running, we will let users rerun the job with heapdump related 
 * configurations. The second job will generate many heap dumps before the OOM occurs.
 * e.g., if OOM occurs when map() reads the 1,000th record,
 * we will specify "heapdump.map.input.records=[100, 200, 300, 400, 500, 600, 700, 800, 900, 999]"
 * which means the job will generate 10 heap dumps when the map_input_record are [100, 200, ..., 999].
 * Also, OOM will generate another heap dump which map_input_record is 1000.
 * 
 * By analyzing the heap dump of 999 and 1000, we can determine whether the OOM pattern is 
 * "large single intermediate computing result" or "large cumulative computing result".
 * If the second pattern is true, we can use pearson correlation and regression to identify the relationship
 * between input records and the size of user objects.
 */

public class UserObjectModel {
    // inputRecordsPerDumpList ==> userObjsPerDumpList (object1, object2, ..., objectN)
    //
    // record1 ==> object1 (innerObjNum11, objectBytes11)
    //             object2 (innerObjNum12, objectBytes12)
    //             object3 (innerObjNum13, objectBytes13)
    // record2 ==> object1 (innerObjNum21, objectBytes21)
    //             object2 (innerObjNum22, objectBytes22)
    //             object3 (innerObjNum23, objectBytes23)
    // record3 ==> object2 (innerObjNum32, objectBytes32) [OOM]
    //             object3 (innerObjNum33, objectBytes33) [OOM]
    //             object4 (innerObjNum34, objectBytes34) [OOM]
    
    // objectModelList ==> (recordList, objectList)
    //
    // object1 ==> <record1, (innerObjNum11, objectBytes11)>
    //         ==> <record2, (innerObjNum21, objectBytes21)>
    // object2 ==> <record1, (innerObjNum12, objectBytes12)> [OOM]
    //         ==> <record2, (innerObjNum22, objectBytes22)>
    //         ==> <record3, (innerObjNum32, objectBytes32)>
    // object3 ==> <record1, (innerObjNum13, objectBytes13)> [OOM]
    //         ==> <record2, (innerObjNum23, objectBytes23)>
    //         ==> <record3, (innerObjNum33, objectBytes33)>
    // object4 ==> <record3, (innerObjNum34, objectBytes34)> [OOM]
    
    // result of the linear regression 
    private List<Long> inputRecordsPerDumpList;
    private List<UserObjectsPerDump> userObjsPerDumpList;
    
    private List<SizeModel> objectModelList;
    
    private Map<String, Integer> objectName_index = new HashMap<String, Integer>();
    
    public void invertObject() {
	for(int i = 0; i < inputRecordsPerDumpList.size(); i++) {
	    long record = inputRecordsPerDumpList.get(i);
	    UserObjectsPerDump objects = userObjsPerDumpList.get(i);
	    
	    for(UserObject obj : objects.getUesrObjects()) {
		
		if(!objectName_index.containsKey(obj.getObjectName())) {
		    SizeModel model = new SizeModel();
		    model.add(record, obj);
		    objectModelList.add(model);
		    objectName_index.put(obj.getObjectName(), objectModelList.size() - 1);
		}
		else {
		    int index = objectName_index.get(obj.getObjectName());
		    SizeModel model = objectModelList.get(index);
		    model.add(record, obj);
		}
	    }
	}
    }
    
}


