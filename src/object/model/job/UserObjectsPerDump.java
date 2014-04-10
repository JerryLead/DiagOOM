package object.model.job;

import java.util.List;

public class UserObjectsPerDump {

    String counterName;
    long counter;
    
    List<UserObject> userObjs;
    
    
    public UserObjectsPerDump(String counterName, long counter, List<UserObject> uObjs) {
	this.counterName = counterName;
	this.counter = counter;
	this.userObjs = uObjs;
    }


    public List<UserObject> getUesrObjects() {
	return userObjs;
    }
    
    public String getCounterName() {
	return counterName;
    }
    
    public long getCounter() {
	return counter;
    }
    
    public void setCounter(long counter) {
	this.counter = counter;
    }
    
    public void display() {
	if(userObjs == null || userObjs.isEmpty())
	    return;
	//System.out.println("### User Objects\n");
	System.out.println("| User object | shallow heap | retained heap | length | inner object | inner size | threads | code() |");
	System.out.println("|:------------| ------------:| -------------:| ------:|:------------ | ----------:| :------ | :------|");
	
	for(UserObject userObj : userObjs) {
	    userObj.display();
	}
    }
    
}
