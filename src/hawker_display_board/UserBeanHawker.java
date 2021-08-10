package hawker_display_board;

public class UserBeanHawker {

	String mobile,hawker,address,salary,imgPath,areas,doj;

	public UserBeanHawker(String mobile, String hawker, String address, String salary, String imgPath, String areas,
			String doj) {
		super();
		this.mobile = mobile;
		this.hawker = hawker;
		this.address = address;
		this.salary = salary;
		this.imgPath = imgPath;
		this.areas = areas;
		this.doj = doj;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getHawker() {
		return hawker;
	}

	public void setHawker(String hawker) {
		this.hawker = hawker;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getAreas() {
		return areas;
	}

	public void setAreas(String areas) {
		this.areas = areas;
	}

	public String getDoj() {
		return doj;
	}

	public void setDoj(String doj) {
		this.doj = doj;
	}
	
}
