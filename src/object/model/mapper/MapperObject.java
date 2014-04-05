package object.model.mapper;

import java.util.List;

import dataflow.model.mapper.Mapper;
import object.model.job.BufferObject;
import object.model.job.UserObject;

public class MapperObject {

    private Mapper mapper;

    private List<BufferObject> frameworkObjs;

    private List<UserObject> userObjs;

    public MapperObject() {
	BufferObject kvbuffer = new BufferObject("kvbuffer", "", 0, 0);
	BufferObject kvoffsets = new BufferObject("kvoffsets", "", 0, 0);
	BufferObject kvindices = new BufferObject("kvindices", "", 0, 0);
	
	frameworkObjs.add(kvbuffer);
	frameworkObjs.add(kvoffsets);
	frameworkObjs.add(kvindices);
    }

    public Mapper getMapper() {
	return mapper;
    }

    public List<BufferObject> getAllocatedFObjs() {
	return frameworkObjs;
    }

}
