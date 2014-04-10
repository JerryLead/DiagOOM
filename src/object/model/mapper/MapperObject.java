package object.model.mapper;

import java.util.List;

import dataflow.model.mapper.Mapper;
import object.model.job.BufferObject;
import object.model.job.UserObjectsPerDump;

public class MapperObject {

    private Mapper mapper;

    private List<BufferObject> bufferObjs;

    private List<UserObjectsPerDump> userObjsList;
    

    public MapperObject(Mapper mapper, List<BufferObject> bufferObjs, 
	    List<UserObjectsPerDump> userObjsPerDump) {
	this.mapper = mapper;
	this.bufferObjs = bufferObjs;
	this.userObjsList = userObjsPerDump; 
    }

    public Mapper getMapper() {
	return mapper;
    }

    public List<BufferObject> getBufferObjs() {
        return bufferObjs;
    }

    public List<UserObjectsPerDump> getUserObjs() {
        return userObjsList;
    }


}
