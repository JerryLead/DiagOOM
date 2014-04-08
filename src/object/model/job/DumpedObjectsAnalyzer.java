package object.model.job;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DumpedObjectsAnalyzer {
    // e.g., /Users/xulijie/Documents/DiagOOMSpace/PigMapJoin
    // mapFileBytesRead-48205824-pid-25687.md
    // mapFileBytesRead-97026048-pid-25687.md
    // mapFileBytesRead-143392768-pid-25687.md
    // mapFileBytesRead-182734848-pid-25687.md
    // mapFileBytesRead-213135360-pid-25687.md
    // mapFileBytesRead-245886976-pid-25687.md
    
    // e.g., 48205824, 97026048, 143392768, 182734848, 213135360, 245886976
    // Default: the last one represents OOM dump
    private List<SortedFile> dumpFiles;
    
    // e.g., mapFileBytesRead
    private String counterName; 

    // e.g, 48205824 ==> object1, object2, object3
   
    private List<UserObjectsPerDump> userObjsPerDumpList;
    
    // only extract the framework objects in the last dump when OOM occurs
    private List<BufferObject> bufferObjs;
    private List<SegmentObj> segments; 
    
    
    
    public DumpedObjectsAnalyzer(String dir) {
	dumpFiles = new ArrayList<SortedFile>();
	userObjsPerDumpList = new ArrayList<UserObjectsPerDump>();
	bufferObjs = new ArrayList<BufferObject>();
	segments = new ArrayList<SegmentObj>();
	
	File directory = new File(dir);
	for(File f : directory.listFiles()) {
	    String name = f.getName();
	    if(name.endsWith(".md")) {
		long counter = Long.parseLong(name.substring(name.indexOf('-') + 1, name.indexOf("-pid")));
		
		dumpFiles.add(new SortedFile(counter, f));
		
		if(counterName == null) {
		    counterName = name.substring(0, name.indexOf('-'));
		}			
	    }
	}
	
	// parseEachDump();
    }

    public void parseEachDump() {
	Collections.sort(dumpFiles);
	
	for(SortedFile f : dumpFiles) {
	    try {
		BufferedReader reader = new BufferedReader(new FileReader(f.getFile()));
		String line;
		List<String> flines = new ArrayList<String>();
		List<String> ulines = new ArrayList<String>();
		List<String> tlines = new ArrayList<String>();
		int category = -1; // 0 => framework objects, 1 => user objects, 2 => threads and code()
		boolean isMap = false;
		
		while((line = reader.readLine()) != null) {
		    if(!line.isEmpty()) {
			if(line.startsWith("## Objects in Map Stage"))
			    isMap = true;
			else if(line.startsWith("## Objects in Reduce Stage"))
			    isMap = false;
			
			
			if(line.startsWith("### Framework Objects")) 
			    category = 0;
			else if(line.startsWith("### User Objects"))
			    category = 1;
			else if(line.startsWith("### User objects => Threads and code()"))
			    category = 2;
			
			if(category == 0) 
			    flines.add(line);
			else if(category == 1)
			    ulines.add(line);

			    
		    }
		    
		    if(category == 2)
			tlines.add(line);
		}
		
		if(f.getFile().getName().contains("OOM"))
		    parseFrameworkObjects(flines, isMap);
		
		//if(!ulines.isEmpty()) {
		Map<String, UserObject> name_object = parseUserObjects(ulines);
		parseThreadsCode(tlines, name_object);
		//}
		
		reader.close();
	    } catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
	
    }

    private void parseFrameworkObjects(List<String> flines, boolean isMap) {
	
	String location = "";
	
	for(int i = 0; i < flines.size(); i++) {
	    if(flines.get(i).startsWith("#### [SegmentsInCopy]")) 
		location = "SegmentsInCopy";
	    
	    else if(flines.get(i).startsWith("#### [SegmentsInList]")) 
		location = "SegmentsInList";
	    
	    else if(flines.get(i).startsWith("#### [SegmentsInMerge")) 
		location = "SegmentsInMerge/Buffer";
	    
	    else if(flines.get(i).startsWith("#### [minSegment]"))
		location = "minSegment";
	    
	    else if(flines.get(i).startsWith("#### [keySegment]"))
		location = "keySegment";
	    
	    else if(flines.get(i).startsWith("#### [comparatorSegment]"))
		location = "comparatorSegment";
	    
	    else {
		if(flines.get(i).startsWith("| FrameworkObj")) {
		    ++i;
		}
		    
		else if(flines.get(i).startsWith("|")) {
		    if(isMap) {
			BufferObject bufferObj = new BufferObject(split(flines.get(i)));
			bufferObjs.add(bufferObj);
		    }
		    else {
			SegmentObj segObj = new SegmentObj(location, split(flines.get(i)));
			segments.add(segObj);
		    }
		}
	    }
	    
	}
	
    }
    
    private Map<String, UserObject> parseUserObjects(List<String> ulines) {
	List<UserObject> uObjs = new ArrayList<UserObject>();
	Map<String, UserObject> name_object = new HashMap<String, UserObject>();
	
	for(int i = 0; i < ulines.size(); i++) {
	    if(ulines.get(i).startsWith("| User object")) 
		++i;
	    else if(ulines.get(i).startsWith("|")) {
		UserObject uObj = new UserObject(split(ulines.get(i)));
		uObjs.add(uObj);
		
		name_object.put(uObj.getObjectId(), uObj);
	    }
	}
	
	userObjsPerDumpList.add(new UserObjectsPerDump(uObjs));
	return name_object;
	
    }
    
    private void parseThreadsCode(List<String> tlines, Map<String, UserObject> name_object) {
	String objId = "";
	StringBuilder sb = null;
	
	for(int i = 0; i < tlines.size(); i++) {
	    String line = tlines.get(i);
	    
	    if(line.startsWith("[") && line.endsWith("=>")) {
		objId = line.substring(line.indexOf('@') + 2, line.lastIndexOf(']'));
		
		if(sb != null)
		    name_object.get(objId).setThreadStack(sb.toString());
		sb = new StringBuilder();
	    }
	    else if (sb != null){
		sb.append(line + "\n");
	    }
	    
	    if(i == tlines.size() - 1 && sb != null) {
		
		name_object.get(objId).setThreadStack(sb.toString());
	    }
	}
	
    }

    public String[] split(String line) {
	String[] items = line.substring(line.indexOf('|') + 1, line.lastIndexOf('|')).split("\\|");
	List<String> list = new ArrayList<String>();
	
	for(String s : items) {
	    list.add(s.trim());
	}
	
	return list.toArray(new String[0]);
	
    }
    
    public void display() {
	System.out.println("## Heap Dump [Counter = " + counterName + "]");
	
	DecimalFormat format = new DecimalFormat(",###");
	
	for(int i = 0; i < dumpFiles.size(); i++) {
	    System.out.println("### Dump " + i + " [" + counterName + " = " + format.format(dumpFiles.get(i).getCounter()) + "]");
	    
	    UserObjectsPerDump uDump = userObjsPerDumpList.get(i);
	    uDump.display();
	}
	
	System.out.println("### Framework objects");
	
	
	if(!bufferObjs.isEmpty()) {
	    System.out.println("#### SpillBuffer\n");
	    System.out.println("| FrameworkObj \t| Inner object \t| shallowHeap \t| retainedHeap \t|");
	    System.out.println("| :----------- | :----------- | -----------: | -----------: |");
	
	    for(BufferObject bufferObj : bufferObjs) {
		System.out.println(bufferObj);
	    } 
	    System.out.println();
	}
	
	System.out.println();
	System.out.println();
	
	
	if(!segments.isEmpty()) {
	    System.out.println("#### Segments\n");
	    
	    System.out.println("| Location \t | FrameworkObj \t| Inner object \t| shallowHeap \t| retainedHeap \t| taskId \t|");
	    System.out.println("| :----------- | :----------- | :----------- | -----------: | -----------: | -----------: |");
		
	    for(SegmentObj sObj : segments) {
		System.out.println(sObj);
	    }
	    System.out.println();
	}
	
	
    }
    
    public static void main(String[] args) {
	// String dir = "/Users/xulijie/Documents/DiagOOMSpace/PigMapJoin";
	String dir = "/Users/xulijie/Documents/DiagOOMSpace/reducerTest";
	
	DumpedObjectsAnalyzer analyzer = new DumpedObjectsAnalyzer(dir);
	analyzer.parseEachDump();
	analyzer.display();
    }

    
}


class SortedFile implements Comparable<SortedFile> {
    private long counter;
    private File file;
    
    public SortedFile(long counter, File file) {
	this.counter = counter;
	this.file = file;
    }

    @Override
    public int compareTo(SortedFile o) {
	if(this.counter < o.counter)
	    return -1;
	else if(this.counter > o.counter)
	    return 1;
	return 0;
    }

    public long getCounter() {
        return counter;
    }

    public File getFile() {
        return file;
    }
}
