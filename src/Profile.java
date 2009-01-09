import org.apache.commons.lang.builder.ToStringBuilder;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Profile{
	private String id;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String email;
	private Address address;
	
	public Profile(Node node) throws Exception{
		Element profileElem = (Element) node;
		id = profileElem.getAttribute("id");
		
        firstName = mCommons.getValueFromChildNodeByNodeName(profileElem, "first_name");
        lastName = mCommons.getValueFromChildNodeByNodeName(profileElem, "last_name");
        phoneNumber = mCommons.getValueFromChildNodeByNodeName(profileElem, "phone_number");        
        email = mCommons.getValueFromChildNodeByNodeName(profileElem, "email");
        
        NodeList addressList = profileElem.getElementsByTagName("address");
        Element addressElem = (Element) addressList.item(0);
        
        address = new Address(addressElem);
        
	}
	
	public String getId(){
		return id;
	}
	public String getFirstName(){
		return firstName;
	}
	public String getLastName(){
		return lastName;
	}
	public String getPhoneNumber(){
		return phoneNumber;
	}
	public String getEmail(){
		return email;
	}
	public Address getAddress(){
		return address;
	}
	
	public class Address{
		private String street1;
		private String street2;
		private String city;
		private String state;
		private String postalCode;
		private String country;
		
		public Address(Element elem) throws Exception{
			street1 = mCommons.getValueFromChildNodeByNodeName(elem, "street1");
			street2 = mCommons.getValueFromChildNodeByNodeName(elem, "street2");
			city = mCommons.getValueFromChildNodeByNodeName(elem, "city");
			state = mCommons.getValueFromChildNodeByNodeName(elem, "state");
			postalCode = mCommons.getValueFromChildNodeByNodeName(elem,"postal_code");
			country = mCommons.getValueFromChildNodeByNodeName(elem, "country");
		}
		
		public String getStreet1(){
			return street1;
		}
		public String getStreet2(){
			return street2;
		}
		public String getCity(){
			return city;
		}
		public String getState(){
			return state;
		}
		public String getPostalCode(){
			return postalCode;
		}
		public String getCountry(){
			return country;
		}
		public String toString() {
		     return new ToStringBuilder(this).
		     append("street1", street1).
		     append("street2", street2).
		     append("city", city).
		     append("state", state).
		     append("postalCode", postalCode).		   
		     append("country", country).toString();
		}
	}
		
	public String toString() {
	     return new ToStringBuilder(this).	       
	       append("id", id).
	       append("firstName", firstName).
	       append("lastName", lastName).
	       append("phoneNumber", phoneNumber).
	       append("email", email).
	       append(address.toString()).
	       toString();
	}
}