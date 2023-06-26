package view;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Unit {
	private String name;
	private String id;
	private ArrayList<Officer> officers = new ArrayList<>();
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public ArrayList<Officer> getOfficers() {
		return officers;
	}
	public void setOfficers(ArrayList<Officer> officers) {
		this.officers = officers;
	}
	public Unit(String id) {
		super();
		this.id = id;
	}
	
	public Unit(String name, String id) {
		super();
		this.name = name;
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void getEmployees(String path) {
		try {
			Reader reader = Files.newBufferedReader(Paths.get(path));
			List<Officer> list = new Gson().fromJson(reader,
					new TypeToken<List<Officer>>() {
					}.getType());
			reader.close();
			officers.addAll(list);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	
	public void getEmployeesWorksData() {
		for (Officer officer : officers) {
			officer.setOfficerWorksDetail(new OfficerWorksDetail());
			officer.getOfficerWorksDetail().getDataFromFile();
		}
	}
	
	public boolean importData(ArrayList<OfficerTimesheet> officerTimesheet) {
		for (OfficerTimesheet officerTimesheet2 : officerTimesheet) {
			for (Officer officer : officers) {
				if(officer.getId().equals(officerTimesheet2.getId())) {
					if(!officer.getOfficerWorksDetail().importData(officerTimesheet2)) {
						return false;
					}
					break;
				}
			}
		}
		return true;
	}
}
