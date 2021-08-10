package dashboard;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DashBoardViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane dash;

    @FXML
    void callBillCollector(MouseEvent event) {

    	try 
    	{
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("bill_collector/BillCollector.fxml"));
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

    @FXML
    void callBillGenerator(MouseEvent event) {

    	try 
    	{
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("bill_generator/BillCalculator.fxml"));
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

    @FXML
    void callBillHistroy(MouseEvent event) {

    	try 
    	{
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("bill_history/BillHistory.fxml"));
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

    @FXML
    void callCustomer(MouseEvent event) {

    	try 
    	{
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("customerManager/customer.fxml"));
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

    @FXML
    void callCustomerDisplay(MouseEvent event) {

    	try 
    	{
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("customer_display_board/CustomerShow.fxml"));
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

    @FXML
    void callHawker(MouseEvent event) {

    	try 
    	{
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("hawkerControlPanel/HawkerControl.fxml"));
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

    @FXML
    void callHawkerDisplay(MouseEvent event) {

    	try 
    	{
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("hawker_display_board/HawkerDisplay.fxml"));
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

    @FXML
    void callPaper(MouseEvent event) {

    	try 
    	{
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("paperMaster/paperView.fxml"));
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

    @FXML
    void initialize() {
        assert dash != null : "fx:id=\"dash\" was not injected: check your FXML file 'DashBoardView.fxml'.";

    }
}
