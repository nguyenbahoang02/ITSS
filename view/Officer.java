package view;

public class Officer extends Employee{
	
	private OfficerWorksDetail officerWorksDetail = new OfficerWorksDetail();
	
	public Officer(String name, String id, String unitId) {
		super(name, id, unitId);
	}

	public OfficerWorksDetail getOfficerWorksDetail() {
		return officerWorksDetail;
	}

	public void setOfficerWorksDetail(OfficerWorksDetail officerWorksDetail) {
		this.officerWorksDetail = officerWorksDetail;
	}
	
}
