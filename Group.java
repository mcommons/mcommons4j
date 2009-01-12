import org.apache.commons.lang.builder.ToStringBuilder;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
 
public class Group {
  private String name;
  private String id;
  private Integer size;
  enum GroupType { FILTERED_GROUP, UPLOADED_GROUP } ;
  private GroupType type;
  
  public Group(Node node) throws Exception{
    Element groupElem = (Element) node;
    id = groupElem.getAttribute("id");
    
    String tp = groupElem.getAttribute("type");
    if (tp.equals("UploadedGroup")){
      type = GroupType.UPLOADED_GROUP;
    }
    else if (tp.equals("FilteredGroup")){
      type = GroupType.FILTERED_GROUP;
    }
    
    name = MobileCommons.getValueFromChildNodeByNodeName(groupElem, "name");
    size = Integer.parseInt(MobileCommons.getValueFromChildNodeByNodeName(groupElem, "size"));    
  }
  
  
  public String getName(){
    return name;
  }
  public String getId(){
    return id;
  }
  public Integer getSize(){
    return size;
  }
  public GroupType getType(){
    return type;
  }
  public String getTypeString(){
    return type.toString();
  }
  public String toString() {
   return new ToStringBuilder(this).
   append("name", name).
   append("id", id).
   append("size", size).
   append("type", type.toString()).
   toString();
  }
}