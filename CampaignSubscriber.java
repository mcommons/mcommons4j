import java.text.DateFormat;
import java.text.SimpleDateFormat;
 
import org.apache.commons.lang.builder.ToStringBuilder;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
 
import java.util.Date;
 
public class CampaignSubscriber{
  private String id;
  private String phoneNumber;
  private Date activatedAt;
  
  public CampaignSubscriber(Node node) throws Exception{
    Element subElem = (Element) node;
    id = mCommons.getValueFromChildNodeByNodeName(subElem, "id");
    phoneNumber = mCommons.getValueFromChildNodeByNodeName(subElem, "phone_number");
    
    String dateString = mCommons.getValueFromChildNodeByNodeName(subElem, "activated_at");
    
    if (!dateString.equals("")){
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss zzz");
    activatedAt = df.parse(dateString);
    }  
  }
  
  
  public String getId(){
    return id;
  }
  
  public String getPhoneNumber(){
    return phoneNumber;
  }
  
  public Date getActivatedAt(){
    return activatedAt;
  }
  
  
  
  public String toString() {
   return new ToStringBuilder(this).
   append("id", id).
   append("phoneNumber", phoneNumber).
   append("activatedAt", activatedAt).toString();
  }
}