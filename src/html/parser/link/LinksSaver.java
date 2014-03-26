package html.parser.link;


import java.util.ArrayList;
import java.util.List;


public class LinksSaver {

	private String jobconf_jsp;
	
	private String map_jobtasks_jsp;
	private List<String> map_tasks_list = new ArrayList<String>(); //refer to taskdetails_jsp
	
	private String reduce_jobtasks_jsp;
	private List<String> reduce_tasks_list = new ArrayList<String>(); //refer to taskdetails_jsp. list is empty if no reducer exists
	
	public LinksSaver (String hostname, String jobId) {	
		
		jobconf_jsp = "http://" + hostname + ":50030/jobconf.jsp?jobid=" + jobId;
		
		map_jobtasks_jsp = "http://" + hostname + ":50030/jobtasks.jsp?jobid=" + jobId
				+ "&type=map&pagenum=1";
		reduce_jobtasks_jsp = "http://" + hostname + ":50030/jobtasks.jsp?jobid=" + jobId
				+ "&type=reduce&pagenum=1";
	}
	
	
	public String getMap_jobtasks_jsp() {
		return map_jobtasks_jsp;
	}

	public List<String> getMap_tasks_list() {
		return map_tasks_list;
	}


	public String getReduce_jobtasks_jsp() {
		return reduce_jobtasks_jsp;
	}
	
	public List<String> getReduce_tasks_list() {
		return reduce_tasks_list;
	}
	
	public String getJobconf_jsp() {
		return jobconf_jsp;
	}

	public void addMapTaskDetailsJsp(String mapTaskDetailsJsp) {		
		map_tasks_list.add(mapTaskDetailsJsp);
	}

	public void addReduceTaskDetailsJsp(String reduceTaskDetailsJsp) {
		reduce_tasks_list.add(reduceTaskDetailsJsp);	
	}

	public void setMap_jobtasks_jsp(String map_jobtasks_jsp) {
		this.map_jobtasks_jsp = map_jobtasks_jsp;
	}



	public void setReduce_jobtasks_jsp(String reduce_jobtasks_jsp) {
		this.reduce_jobtasks_jsp = reduce_jobtasks_jsp;
	}
	
	
}

