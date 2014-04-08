package html.parser.task;

import java.util.Scanner;

import html.util.HtmlFetcher;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import profile.commons.metrics.JvmUsage;

public class TaskGCPrintParser {

    public static void parseGCPrint(String logLink, JvmUsage jvmUsage) {
   	Document mapLogs = HtmlFetcher.getHtml(logLink);

   	Element syslogPre = mapLogs.getElementsByTag("pre").first();
   	String syslog[] = syslogPre.text().split("\\n");

   	if(syslog.length == 0)
   	    return;
   	if(!syslog[0].contains("[GC") && !syslog[0].contains("Heap") && !syslog[0].contains("[Full GC")) 
   	    return;
   	
   	int i;

   	for (i = 0; i < syslog.length; i++) {
   	    /*
   	    if(syslog[i].contains("[GC")) {
   		long[] values = extractLongNumber(syslog[i].substring(syslog[i].indexOf('[')), 6);
   		
   		float newGenBefore = ((float) values[0]) / 1024;
   		float newGenCurrent = ((float) values[1]) / 1024;
   		float newGenCommitted = ((float) values[2]) / 1024;
   		
   		float heapBefore = ((float) values[3]) / 1024;
   		float heapCurrent = ((float) values[4]) / 1024;
   		float heapCommitted = ((float) values[5]) / 1024;
   		
   		jvmUsage.addYoungGCItem(newGenBefore, newGenCurrent, newGenCommitted, 
   			heapBefore, heapCurrent, heapCommitted);
   	    }
   	    */
   	    
   	    if(syslog[i].contains("[Full GC")) {
   		long[] values = extractLongNumber(syslog[i].substring(syslog[i].indexOf('[')), 9);
   		
   		float newGenBefore = ((float) values[0]) / 1024;
   		float newGenCurrent = ((float) values[1]) / 1024;
   		float newGenCommitted = ((float) values[2]) / 1024;
   		
   		float oldGenBefore = ((float) values[3]) / 1024;
   		float oldGenCurrent = ((float) values[4]) / 1024;
   		float oldGenCommitted = ((float) values[5]) / 1024;
   		
   		float heapBefore = ((float) values[6]) / 1024;
   		float heapCurrent = ((float) values[7]) / 1024;
   		float heapCommitted = ((float) values[8]) / 1024;
   		
   		jvmUsage.setFullGCItem(newGenBefore, newGenCurrent, newGenCommitted, 
   			oldGenBefore, oldGenCurrent, oldGenCommitted, 
   			heapBefore, heapCurrent, heapCommitted);
   	    }
   	    
   	    else if (syslog[i].equals("Heap")) {
   		// PSYoungGen
   		i++;
   		long[] values = extractLongNumber(syslog[i], 2);
   		float total = ((float) values[0]) / 1024;
   		float used = ((float) values[1]) / 1024;
   		
   		long[] bound = extractHeapBound(syslog[i]);
   		
   		jvmUsage.addHeapUsage("newGen", total, used, bound);
   		
   		
   		// eden
   		i++;
   		values = extractLongNumber(syslog[i], 2);
   		total = ((float) values[0]) / 1024;
   		used = total * values[1] / 100;
   		bound = extractHeapBound(syslog[i]);
   		
   		
   		jvmUsage.addHeapUsage("eden", total, used, bound);
   		
   		// from
   		i++;
   		values = extractLongNumber(syslog[i], 2);
   		total = ((float) values[0]) / 1024;
   		used = total * values[1] / 100;
   		bound = extractHeapBound(syslog[i]);
   		
   		jvmUsage.addHeapUsage("from", total, used, bound);
   		
   		// to
   		i++;
   		values = extractLongNumber(syslog[i], 2);
   		total = ((float) values[0]) / 1024;
   		used = total * values[1] / 100;
   		bound = extractHeapBound(syslog[i]);
   		
   		jvmUsage.addHeapUsage("to", total, used, bound);
   		
   		
   		// PSOldGen
   		i++;
   		values = extractLongNumber(syslog[i], 2);
   		total = ((float) values[0]) / 1024;
   		used = ((float) values[1]) / 1024;
   		
   		bound = extractHeapBound(syslog[i]);
   		
   		jvmUsage.addHeapUsage("oldGen", total, used, bound);
   		
   		jvmUsage.computeOOMUsage();
   		break;
   	    }
   		
   	}
   	
   	
    }
    
    public static long[] extractLongNumber(String line, int n) {
	long[] values = new long[n];
	Scanner scanner = new Scanner(line).useDelimiter("[^0-9]+");
	int i = 0;

	while (scanner.hasNextLong() && i < n)
	    values[i++] = scanner.nextLong();
	return values;
    }

    public static long[] extractHeapBound(String line) {
	
	long[] bound = new long[3];
	int loc = line.indexOf('[') + 1;

	
	bound[0] = Long.parseLong(line.substring(loc + 2, line.indexOf(',', loc)), 16);
		
	loc = line.indexOf(',', loc) + 2;
	bound[1] = Long.parseLong(line.substring(loc + 2, line.indexOf(',', loc)), 16);
	
		
	loc = line.lastIndexOf(',') + 2;
	bound[2] = Long.parseLong(line.substring(loc + 2, line.lastIndexOf(')')), 16);
	
	
	return bound;
    }

}
