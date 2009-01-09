import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class mCommons {
	String rootUrl;
	
	mCommons(String url) {    // "http://hrc.mcommons.com/api/"
		rootUrl = url;
	}
	
	public CampaignSubscriber[] getCampaignSubscribers(User user, Campaign campaign)
		throws Exception{
		return getCampaignSubscribers(user, campaign.getId());
		}
	
	public CampaignSubscriber[] getCampaignSubscribers(User user, String campaignId) 
		throws Exception {
        NodeList subList = 
        	getNodeListFromPage(rootUrl+"campaign_subscribers?campaign_id="+campaignId, user, "sub");
        CampaignSubscriber[] allSubs= new CampaignSubscriber[subList.getLength()];
        
        for(int i=0; i < subList.getLength(); i++){
        	allSubs[i] = new CampaignSubscriber(subList.item(i));        	
        }        
        return allSubs;
	}
	
	public Profile[] getGroupMembers(User user, Group group) throws Exception {
		return getGroupMembers(user, group.getId());
	}
	
	public Profile[] getGroupMembers(User user, String groupId) throws Exception{
        NodeList profileList = 
        	getNodeListFromPage(rootUrl+"group_members?group_id="+groupId, user, "profile");
        Profile[] allProfiles = new Profile[profileList.getLength()];        
        for(int i=0; i < profileList.getLength(); i++){
        	allProfiles[i] = new Profile(profileList.item(i));        	
        }        
        return allProfiles;    
	}
	
	public Profile[] getProfiles(User user) throws Exception{		
        NodeList profileList = getNodeListFromPage(rootUrl+"profiles", user, "profile");
        Profile[] allProfiles = new Profile[profileList.getLength()];        
        for(int i=0; i < profileList.getLength(); i++){
        	allProfiles[i] = new Profile(profileList.item(i));        	
        }        
        return allProfiles;        
	}
	
	public Group[] getGroups(User user) throws Exception{
        NodeList groupList = getNodeListFromPage(rootUrl + "groups", user, "group");
        Group[] allGroups = new Group[groupList.getLength()];        
        for(int i=0; i< groupList.getLength(); i++){
        	allGroups[i] = new Group(groupList.item(i));
        }
        return allGroups;
	}
	
	public Campaign[] getCampaigns (User user) throws Exception {
		NodeList campaignList = getNodeListFromPage(rootUrl + "campaigns", user, "campaign");
        Campaign[] allCampaigns = new Campaign[campaignList.getLength()];
        for(int i=0; i<campaignList.getLength(); i++){
        	allCampaigns[i] = new Campaign(campaignList.item(i));
        }
        return allCampaigns;
	}
	
	//used to fetch value from xml tag named nodeName which is one level below superElem
	public static String getValueFromChildNodeByNodeName(Element superElem, String nodeName) throws Exception{
		StringWriter sw = new StringWriter();
        StreamResult result = new StreamResult(sw);
        DOMSource source = new DOMSource();
        TransformerFactory transfac = TransformerFactory.newInstance();
        Transformer trans = transfac.newTransformer();
        trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        
		NodeList nameList = superElem.getElementsByTagName(nodeName);
		Element firstNameElem = (Element) nameList.item(0);
		source.setNode(firstNameElem.getFirstChild());
		trans.transform(source, result);
		return sw.toString();
	}
	
	//gets NodeList of all nodes with tag name tagName from xml page uri, using user credentials
	private NodeList getNodeListFromPage(String uri, User user, String tagName) throws Exception{
		final URLConnection connection;
		final InputStream inStream;
		connection = new URL(uri).openConnection();
		authenticate(user, connection);
		inStream = connection.getInputStream();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(inStream);
		doc.getDocumentElement().normalize();
        return doc.getElementsByTagName(tagName);
	}
	
	private void authenticate(User user, URLConnection connection){
		String userPass = user.getStringForAuth();
		String encoding = new sun.misc.BASE64Encoder().encode(userPass.getBytes());
		connection.setRequestProperty("Authorization", "Basic " + encoding);
	}
}