package object.model.job;

import java.util.List;

public class UserObjectsPerDump {

    List<UserObject> userObjs;
    
    
    public UserObjectsPerDump(List<UserObject> uObjs) {
	this.userObjs = uObjs;
    }


    public List<UserObject> getUesrObjects() {
	return userObjs;
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
