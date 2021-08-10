package bill_history;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import database_connectivity.DataBaseConnectivity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

public class BillHistoryController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private RadioButton rdoPaid;

    @FXML
    private ToggleGroup bill;

    @FXML
    private RadioButton rdoUnpaid;

    @FXML
    private TextField txtMob;

    @FXML
    private RadioButton rdoP;

    @FXML
    private ToggleGroup chk;

    @FXML
    private RadioButton rdoU;

    @FXML
    private TableView<UserBeanBill> tblBill;

    @FXML
    private TextField txtAmount;
    //bill_id,mobile,noOfDays,date_of_bill,bill_status;
    TableColumn<UserBeanBill,String> id,mob,days,date,amount,status;
    
    @FXML
    void doFetchAll(ActionEvent event) {

    	ObservableList<UserBeanBill>ary=getAllRecords();
    	tblBill.setItems(ary);
    	
    }

    @FXML
    void clearTbl(ActionEvent event) {

    	tblBill.getItems().clear();
    	
    }
    
    ObservableList<UserBeanBill> getAllRecords()
    {
    	ObservableList<UserBeanBill>ary=FXCollections.observableArrayList();
    	try 
    	{
    		if(rdoPaid.isSelected())
    			ps=con.prepareStatement("select * from billing where bill_status=1");
    		else if(rdoUnpaid.isSelected())
    			ps=con.prepareStatement("select * from billing where bill_status=0");
    		
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				String id=String.valueOf(rs.getInt("bill_id"));
    			String mob=rs.getString("mobile");
    			String days=String.valueOf(rs.getInt("no_of_days"));
    			System.out.println(days);
    			String date=rs.getDate("date_of_bill").toString();
    			
    			String amo=String.valueOf(rs.getFloat("amount"));
    			String status=String.valueOf(rs.getInt("bill_status"));
    			UserBeanBill ref=new UserBeanBill(id, mob, days, date, amo, status);
    			ary.add(ref);
			}
    	}
    	catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return ary;
    }
    
    @FXML
    void doShow(ActionEvent event) {

    	ObservableList<UserBeanBill>ary=getRecords();
    	tblBill.setItems(ary);
    	
    }

    ObservableList<UserBeanBill> getRecords()
    {
    	ObservableList<UserBeanBill>ary=FXCollections.observableArrayList();
    	try 
    	{
    		if(rdoP.isSelected())
    			ps=con.prepareStatement("select * from billing where mobile=? and bill_status=1");
    		else if(rdoU.isSelected())
    			ps=con.prepareStatement("select * from billing where mobile=? and bill_status=0");
    		
    		ps.setString(1, txtMob.getText());
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				String id=String.valueOf(rs.getInt("bill_id"));
    			String mob=rs.getString("mobile");
    			String days=String.valueOf(rs.getInt("no_of_days"));
    			String date=rs.getDate("date_of_bill").toString();
    			String amo=String.valueOf(rs.getFloat("amount"));
    			String status=String.valueOf(rs.getInt("bill_status"));
    			UserBeanBill ref=new UserBeanBill(id, mob, days, date, amo, status);
    			ary.add(ref);
			}
    	}
    	catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return ary;
    }
    
    Connection con;
    PreparedStatement ps;
    
    @SuppressWarnings("unchecked")
	@FXML
    void initialize() {
    	
    	con=DataBaseConnectivity.getConnection();
    	
    	
    	id=new TableColumn<UserBeanBill, String>("bill_id");
    	id.setCellValueFactory(new PropertyValueFactory<>("bill_id"));
    	id.setMinWidth(100);
    	
    	mob=new TableColumn<UserBeanBill, String>("Mobile No.");
    	mob.setCellValueFactory(new PropertyValueFactory<>("mobile"));
    	mob.setMinWidth(100);
    	
    	days=new TableColumn<UserBeanBill, String>("no of days");
    	days.setCellValueFactory(new PropertyValueFactory<>("no_of_days"));
    	days.setMinWidth(100);
    	
    	date=new TableColumn<UserBeanBill, String>("date of bill");
    	date.setCellValueFactory(new PropertyValueFactory<>("date_of_bill"));
    	date.setMinWidth(100);
    	
    	amount=new TableColumn<UserBeanBill, String>("amount");
    	amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
    	amount.setMinWidth(100);
    	
    	status=new TableColumn<UserBeanBill, String>("Bill Status");
    	status.setCellValueFactory(new PropertyValueFactory<>("bill_status"));
    	status.setMinWidth(100);
    	
  
    	
    	tblBill.getColumns().addAll(id,mob,days,date,amount,status);
    	
    	
    	
        assert rdoPaid != null : "fx:id=\"rdoPaid\" was not injected: check your FXML file 'BillHistory.fxml'.";
        assert bill != null : "fx:id=\"bill\" was not injected: check your FXML file 'BillHistory.fxml'.";
        assert rdoUnpaid != null : "fx:id=\"rdoUnpaid\" was not injected: check your FXML file 'BillHistory.fxml'.";
        assert txtMob != null : "fx:id=\"txtMob\" was not injected: check your FXML file 'BillHistory.fxml'.";
        assert rdoP != null : "fx:id=\"rdoP\" was not injected: check your FXML file 'BillHistory.fxml'.";
        assert chk != null : "fx:id=\"chk\" was not injected: check your FXML file 'BillHistory.fxml'.";
        assert rdoU != null : "fx:id=\"rdoU\" was not injected: check your FXML file 'BillHistory.fxml'.";
        assert tblBill != null : "fx:id=\"tblBill\" was not injected: check your FXML file 'BillHistory.fxml'.";
        assert txtAmount != null : "fx:id=\"txtAmount\" was not injected: check your FXML file 'BillHistory.fxml'.";

    }
}
