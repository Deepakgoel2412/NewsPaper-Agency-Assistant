package paperMaster;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import database_connectivity.DataBaseConnectivity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class paperViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboId;

    @FXML
    private TextField txtPrice;

    @FXML
    private ImageView imgLogo;

    @FXML
    private Label lblID;
    
    @FXML
    void doFetch(ActionEvent event) {

    	String text=comboId.getEditor().getText();
    	try 
    	{
			ps=con.prepareStatement("select * from papers where paper=?");
			ps.setString(1, text);
			ResultSet table=ps.executeQuery();
			if(table.next())
			{
				float pri=table.getFloat("price");
				txtPrice.setText(String.valueOf(pri));
			}
			else
			{
				txtPrice.setText("");
				lblID.setText("Invalid paper name");
			}
		}
    	catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }

    @FXML
    void doNew(ActionEvent event) {

    	comboId.getEditor().setText("");
    	txtPrice.clear();
    	lblID.setText("");
    }

    @FXML
    void doRemove(ActionEvent event) {

    	try 
    	{
			ps=con.prepareStatement("delete from papers where paper=?");
			ps.setString(1, comboId.getSelectionModel().getSelectedItem());
			ps.executeUpdate();
		}
    	catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	txtPrice.clear();
    	lblID.setText("Removed successfully");
    	comboUpdate();
    }

    @FXML
    void doSave(ActionEvent event) {

    	String paper=comboId.getEditor().getText();
    	try 
    	{
			ps=con.prepareStatement("insert into papers values(?,?)");
			ps.setString(1, paper);
			ps.setFloat(2, Float.parseFloat(txtPrice.getText()));
			ps.executeUpdate();
		}
    	catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	lblID.setText("Saved successfully");
    	comboUpdate();
    }

    @FXML
    void doUpdate(ActionEvent event) {

    	try 
    	{
			ps=con.prepareStatement("update papers set price=? where paper=?");
			ps.setFloat(1, Float.parseFloat(txtPrice.getText()));
			ps.setString(2, comboId.getEditor().getText());
			ps.executeUpdate();
			
		}
    	catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	lblID.setText("Updated successfully");
    	
    }

    void comboUpdate()
    {
    	comboId.getItems().clear();
    	try 
    	{
			ps=con.prepareStatement("select paper from papers");
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				String s=rs.getString("paper");
				comboId.getItems().add(s);
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
    	comboUpdate();
    	    	
    	
        assert comboId != null : "fx:id=\"comboId\" was not injected: check your FXML file 'paperView.fxml'.";
        assert txtPrice != null : "fx:id=\"txtPrice\" was not injected: check your FXML file 'paperView.fxml'.";
        assert imgLogo != null : "fx:id=\"imgLogo\" was not injected: check your FXML file 'paperView.fxml'.";

    }
}
