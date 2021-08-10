package bill_history;

public class UserBeanBill {

	String bill_id,mobile,no_of_days,date_of_bill,amount,bill_status;

	public UserBeanBill(String bill_id, String mobile, String no_of_days, String date_of_bill, String amount,
			String bill_status) {
		super();
		this.bill_id = bill_id;
		this.mobile = mobile;
		this.no_of_days = no_of_days;
		this.date_of_bill = date_of_bill;
		this.amount = amount;
		this.bill_status = bill_status;
	}

	public String getBill_id() {
		return bill_id;
	}

	public void setBill_id(String bill_id) {
		this.bill_id = bill_id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNo_of_days() {
		return no_of_days;
	}

	public void setNo_of_days(String no_of_days) {
		this.no_of_days = no_of_days;
	}

	public String getDate_of_bill() {
		return date_of_bill;
	}

	public void setDate_of_bill(String date_of_bill) {
		this.date_of_bill = date_of_bill;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getBill_status() {
		return bill_status;
	}

	public void setBill_status(String bill_status) {
		this.bill_status = bill_status;
	}

	
}
