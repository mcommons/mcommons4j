import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
 
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
  User user;
  
  mCommons(String url, User aUser) { // "http://hrc.mcommons.com/api/"
    rootUrl = url;
    user = aUser;
  }
  
  public CampaignSubscriber[] getCampaignSubscribers(Campaign campaign)
    throws Exception{
    return getCampaignSubscribers(campaign.getId());
    }
  
  public CampaignSubscriber[] getCampaignSubscribers(String campaignId)
    throws Exception {
        NodeList subList =
          getNodeListFromPage(rootUrl+"campaign_subscribers?campaign_id="+campaignId, "sub");
        CampaignSubscriber[] allSubs= new CampaignSubscriber[subList.getLength()];
        
        for(int i=0; i < subList.getLength(); i++){
          allSubs[i] = new CampaignSubscriber(subList.item(i));   
        }
        return allSubs;
  }
  
  public Profile[] getGroupMembers(Group group) throws Exception {
    return getGroupMembers(group.getId());
  }
  
  public Profile[] getGroupMembers(String groupId) throws Exception{
        NodeList profileList =
          getNodeListFromPage(rootUrl+"group_members?group_id="+groupId, "profile");
        Profile[] allProfiles = new Profile[profileList.getLength()];
        for(int i=0; i < profileList.getLength(); i++){
          allProfiles[i] = new Profile(profileList.item(i));   
        }
        return allProfiles;
  }
  
  public Profile[] getProfiles() throws Exception{    
        NodeList profileList = getNodeListFromPage(rootUrl+"profiles", "profile");
        Profile[] allProfiles = new Profile[profileList.getLength()];
        for(int i=0; i < profileList.getLength(); i++){
          allProfiles[i] = new Profile(profileList.item(i));   
        }
        return allProfiles;
  }
  
  public Group[] getGroups() throws Exception{
        NodeList groupList = getNodeListFromPage(rootUrl + "groups", "group");
        Group[] allGroups = new Group[groupList.getLength()];
        for(int i=0; i< groupList.getLength(); i++){
          allGroups[i] = new Group(groupList.item(i));
        }
        return allGroups;
  }
  
  public Campaign[] getCampaigns () throws Exception {
    NodeList campaignList = getNodeListFromPage(rootUrl + "campaigns", "campaign");
        Campaign[] allCampaigns = new Campaign[campaignList.getLength()];
        for(int i=0; i<campaignList.getLength(); i++){
          allCampaigns[i] = new Campaign(campaignList.item(i));
        }
        return allCampaigns;
  }
  
  public InputStream sendSmsMessage(String campaignId, String phoneNumber, String body) throws Exception {
	  Map<String,String> params = new HashMap<String,String>();
	  params.put("campaign_id", campaignId);
	  params.put("phone_number", phoneNumber);
	  params.put("body", body);
	   
	  InputStream stream = validatedPost(rootUrl+"send_message", params);

	  return stream;
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
  private NodeList getNodeListFromPage(String uri, String tagName) throws Exception{
    final URLConnection connection;
    final InputStream inStream;
    connection = new URL(uri).openConnection();
    authenticate(connection);
    inStream = connection.getInputStream();
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document doc = builder.parse(inStream);
    doc.getDocumentElement().normalize();
    return doc.getElementsByTagName(tagName);
  }
  
	private InputStream validatedPost(String uri, Map<String, String> vars) throws Exception
	{
		HttpURLConnection connection;
		connection = (HttpURLConnection) new URL(uri).openConnection();
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		authenticate(connection);
		
		StringBuilder encodedData = new StringBuilder();
		for (String key : vars.keySet()){
			String val = vars.get(key);
			encodedData.append(key);
			encodedData.append('=');
			encodedData.append(val);
			encodedData.append('&');					
		}
		connection.setRequestProperty("Content-Length", "" + encodedData.length());
		OutputStream os = connection.getOutputStream();
		os.write(encodedData.toString().getBytes());
		os.close();
		InputStream inStream = connection.getInputStream();
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    Document doc = builder.parse(inStream);
	    doc.getDocumentElement().normalize();
	    
	    NodeList responseList = doc.getElementsByTagName("response");	    
        Element responseElem = (Element) responseList.item(0);
        
        assert responseElem.getAttribute("success").equals("true");
        
		return inStream;
	}
  
  private void authenticate(URLConnection connection){
    String userPass = user.getStringForAuth();
    String encoding = new sun.misc.BASE64Encoder().encode(userPass.getBytes());
    connection.setRequestProperty("Authorization", "Basic " + encoding);
  }
}