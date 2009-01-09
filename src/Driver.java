public class Driver {
	public static void main(String[] args) throws Exception
	{
		mCommons site = new mCommons("http://hrc.localhost.com:3000/api/");		
		User me = new User("asdf@asdf.com", "asdf");
		
//		Campaign[] campaigns = {};
//		
//
//		campaigns = site.getCampaigns(me);
//		
//		for(int i=0; i < campaigns.length; i++){
//			System.out.println(campaigns[i].toString());
//		}
//		
		Group[] groups;
		groups = site.getGroups(me);
		
		for (int i=0; i < groups.length; i++){
			System.out.println(groups[i].toString());
		}
		
		Profile[] profiles;
		profiles = site.getGroupMembers(me, groups[groups.length-1]);
		for(int i=0; i < profiles.length; i++){
			System.out.println(profiles[i].toString());
		}
		
//		CampaignSubscriber[] subz;
//		subz = site.getCampaignSubscribers(me, campaigns[2]);
//		for(int i=0; i<100; i++){
//			System.out.println(subz[i].toString());
//		}
	}
}