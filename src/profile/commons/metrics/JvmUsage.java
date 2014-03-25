package profile.commons.metrics;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class JvmUsage implements Serializable {

    private List<JstatItem> jstatMetricsList = new ArrayList<JstatItem>();
    private GcCapacity gcCap;

 
    public void addJstatMetrics(String[] jstatParams) {
	JstatItem item = new JstatItem(jstatParams);
	jstatMetricsList.add(item);
    }

    public List<JstatItem> getJstatMetricsList() {
	return jstatMetricsList;
    }

    public GcCapacity getGcCapacity() {
	return gcCap;
    }

    
    public List<String> getJstatMetricsStringList() {

	List<String> list = new ArrayList<String>();
	String header = "TimeS S0U S1U EU OU PU S0C S1C EC OC PC NGC YGC FGC YGCT FGCT GCT";
	list.add(header);
	for (JstatItem item : jstatMetricsList) {
	    list.add(item.toString());
	}
	return list;
    }

    public void addGcCapacity(GcCapacity gcCap) {
	this.gcCap = gcCap;
    }

}

class LongToTime {

    private static DateFormat f = new SimpleDateFormat("mm:ss");

    public static String longToMMSS(long time) {
	return f.format(time);
    }

}
