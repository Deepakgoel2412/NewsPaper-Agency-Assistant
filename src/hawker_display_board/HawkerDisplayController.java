package hawker_display_board;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class HawkerDisplayController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<UserBeanHawker> tblHawker;

    @SuppressWarnings("unchecked")
	@FXML
    void doFetch(ActionEvent event) {
    	 //mobile,hawker,address,salary,imgPath,areas,doj;
    	TableColumn<UserBeanHawker,String> mob,haw,addr,sal,img,area,doj;
    	
    	mob=new TableColumn<UserBeanHawker, String>("Mobile No.");
    	mob.setCellValueFactory(new PropertyValueFactory<>("mobile"));
    	mob.setMinWidth(100);
    	
    	haw=new TableColumn<UserBeanHawker, String>("Hawker");
    	haw.setCellValueFactory(new PropertyValueFactory<>("hawker"));
    	haw.setMinWidth(100);
    	
    	addr=new TableColumn<UserBeanHawker, String>("Address");
    	addr.setCellValueFactory(new PropertyValueFactory<>("address"));
    	addr.setMinWidth(100);
    	
    	sal=new TableColumn<UserBeanHawker, String>("Salary");
    	sal.setCellValueFactory(new PropertyValueFactory<>("salary"));
    	sal.setMinWidth(100);
    	
    	img=new TableColumn<UserBeanHawker, String>("Image-Path");
    	img.setCellValueFactory(new PropertyValueFactory<>("imgPath"));
    	img.setMinWidth(100);
    	
    	area=new TableColumn<UserBeanHawker, String>("area");
    	area.setCellValueFactory(new PropertyValueFactory<>("areas"));
    	area.setMinWidth(100);
    	
    	
    	doj=new TableColumn<UserBeanHawker, String>("doj");
    	doj.setCellValueFactory(new PropertyValueFactory<>("doj"));
    	doj.setMinWidth(100);
    	
    	
    	
    	
    	tblHawker.getColumns().addAll(mob,haw,addr,sal,img,area,doj);
    	ObservableList<UserBeanHawker>ary=getAllRecords();
    	tblHawker.setItems(ary);
    }
    
    ObservableList<UserBeanHawker> getAllRecords()
    {
    	ObservableList<UserBeanHawker>ary=FXCollections.observableArrayList();
    	try 
    	{
			ps=con.prepareStatement("select * from hawkers");
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
    			String mob=rs.getString("mobile");
    			String haw_name=rs.getString("hawker");
    			String add=rs.getString("address");
    			String sal=String.valueOf(rs.getFloat("salary"));
    			String img=rs.getString("imgPath");
    			String area=rs.getString("areas");
    			String date=rs.getDate("doj").toString();
    			UserBeanHawker ref=new UserBeanHawker(mob, haw_name, add, sal, img, area, date);
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
    	
    	
        assert tblHawker != null : "fx:id=\"tblHawker\" was not injected: check your FXML file 'HawkerDisplay.fxml'.";

    }
}
