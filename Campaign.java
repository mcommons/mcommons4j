import org.apache.commons.lang.builder.ToStringBuilder;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
 
public class Campaign
{
  private String name;
  private String id;
  private Boolean active;
  
  public Campaign(Node node) throws Exception{
    Element campaignElem = (Element) node;
    id = campaignElem.getAttribute("id");
    
    active = false;
      if (campaignElem.getAttribute("active").equals("true")){
        active = true;
      }
      
      name = MobileCommons.getValueFromChildNodeByNodeName(campaignElem, "name");   
  }
  
  public String getName(){
    return name;
  }
  public String getId(){
    return id;
  }
  public Boolean getActive(){
    return active;
  }
  
  public String toString() {
   return new ToStringBuilder(this).
   append("name", name).
   append("id", id).
   append("active", active).
   toString();
  }
}