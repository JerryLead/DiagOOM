package dataflow.model.reducer;

import java.util.List;

import dataflow.model.mapper.Segment;

public class Reducer {
    private List<Segment> segmentsInCopy;
    private List<Segment> segmentsInList;
    private List<Segment> segmentsInMerge;
    
    private MergeCombineFunc mergeCombineFunc;
    private List<OnDiskSeg> onDiskSegs;
    private List<Segment> segmentsInReduceBuf;
    private ReduceFunc mergeFunc;
    
}
