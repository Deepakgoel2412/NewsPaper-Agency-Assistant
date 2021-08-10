package admin_page;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import database_connectivity.DataBaseConnectivity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AdminViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtUser;

    @FXML
    private TextField txtPass;

    @FXML
    void doLogin(ActionEvent event) {

    	String user=txtUser.getText();
    	String pass=txtPass.getText();
    	try 
    	{
			ps=con.prepareStatement("select * from users where user_name=?");
			ps.setString(1, user);
			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{
				String p=rs.getString("passwords");
				if(p.equals(pass))
				{
					try 
			    	{
						Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("dashboard/DashBoardView.fxml"));
						Scene scene=new Scene(root);
						Stage stage=new Stage();
						stage.setScene(scene);
						stage.show();
						
			    	}
			    	catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else
				{
					Alert alert=new Alert(AlertType.ERROR);
					alert.setTitle("Login failed");
					alert.setHeaderText(null);
					alert.setContentText("Invalid password");
					alert.show();
				}
			}
			else
			{
				Alert alert=new Alert(AlertType.ERROR);
				alert.setTitle("Login failed");
				alert.setHeaderText(null);
				alert.setContentText("Invalid Username");
				alert.show();
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
    	
        assert txtUser != null : "fx:id=\"txtUser\" was not injected: check your FXML file 'AdminView.fxml'.";
        assert txtPass != null : "fx:id=\"txtPass\" was not injected: check your FXML file 'AdminView.fxml'.";

    }
}
