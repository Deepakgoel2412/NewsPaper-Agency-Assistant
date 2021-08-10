package hawkerControlPanel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
//import javafx.collections.SetChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
//import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

public class HawkerControlController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboID;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtSal;

    @FXML
    private ListView<String> txtAreas;

    @FXML
    private ImageView imgID;

    @FXML
    private Label lblID;
    
    @FXML
    private ComboBox<String> comboArea;
    
    @FXML
    private DatePicker dojID;
    
    String imgPath="";
    int count=0;
    Connection con;
    PreparedStatement ps;
    
    @FXML
    void doNew(ActionEvent event) {

    	comboID.getEditor().setText("");
    	txtName.setText("");
    	txtAddress.setText("");
    	txtSal.setText("");
    	comboArea.getItems().clear();
    	txtAreas.getItems().clear();
    	imgID.setImage(null);
    	getItemsInCombo();
    }

    void updateArea(String s)
    {
    	try 
    	{
			ps=con.prepareStatement("update areas set value=0 where area_name=?");
			ps.setString(1,s);
			ps.executeUpdate();
		} 
    	catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    @FXML
    void doRemove(ActionEvent event) {

    	try
    	{
    		String s= comboID.getSelectionModel().getSelectedItem();
			ps=con.prepareStatement("delete from hawkers where mobile=?");
			ps.setString(1,s);
			ps.executeUpdate();
			lblID.setText("Removed Successfully");
			
			ps=con.prepareStatement("select areas from hawkers where mobile =?");
			ps.setString(1, s);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				int i=0;
				String r=rs.getString("areas");
				char ch[]=r.toCharArray();
				while(ch[i]!='\0')
				{
					int index=r.indexOf(",", i);
					if(index==-1)
						break;
					String sub=s.substring(i, index);
					sub=sub.trim();
					updateArea(sub);
					i=index+1;
				}
				int p=r.lastIndexOf(",");
				String a=r.substring(p+1,s.length());
				a=a.trim();
				updateArea(a);
			}
			
			
		}
    	catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }

    @FXML
    void doSave(ActionEvent event) {

    	String lst="";
    	String mob=comboID.getEditor().getText();

    	ObservableList<String>obs=txtAreas.getItems();
    	for(String s:obs)
    	{
    		try
    		{
				ps=con.prepareStatement("update areas set value=1 where area_name=?");
				ps.setString(1, s);
				ps.executeUpdate();
    		}
    		catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    		lst+=s;
    		lst+=", ";
    		
    	}
    	lst=lst.substring(0, lst.length()-2);
    	try 
    	{
			ps=con.prepareStatement("insert into hawkers values(?,?,?,?,?,?,?)");
			ps.setString(1, mob);
			if(txtName.getText().isEmpty())
			{
				Alert al=new Alert(AlertType.ERROR);
				al.setContentText("Name field is empty");
				al.show();
			}
			ps.setString(2, txtName.getText());
			ps.setString(3, txtAddress.getText());
			ps.setInt(4, Integer.parseInt(txtSal.getText()));
			ps.setString(5, imgPath);
			ps.setString(6, lst);
			LocalDate ldoj=dojID.getValue();
			try {
				Date obj=Date.valueOf(ldoj);
				ps.setDate(7, obj);
			}
			catch(Exception e)
			{
				Alert a=new Alert(AlertType.ERROR);
				a.setContentText("Date is empty");
			}
			
			
			ps.executeUpdate();
			lblID.setText("Saved Successfully");
		}
    	catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }

    
    @FXML
    void doUpload(ActionEvent event) {

    	FileChooser fc=new FileChooser();
    	File f=fc.showOpenDialog(null);
    	if(f!=null)
    	{
    		FileInputStream fis;
    		try 
    		{
				fis=new FileInputStream(f);
				Image img=new Image(fis);
				imgID.setImage(img);
				imgPath=f.getAbsolutePath();
				
			}
    		catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	}
    	
    }
    
    void clearAll()
    {
    	//comboID.getEditor().setText("");
    	txtName.setText("");
    	txtAddress.setText("");
    	txtSal.setText("");
    	comboArea.getItems().clear();
    	txtAreas.getItems().clear();
    	imgID.setImage(null);
    	getItemsInCombo();
    }
    
    @FXML
    void doSearch(ActionEvent event) {

    	clearAll();
    	String text=comboID.getEditor().getText();
    	try 
    	{
			ps=con.prepareStatement("select * from hawkers where mobile=?");
			ps.setString(1, text);
			ResultSet table=ps.executeQuery();
			if(table.next())
			{
				txtName.setText(table.getString("hawker"));
				txtAddress.setText(table.getString("address"));
				txtSal.setText(String.valueOf(table.getInt("salary")));
				Date dojj=table.getDate("doj");
				LocalDate ldoj=dojj.toLocalDate();
				dojID.setValue(ldoj);
				
				String s=table.getString("areas");
				char ch[]=s.toCharArray();
				int i=0;
				while(ch[i]!='\0')
				{
					int index=s.indexOf(",", i);
					if(index==-1)
						break;
					String sub=s.substring(i, index);
					sub=sub.trim();
					txtAreas.getItems().add(sub);
					comboArea.getItems().removeAll(sub);
					i=index+1;
				}
				int p=s.lastIndexOf(",");
				String a=s.substring(p+1,s.length());
				a=a.trim();
				txtAreas.getItems().add(a);
				comboArea.getItems().removeAll(a);
				String iPath=table.getString("imgPath");
				try
				{
					FileInputStream fis=new FileInputStream(iPath);
					Image img=new Image(fis);
					imgID.setImage(img);
					imgPath=iPath;
				} 
				catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			else
			{
				
				lblID.setText("Mobile no not found");
			}
		}
    	catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }

    @FXML
    void doUpdate(ActionEvent event) {

    	String lst="";
    	String mob=comboID.getEditor().getText();

    	ObservableList<String>obs=txtAreas.getItems();
    	for(String s:obs)
    	{
    		lst+=s;
    		lst+=", ";
    	}
    	lst=lst.substring(0, lst.length()-2);
    	try
    	{
			ps=con.prepareStatement("update hawkers set hawker=?, address=?, salary=?, doj=?, areas=?, imgPath=? where mobile=?");
			ps.setString(7, mob);
			ps.setString(1, txtName.getText());
			ps.setString(2, txtAddress.getText());
			ps.setInt(3, Integer.parseInt(txtSal.getText()));
			ps.setString(6, imgPath);
			ps.setString(5, lst);
			LocalDate ldoj=dojID.getValue();
			Date obj=Date.valueOf(ldoj);
			ps.setDate(4, obj);
			ps.executeUpdate();
			lblID.setText("Updated Successfully");
			
    	}
    	catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

    
    @FXML
    void doDelete(ActionEvent event) {

    	int index=txtAreas.getSelectionModel().getSelectedIndex();
    	String s=txtAreas.getSelectionModel().getSelectedItem();
    	txtAreas.getItems().remove(index);
    	count--;
    	comboArea.getItems().add(s);
    	
    	
    }
    
    @FXML
    void doAdd(ActionEvent event) {
    	
    	String s=comboArea.getSelectionModel().getSelectedItem();
    	//int index=txtAreas.getSelectionModel().getSelectedIndex();
    	txtAreas.getItems().add(s);
    	//count++;
    	comboArea.getItems().removeAll(s);
    	
    }
    
   void getItemsInCombo()
   {
	   try 
   		{
			ps=con.prepareStatement("select area_name from areas");
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
    
   void getMobInCombo()
   {
	   comboID.getItems().clear();
	   try
	   {
		   ps=con.prepareStatement("select mobile from hawkers");
		   ResultSet rs=ps.executeQuery();
		   while(rs.next())
		   {
			   String s=rs.getString("mobile");
			   comboID.getItems().add(s);
		   }
		   
	   } 
	   catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	   }
	   
	   
   }
   
   @FXML
   void backNormal(MouseEvent event) {
	   
	  // Button btn=(Button)event.getSource();
   	//String id=btn.getId();
   	//btn.setStyle("-fx-color: ");
	  // btn.getStyle().
	   
   }
   
   @FXML
   void hoverChange(MouseEvent event) {

	  // Button btn=(Button)event.getSource();
   	//String id=btn.getId();
   	//btn.setStyle("-fx-color: #827F7E");
	   
   }
   
    @FXML
    void initialize() {
    	
    	
    	con=DataBaseConnectivity.getConnection();
    	
    	getItemsInCombo();
    	getMobInCombo();
    	
 
        assert comboID != null : "fx:id=\"comboID\" was not injected: check your FXML file 'HawkerControl.fxml'.";
        assert txtName != null : "fx:id=\"txtName\" was not injected: check your FXML file 'HawkerControl.fxml'.";
        assert txtAddress != null : "fx:id=\"txtAddress\" was not injected: check your FXML file 'HawkerControl.fxml'.";
        assert txtSal != null : "fx:id=\"txtSal\" was not injected: check your FXML file 'HawkerControl.fxml'.";
        assert txtAreas != null : "fx:id=\"txtAreas\" was not injected: check your FXML file 'HawkerControl.fxml'.";
        assert imgID != null : "fx:id=\"imgID\" was not injected: check your FXML file 'HawkerControl.fxml'.";

    }
}
