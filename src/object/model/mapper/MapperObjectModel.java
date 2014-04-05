package object.model.mapper;

import java.util.ArrayList;
import java.util.List;

import object.model.job.BufferObject;
import object.model.job.UserObject;
import profile.commons.configuration.Configuration;

public class MapperObjectModel {
    
    private List<BufferObject> allocatedFObjs;
    private List<BufferObject> allFObjs;

    private List<UserObject> allocatedUObjs;

    public MapperObjectModel(MapperObject mapperObj) {
	
	allocatedFObjs = mapperObj.getAllocatedFObjs();
	allFObjs = new ArrayList<BufferObject>();
	allocatedUObjs = new ArrayList<UserObject>();

	if (mapperObj.getMapper().hasReducer() == true) {
	    
	    Configuration conf = mapperObj.getMapper().getConf();
	    // float spillper = conf.getIo_sort_spill_percent();
	    float recper = conf.getIo_sort_record_percent();
	    int sortmb = conf.getIo_sort_mb();

	    // buffers and 
	    long maxMemUsage = sortmb << 20;
	    long recordCapacity = (long) (maxMemUsage * recper);
	    recordCapacity -= recordCapacity % 16;
	    long kvbufferBytes = maxMemUsage - recordCapacity;

	    recordCapacity /= 16;
	    // each kvoffsets/kvindices is a integer, kvindices has three
	    // elements while kvoffsets has only one
	    long kvoffsetsBytes = recordCapacity * 4;
	    long kvindicesBytes = recordCapacity * 3 * 4;

	    BufferObject kvbuffer = new BufferObject("kvbuffer", "", 0,
		    kvbufferBytes);
	    BufferObject kvoffsets = new BufferObject("kvoffsets", "", 0,
		    kvoffsetsBytes);
	    BufferObject kvindices = new BufferObject("kvindices", "", 0,
		    kvindicesBytes);
	    
	    allFObjs.add(kvbuffer);
	    allFObjs.add(kvoffsets);
	    allFObjs.add(kvindices);
	    
	}
	
    }

    
}
