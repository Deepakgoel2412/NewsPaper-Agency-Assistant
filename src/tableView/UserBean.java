package tableView;

public class UserBean {
	
		String customer_name,mobile,address,paper,price,hawker_name,cur_Date;

		public UserBean(String customer_name, String mobile, String address, String paper, String price,
				String hawker_name, String cur_Date) {
			super();
			this.customer_name = customer_name;
			this.mobile = mobile;
			this.address = address;
			this.paper = paper;
			this.price = price;
			this.hawker_name = hawker_name;
			this.cur_Date = cur_Date;
		}

		public String getCustomer_name() {
			return customer_name;
		}

		public void setCustomer_name(String customer_name) {
			this.customer_name = customer_name;
		}

		public String getMobile() {
			return mobile;
		}

		public void setMobile(String mobile) {
			this.mobile = mobile;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getPaper() {
			return paper;
		}

		public void setPaper(String paper) {
			this.paper = paper;
		}

		public String getPrice() {
			return price;
		}

		public void setPrice(String price) {
			this.price = price;
		}

		public String getHawker_name() {
			return hawker_name;
		}

		public void setHawker_name(String hawker_name) {
			this.hawker_name = hawker_name;
		}

		public String getCur_Date() {
			return cur_Date;
		}

		public void setCur_Date(String cur_Date) {
			this.cur_Date = cur_Date;
		}

}
