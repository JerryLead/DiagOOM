package object.model.job;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math3.stat.regression.SimpleRegression;

public class SizeModel {

    private boolean isCounterRecord;
    
    private String userObjName;
    private String counterName;
    private List<Long> inputRecords;
    private List<UserObject> userObjs;
    
    // y = intercept + slope * x
    // bytes = intercept + slope * records
    private float slope;
    private long intercept;

    // the correlation between the number of input records and size of the user object
    private float pearsonCorr;
    
    // Number of inner object / number of input records
    private float innerObjNumPerRecord = -1;

    public SizeModel(String userObjName) {
	this.userObjName = userObjName;
	inputRecords = new ArrayList<Long>();
	userObjs = new ArrayList<UserObject>();
    }
    
    public void add(long record, UserObject obj) {
	inputRecords.add(record);
	userObjs.add(obj);
	
    }
    // mapInRecords | mapFileBytesRead | mCombInRecords | rCombInRecords | redInRecords | redInGroups | redFileBytesRead 

    public void buildModel() {
	if(counterName.equals("mapFileBytesRead") || counterName.equals("redFileBytesRead")) {
	    isCounterRecord = false;
	    computeCorrelation();
	    regression();
	}
	else {
	    isCounterRecord = true;
	    computeInnerObjNumPerRecord();
	    computeCorrelation();
	    regression();    
	}
    }
    
    public void computeInnerObjNumPerRecord() {
	if (inputRecords.size() == 1) {
	    innerObjNumPerRecord = (float) userObjs.get(0).getLength() / inputRecords.get(0);
	}
	else {
	    SimpleRegression regression = new SimpleRegression();

	    for(int i = 0; i < inputRecords.size(); i++) {
		double record = inputRecords.get(i);
		double lengths = userObjs.get(i).getLength();
		
		regression.addData(record, lengths);
	    }
	    innerObjNumPerRecord = (float) regression.getSlope();
	}
    }
    // bytes = intercept + slope * records
    public void regression() {
	if (inputRecords.size() == 1) {
	    intercept = 0;
	    slope = (float) userObjs.get(0).getRetainedHeap()
		    / inputRecords.get(0);
	} 
	else {

	    SimpleRegression regression = new SimpleRegression();

	    for(int i = 0; i < inputRecords.size(); i++) {
		double record = inputRecords.get(i);
		double bytes = userObjs.get(i).getRetainedHeap();
		
		regression.addData(record, bytes);
	    }
	  
	    intercept = (long) regression.getIntercept();
	    slope = (float) regression.getSlope();
	}
    }

    public void computeCorrelation() {
	//new PearsonsCorrelation().correlation(x, y)
	if (inputRecords.size() == 1) {
	    pearsonCorr = 0;
	}
	else {
	    double[] records = new double[inputRecords.size()];
	    double[] bytes = new double[inputRecords.size()];
	    
	    for(int i = 0; i < inputRecords.size(); i++) {
		records[i] = inputRecords.get(i);
		bytes[i] = userObjs.get(i).getRetainedHeap();
	    }
	    
	    PearsonsCorrelation corr = new PearsonsCorrelation();
	    pearsonCorr = (float) corr.correlation(records, bytes);
	}
    }
    
    public void display() {
	
	String objectName = userObjs.get(0).getObjectName();
	
	System.out.println("----------------- " + objectName + " -----------------");
	
	if(innerObjNumPerRecord != -1) {
	    System.out.println("Count(inner obj of" + objectName + ") / Count(" + counterName + ") = " + innerObjNumPerRecord);
	}
	
	System.out.println("[Pearson correlation] " + pearsonCorr);
	
	// bytes = intercept + slope * records
	if(isCounterRecord)
	    System.out.println("Bytes(" + objectName + ") = " + intercept + " + " + slope + " * Count(" + counterName + ")");
	else
	    System.out.println("Bytes(" + objectName + ") = " + intercept + " + " + slope + " * Bytes(" + counterName + ")");
	System.out.println();
    }
    


    public long predict(long inputRecords) {
	long totalObjBytes = (long) (intercept + slope * inputRecords);
	return totalObjBytes;
    }
    
    public String getUserObjName() {
	return userObjName;
    }
    
    public static void main(String[] args) {
	//double[] record = {48205824, 97026048, 143392768, 182734848, 213135360, 252739584};
	//double[] bytes = {308047792, 493580976, 729236672, 933534024, 1060080912, 1221451504};
	double[] len = {350955, 500876, 788182, 943343, 1028168, 1334312};
	
	int[] ids = {9697048, 9688390, 9678749, 9670628, 9665652, 9657063};
	
	double[] record = {2615183, 2764769, 2914355, 3063941, 3213527, 3288320};
	double[] bytes = {11476560, 34693840, 48092344, 59756144, 71866744, 77251840};
	
	System.out.println(new PearsonsCorrelation().correlation(record, bytes));
	
	SimpleRegression regression = new SimpleRegression();

	for (int i = 0; i < record.length; i++) {
	    double r = record[i];
	    double b = bytes[i];

	    regression.addData(r, b);
	}

	long intercept = (long) regression.getIntercept();
	float slope = (float) regression.getSlope();
	if(intercept >= 0)
	    System.out.println("bytes = " + slope + " * record + " + intercept);
	else
	    System.out.println("bytes = " + slope + " * record " + intercept);
    }	


}
