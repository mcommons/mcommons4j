import java.io.InputStream;
import java.io.StringWriter;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class Driver {
  public static void main(String[] args) throws Exception
  {
	  User me = new User("asdf@asdf.com", "asdf");
	  mCommons site = new mCommons("http://hrc.localhost.com:3000/api/", me);    
    
//	  Date date1 = new Date();
//	  
//	  
//    Campaign[] campaigns = {};
//    
//
//    campaigns = site.getCampaigns();
//    
////    for(int i=0; i < campaigns.length; i++){
////      System.out.println(campaigns[i].toString());
////    }
////    
//    Group[] groups;
//    groups = site.getGroups();
////    
////    for (int i=0; i < groups.length; i++){
////      System.out.println(groups[i].toString());
////    }
////    
//    Profile[] profiles;
//    profiles = site.getGroupMembers(groups[groups.length-1]);
////    for(int i=0; i < profiles.length; i++){
////      System.out.println(profiles[i].toString());
////    }
//    
//    CampaignSubscriber[] subz;
//    subz = site.getCampaignSubscribers(campaigns[2]);
////    for(int i=0; i<100; i++){
////      System.out.println(subz[i].toString());
////    }
//
//	   Date date = new Date();
	  
       System.out.println(site.sendSmsMessage("471", "9469598304", "hi"));
//       System.out.println(site.sendSmsMessage("471", profiles[0], "hi"));
//       System.out.println(site.sendSmsMessage("471", subz[0], "hi"));
//       
//       System.out.println(site.sendSmsMessage(campaigns[0], "9732238304", "hi"));
//       System.out.println(site.sendSmsMessage(campaigns[0], profiles[0], "hi"));
//       System.out.println(site.sendSmsMessage(campaigns[0], subz[0], "hi"));
//       
//       System.out.println(site.scheduleBroadcast("471", "bye"));
//       System.out.println(site.scheduleBroadcast("471", "bye", date));
//       System.out.println(site.scheduleBroadcast(campaigns[3], "bye"));
//       System.out.println(site.scheduleBroadcast(campaigns[4], "bye", date1));
	  
	  
  }
}