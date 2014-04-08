package dataflow.model.job;

import java.util.List;

import dataflow.model.mapper.Mapper;
import dataflow.model.reducer.Reducer;

public class Job {
    
    private List<Mapper> mappers;
    private List<Reducer> reducers;

    public void setMappsers(List<Mapper> mappers) {
	this.mappers = mappers;
    }

    public void setReducers(List<Reducer> reducers) {
	this.reducers = reducers;
    }
    
    public List<Mapper> getMappers() {
	return mappers;
    }
    
    public List<Reducer> getReducers() {
	return reducers;
    }

}
