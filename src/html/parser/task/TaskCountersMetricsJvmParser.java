package html.parser.task;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import profile.commons.metrics.GcCapacity;
import profile.commons.metrics.JstatMetrics;
import profile.commons.metrics.JvmUsage;

import html.util.HtmlFetcher;

public class TaskCountersMetricsJvmParser {
	public static void parseCountersMetricsJvm (String metricsLink, JvmUsage jvmUsage) {
		Document doc = HtmlFetcher.getHtml(metricsLink);
		Elements pElems = doc.getElementsByTag("pre");

		Element jstatElem = pElems.get(3);
		
		
		parseJstatMetrics(jstatElem.text(), jvmUsage);
	}

	
	
	private static void parseJstatMetrics(String wholeJstatText, JvmUsage jvmUsage) {
		String[] lines = wholeJstatText.split("\\n");
		if(lines.length < 5)
			return;
		
		String dateStrSec;
	
		if(!lines[0].trim().startsWith("NGCMN"))
			return;
		
		String[] gccapacity = lines[1].trim().split("\\s+");
		if(gccapacity.length != 16)
			return;

		GcCapacity gcCap = new GcCapacity(gccapacity);
		jvmUsage.addGcCapacity(gcCap);
		
		dateStrSec = lines[2].trim(); //(Date s) 1351667183
		
		JstatMetrics jsm = new JstatMetrics(dateStrSec);
		
		for (int i = 4; i < lines.length; i++) {
			String parameters[] = lines[i].trim().split("\\s+");
			if (parameters.length == 16) // 16 parameters totally
			    jvmUsage.addJstatMetrics(jsm.generateArrayList(parameters));
			
		}	
	}
	
	public static String getGcCapacity(String metricsLink) {
		Document doc = HtmlFetcher.getHtml(metricsLink);
		Elements pElems = doc.getElementsByTag("pre");
		
		Element jstatElem = pElems.get(3);
		
		String[] lines = jstatElem.text().split("\\n");
		if(lines.length < 2)
			return null;
		if(!lines[0].trim().startsWith("NGCMN"))
			return null;
		return lines[1].trim();
	}

}


