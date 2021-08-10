package tableView;

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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
//import tableView.*;

public class TableViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn;

    
    @FXML
    private TableView<UserBean> tblID;

    @SuppressWarnings("unchecked")
	@FXML
    void doShow(ActionEvent event) {

    	TableColumn<UserBean, String>cus=new TableColumn<UserBean, String>("nameOFCustomer");
    	cus.setCellValueFactory(new PropertyValueFactory<>("customer_name"));
    	cus.setMinWidth(100);
    	
    	TableColumn<UserBean, String>mob=new TableColumn<UserBean, String>("Mobile No.");
    	mob.setCellValueFactory(new PropertyValueFactory<>("mobile"));
    	mob.setMinWidth(100);
    	
    	TableColumn<UserBean, String>addr=new TableColumn<UserBean, String>("address");
    	addr.setCellValueFactory(new PropertyValueFactory<>("address"));
    	addr.setMinWidth(100);
    	
    	tblID.getColumns().clear();
    	tblID.getColumns().addAll(cus,mob,addr);
    	
    	
    	ObservableList<UserBean>ary=getAllRecords();
    	tblID.setItems(null);
    	tblID.setItems(ary);
    }

    
    ObservableList<UserBean> getAllRecords()
    {
    	ObservableList<UserBean>ary=FXCollections.observableArrayList();
        try 
        {
			ps=con.prepareStatement("select * from customer");
			ResultSet rs=ps.executeQuery();
	    	while(rs.next())
	    		{
	    			String name=rs.getString("customer_name");
	    			String mob=rs.getString("mobile");
	    			String add=rs.getString("address");
	    			String pap=rs.getString("paper");
	    			String pri=rs.getString("price");
	    			String haw_name=rs.getString("hawker_name");
	    			String date=rs.getDate("cur_date").toString();
	    			UserBean ref=new UserBean(name,mob,add,pap,pri,haw_name,date);
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
    @FXML
    void initialize() {
    	
    	con=DataBaseConnectivity.getConnection();
    	
        assert tblID != null : "fx:id=\"tblID\" was not injected: check your FXML file 'TableView.fxml'.";

    }
}
