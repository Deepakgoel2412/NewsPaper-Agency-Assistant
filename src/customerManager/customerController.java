package customerManager;
//import paperMaster.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import database_connectivity.DataBaseConnectivity;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
//import javafx.event.Event;
//import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class customerController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtMobile;

    @FXML
    private TextField txtAddress;

    @FXML
    private ListView<String> lstPaper;

    @FXML
    private ListView<String> lstPrice;

    @FXML
    private ComboBox<String> comboArea;

    @FXML
    private TextField txtHawker;
    
    @FXML
    private Label lblID;
    
    @FXML
    void doClear(ActionEvent event) {

    	txtName.setText("");
    	txtMobile.setText("");
    	txtAddress.setText("");
    	comboArea.getEditor().setText("");
    	txtHawker.setText("");
    	fillListBox();
    	lblID.setText("");
    	
    }

    @FXML
    void doDelete(ActionEvent event) {

    	try
    	{
			ps=con.prepareStatement("delete from customer where mobile=?");
			ps.setString(1, txtMobile.getText());
			ps.executeUpdate();
			lblID.setText("Removed Successfully");
    	}
    	catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }

    @FXML
    void doSave(ActionEvent event) {

    	String pap="",pri="";
    	
    	ObservableList<String>obs=lstPaper.getSelectionModel().getSelectedItems();
    	ObservableList<String>obp=lstPrice.getSelectionModel().getSelectedItems();
    	for(String s:obp)
    	{
    		pri+=s;
    		pri+=",";
    	}
    	pri=pri.substring(0, pri.length()-1);
    	for(String s:obs)
    	{
    		pap+=s;
    		pap+=",";
    	}
    	pap=pap.substring(0, pap.length()-1);
    	String r=comboArea.getSelectionModel().getSelectedItem();
    	try
    	{
			ps=con.prepareStatement("insert into customer values(?,?,?,?,?,?,?,?)");
			ps.setString(1, txtName.getText());
			ps.setString(2, txtMobile.getText());
			ps.setString(3, txtAddress.getText());
			ps.setString(4, pap);
			ps.setString(5, pri);
			ps.setString(6, txtHawker.getText());
			LocalDate curDate=LocalDate.now();
			Date obj=Date.valueOf(curDate);
			ps.setDate(7, obj);
			ps.setString(8, r);
			ps.executeUpdate();
			
    	}
    	catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	lblID.setText("Saved Successfully");
    }

    void selectAuto()
    {
    	lstPrice.getSelectionModel().clearSelection();
    	ObservableList<Integer>obs=lstPaper.getSelectionModel().getSelectedIndices();
    	for(int a: obs)
    	{	
    		lstPrice.getSelectionModel().select(a);	
    	}
    }
    
    @FXML
    void doSearch(ActionEvent event) {

    	try
    	{
			ps=con.prepareStatement("select * from customer where mobile=?");
			ps.setString(1, txtMobile.getText());
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				txtName.setText(rs.getString("customer_name"));
				txtAddress.setText(rs.getString("address"));
				
				String s=rs.getString("paper");
				char ch[]=s.toCharArray();
				int i=0;
				while(ch[i]!='\0')
				{
					int index=s.indexOf(",", i);
					if(index==-1)
						break;
					String sub=s.substring(i, index);
					sub=sub.trim();
					lstPaper.getSelectionModel().select(sub);
					
					i=index+1;
				}
				int p=s.lastIndexOf(",");
				String a=s.substring(p+1,s.length());
				a=a.trim();
				lstPaper.getSelectionModel().select(a);
				selectAuto();
				
				comboArea.getEditor().setText(rs.getString("area"));
				txtHawker.setText(rs.getString("hawker_name"));
			}
    	}
    	catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

    @FXML
    void doUpdate(ActionEvent event) {

    	String pap="",pri="";
    	ObservableList<String>obs=lstPaper.getSelectionModel().getSelectedItems();
    	ObservableList<String>obp=lstPrice.getSelectionModel().getSelectedItems();
    	for(String s:obp)
    	{
    		pri+=s;
    		pri+=",";
    	}
    	for(String s:obs)
    	{
    		pap+=s;
    		pap+=",";
    	}
    	try 
    	{
			ps=con.prepareStatement("update customer set customer_name=?, address=?,paper=?,price=?, hawker_name=?,cur_date=?, area=? where mobile=? ");
			ps.setString(1, txtName.getText());
			ps.setString(2, txtAddress.getText());
			ps.setString(3, pap);
			ps.setString(4, pri);
			ps.setString(5, txtHawker.getText());
			LocalDate curDate=LocalDate.now();
			Date obj=Date.valueOf(curDate);
			ps.setDate(6, obj);
			ps.setString(7, txtMobile.getText());
			ps.setString(8, comboArea.getSelectionModel().getSelectedItem());
			ps.executeUpdate();
    	}
    	catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

    Connection con;
    PreparedStatement ps;
    
    @FXML
    void getSelect(MouseEvent event) {

    	lstPrice.getSelectionModel().clearSelection();
    	ObservableList<Integer>obs=lstPaper.getSelectionModel().getSelectedIndices();
    	for(int a: obs)
    	{	
    		lstPrice.getSelectionModel().select(a);	
    	}
    	
    }
    
    void getAreaInCombo()
    {
 	   comboArea.getItems().clear();
 	   try
 	   {
 		   ps=con.prepareStatement("select area_name from areas where value=1");
 		   ResultSet rs=ps.executeQuery();
 		   while(rs.next())
 		   {
 			   String s=rs.getString("area_name");
 			   comboArea.getItems().add(s);
 		   }
 		   
 	   } 
 	   catch (SQLException e) {
 		// TODO Auto-generated catch block
 		e.printStackTrace();
 	   }
 	   
 	   
    }
    String area="";
    @FXML
    void findHawker(ActionEvent event) {
    	
    	area=comboArea.getSelectionModel().getSelectedItem();
    	try 
    	{
			ps=con.prepareStatement("select hawker from hawkers where areas like ? ");
			ps.setString(1, '%'+area+'%');
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				String name=rs.getString("hawker");
				txtHawker.setText(name);
				//System.out.println("Name is "+name);
			}
		}
    	catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    void fillListBox()
    {
    	lstPrice.getItems().clear();
    	lstPaper.getItems().clear();
    	//lstPaper.getSelectionModel().clearSelection();
    	
    	
    	try 
    	{
			ps=con.prepareStatement("select * from papers");
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				String s=rs.getString("paper");
				lstPaper.getItems().add(s);
				float i=rs.getFloat("price");
				lstPrice.getItems().add(String.valueOf(i));
				
			}
			
			lstPrice.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			lstPaper.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		}
    	
    	catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void initialize() {
    	
    	
    	con=DataBaseConnectivity.getConnection();
    	getAreaInCombo();
    	
    	fillListBox();
   
    
        assert txtName != null : "fx:id=\"txtName\" was not injected: check your FXML file 'customer.fxml'.";
        assert txtMobile != null : "fx:id=\"txtMobile\" was not injected: check your FXML file 'customer.fxml'.";
        assert txtAddress != null : "fx:id=\"txtAddress\" was not injected: check your FXML file 'customer.fxml'.";
        assert lstPaper != null : "fx:id=\"lstPaper\" was not injected: check your FXML file 'customer.fxml'.";
        assert lstPrice != null : "fx:id=\"lstPrice\" was not injected: check your FXML file 'customer.fxml'.";
        assert comboArea != null : "fx:id=\"comboArea\" was not injected: check your FXML file 'customer.fxml'.";
        assert txtHawker != null : "fx:id=\"txtHawker\" was not injected: check your FXML file 'customer.fxml'.";

    }
}
