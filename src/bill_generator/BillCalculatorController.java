package bill_generator;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import database_connectivity.DataBaseConnectivity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class BillCalculatorController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboMob;

    @FXML
    private ListView<String> lstPaper;

    @FXML
    private ListView<String> lstPrice;

    @FXML
    private TextField txtBill;

    @FXML
    private Label lblDays;

    private int noOfDays;
    
    @FXML
    void doPrepare(ActionEvent event) {

    	String mob=comboMob.getSelectionModel().getSelectedItem();
    	try
    	{
			ps=con.prepareStatement("select cur_date from customer where mobile=?");
			ps.setString(1, mob);
			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{
				Date obj=rs.getDate("cur_date");
			//	System.out.println(obj);
				//LocalDate loc=obj.toLocalDate();
				LocalDate lcl=LocalDate.now();
				Date today=Date.valueOf(lcl);
			//	System.out.println(loc);
				//System.out.println(today);
				//Period diff=Period.between(loc,today);
				long diff=today.getTime()-obj.getTime();
				noOfDays=(int)(diff/(1000*60*60*24));
				System.out.println(noOfDays);
				//System.out.println(totalPrice);
				totalPrice*=noOfDays;
				txtBill.setText(String.valueOf(totalPrice));
				
				
			}
    	}
    	catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }

    @FXML
    void doSave(ActionEvent event) {

    	String mob=comboMob.getSelectionModel().getSelectedItem();
    	LocalDate today=LocalDate.now();
    	Date ob=Date.valueOf(today);
//    	Calendar c = Calendar.getInstance(); 
//    	c.setTime(ob); 
//    	c.add(Calendar.DATE, 1);
//    	ob=(Date) c.getTime();
    	//LocalDate lcl=ob.toLocalDate()
		try 
		{
			ps=con.prepareStatement("update customer set cur_date=? where mobile=?");
			ps.setDate(1, ob);
			ps.setString(2, mob);
			ps.executeUpdate();
			
			ps=con.prepareStatement("insert into billing(mobile,no_of_days,date_of_bill,amount) values(?,?,?,?)");
			ps.setString(1, mob);
			ps.setInt(2, noOfDays);
			ps.setDate(3, ob);
			ps.setFloat(4, totalPrice);
			
			ps.executeUpdate();
			
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }

    void clearAll()
    {
    	lstPaper.getItems().clear();
    	lstPrice.getItems().clear();
    	txtBill.setText("");
    	lblDays.setText("");
    }
    private float totalPrice;
    float getPriceFromTable(String r)
    {
    	//totalPrice=0;
    	float price=0;
    	try
    	{
			ps=con.prepareStatement("select price from papers where paper=?");
			ps.setString(1, r);
			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{
				price=rs.getFloat("price");
				//totalPrice+=price;
				String p=String.valueOf(price);
				lstPrice.getItems().add(p);
			}
    	}
    	catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return price;
    //	System.out.println("total price is "+totalPrice);
    }
    
    @FXML
    void doSearch(ActionEvent event) {

    	clearAll();
    	float p=0;
    	String mob=comboMob.getSelectionModel().getSelectedItem();
    	try 
    	{
    		
			ps=con.prepareStatement("select paper from customer where mobile=?");
			ps.setString(1, mob);
			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{
				String s=rs.getString("paper");
				String spl[]=s.split(",", 0);
				totalPrice=0;
				for(String r:spl)
				{
					lstPaper.getItems().add(r);
					
					p+=getPriceFromTable(r);
				}
			}
    	}
    	catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	totalPrice=p;
    	
    }

    void getMobInCombo()
    {
    	try 
    	{
			ps=con.prepareStatement("select mobile from customer");
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				String s=rs.getString("mobile");
				comboMob.getItems().add(s);
			}
			
		}
    	catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    Connection con;
    PreparedStatement ps;
    @FXML
    void initialize() {
    	
    	con=DataBaseConnectivity.getConnection();
    	getMobInCombo();
    	
    	
        assert comboMob != null : "fx:id=\"comboMob\" was not injected: check your FXML file 'BillCalculator.fxml'.";
        assert lstPaper != null : "fx:id=\"lstPaper\" was not injected: check your FXML file 'BillCalculator.fxml'.";
        assert lstPrice != null : "fx:id=\"lstPrice\" was not injected: check your FXML file 'BillCalculator.fxml'.";
        assert txtBill != null : "fx:id=\"txtBill\" was not injected: check your FXML file 'BillCalculator.fxml'.";
        assert lblDays != null : "fx:id=\"lblDays\" was not injected: check your FXML file 'BillCalculator.fxml'.";

    }
}
