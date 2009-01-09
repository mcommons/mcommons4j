	public class User {
		private String name;
		private String password;
		
		User(String nm, String pw){
			name = nm;
			password = pw;
		}
		
		public String getStringForAuth(){
			return (name + ":" + password);
		}
	}