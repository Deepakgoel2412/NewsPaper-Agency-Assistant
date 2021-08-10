package bill_collector;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import database_connectivity.DataBaseConnectivity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class BillCollectorController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboMob;

    @FXML
    private ListView<String> lstDate;

    @FXML
    private ListView<String> lstPrice;

    @FXML
    private TextField txtAmount;

    @FXML
    private Label show;
    
    void clearAll()
    {
    	lstDate.getItems().clear();
    	lstPrice.getItems().clear();
    	txtAmount.setText("");
    	
    }
    
    @FXML
    void doFetch(ActionEvent event) {

    	float sum=0;
    	String mob=comboMob.getSelectionModel().getSelectedItem();
    	try 
    	{
			ps=con.prepareStatement("select date_of_bill,amount from billing where mobile=? and bill_status=0");
			ps.setString(1, mob);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				Date d=rs.getDate("date_of_bill");
				String s=d.toString();
				lstDate.getItems().add(s);
				
				Float p=rs.getFloat("amount");
				sum+=p;
				String r=p.toString();
				lstPrice.getItems().add(r);
			}
			
			txtAmount.setText(String.valueOf(sum));
			
    	}
    	catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

    @FXML
    void doPayBill(ActionEvent event) {

    	String mob=comboMob.getSelectionModel().getSelectedItem();
    	try
    	{
			ps=con.prepareStatement("update billing set bill_status=1 where mobile=? and bill_status=0");
			ps.setString(1, mob);
			ps.executeUpdate();
			show.setText("Transaction Successfull");
    	}
    	catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }

    Connection con;
    PreparedStatement ps;
    
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
    
    @FXML
    void initialize() {
    	
    	con=DataBaseConnectivity.getConnection();
    	
    	getMobInCombo();
    	
    	
    	
//        assert comboMob != null : "fx:id=\"comboMob\" was not injected: check your FXML file 'BillCollector.fxml'.";
//        assert lstDate != null : "fx:id=\"lstPaper\" was not injected: check your FXML file 'BillCollector.fxml'.";
//        assert lstPrice != null : "fx:id=\"lstPrice\" was not injected: check your FXML file 'BillCollector.fxml'.";
//        assert txtAmount != null : "fx:id=\"txtAmount\" was not injected: check your FXML file 'BillCollector.fxml'.";

    }
}
