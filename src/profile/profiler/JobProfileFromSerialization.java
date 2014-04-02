package profile.profiler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import profile.job.JobProfile;

public class JobProfileFromSerialization {

    public static void main(String[] args) {
	String jobId = "job_201403261726_0001";

	String serializeDir = "/Users/xulijie/Documents/DiagOOMSpace/PigMapJoin/";

	JobProfile job = deserialization(serializeDir, jobId);

	System.out.println("## Mapper");
	System.out.println(job.getMapperInfoList().get(0));
	System.out.println("\n## Reducer");
	System.out.println(job.getReducerInfoList().get(0));

    }

    public static JobProfile deserialization(String serializeDir, String jobId) {
	File jobFile = new File(serializeDir + File.separator + jobId + ".out");
	JobProfile job = null;
	try {
	    FileInputStream fis = new FileInputStream(jobFile);
	    ObjectInputStream ois = new ObjectInputStream(fis);
	    job = (JobProfile) ois.readObject();

	    ois.close();
	} catch (FileNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (ClassNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	return job;
    }
}
