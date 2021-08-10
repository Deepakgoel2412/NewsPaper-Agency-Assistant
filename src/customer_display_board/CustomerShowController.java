package customer_display_board;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.sl.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.*;
import database_connectivity.DataBaseConnectivity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.media.AudioClip;


public class CustomerShowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboArea;

    @FXML
    private ComboBox<String> comboPaper;

    @FXML
    private TableView<UserBean> tblCustomer;

    TableColumn<UserBean,String> cus,mob,addr,pap,pri,haw,curDate,area;
    
   
    

	@FXML
    void doFetchByArea(ActionEvent event) {
    	
    	//comboPaper.getEditor().setText("");
    	comboPaper.setValue("");
    	tblCustomer.getItems().clear();
    	//tblCustomer.getColumns().addAll(cus,mob,addr,pap,pri,haw,curDate,area);
    	
    	ObservableList<UserBean>ary=getRecordsByArea();
    	
    	tblCustomer.setItems(ary);
    }

    ObservableList<UserBean> getRecordsByArea()
    {
    	ObservableList<UserBean>ary=FXCollections.observableArrayList();
    	String ar=comboArea.getSelectionModel().getSelectedItem();
    	try 
    	{
			ps=con.prepareStatement("select * from customer where area=?");
			ps.setString(1, ar);
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
    			String area=rs.getString("area");
    			UserBean ref=new UserBean(name, mob, add, pap, pri, haw_name, date, area);
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
    void doFetchByPaper(ActionEvent event) {
    	
    	comboArea.setValue("");
    	//comboArea.getEditor().setText("");
    	tblCustomer.getItems().clear();
    	
    	
    	ObservableList<UserBean>ary=getRecordsByPaper();
    	
    	tblCustomer.setItems(ary);
    	
    	
    	
    	
    }

    ObservableList<UserBean> getRecordsByPaper()
    {
    	ObservableList<UserBean>ary=FXCollections.observableArrayList();
    	String pa=comboPaper.getSelectionModel().getSelectedItem();
    	try 
    	{
			ps=con.prepareStatement("select * from customer where paper like ?");
			ps.setString(1,'%'+pa+'%');
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
    			String area=rs.getString("area");
    			UserBean ref=new UserBean(name, mob, add, pap, pri, haw_name, date, area);
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
    void doShowAll(ActionEvent event) {

    	URL ur=getClass().getResource("audio1.wav");
    	AudioClip ac=new AudioClip(ur.toString());
    	ac.play();
    	
    	comboArea.getEditor().setText("");
    	comboPaper.getEditor().setText("");
    	tblCustomer.getItems().clear();
    //	tblCustomer.getColumns().addAll(cus,mob,addr,pap,pri,haw,curDate,area);
    	
    	ObservableList<UserBean>ary=getAllRecords();
    	
    	tblCustomer.setItems(ary);
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
    			String area=rs.getString("area");
    			UserBean ref=new UserBean(name, mob, add, pap, pri, haw_name, date, area);
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
    void toExcel(ActionEvent event) {

    	@SuppressWarnings("resource")
		XSSFWorkbook wb=new XSSFWorkbook();
    	XSSFSheet sheet=wb.createSheet("Customer Record");
    	XSSFRow header=sheet.createRow(0);
    	
    	header.createCell(0).setCellValue("customer_name");
        header.createCell(1).setCellValue("mobile");
        header.createCell(2).setCellValue("address");
        header.createCell(3).setCellValue("paper");
        header.createCell(4).setCellValue("price");
        header.createCell(5).setCellValue("hawker_name");
        header.createCell(6).setCellValue("cur_date");
        header.createCell(7).setCellValue("area");
        String pa=comboPaper.getSelectionModel().getSelectedItem();
        try 
    	{
			ps=con.prepareStatement("select * from customer where paper like ?");
			ps.setString(1,'%'+pa+'%');
			ResultSet rs=ps.executeQuery();
			int index=1;
			while(rs.next())
			{
				XSSFRow row=sheet.createRow(index);
				
				String name=rs.getString("customer_name");
				row.createCell(0).setCellValue(name);
    			String mob=rs.getString("mobile");
    			row.createCell(1).setCellValue(mob);
    			String add=rs.getString("address");
    			row.createCell(2).setCellValue(add);
    			String pap=rs.getString("paper");
    			row.createCell(3).setCellValue(pap);
    			String pri=rs.getString("price");
    			row.createCell(4).setCellValue(pri);
    			String haw_name=rs.getString("hawker_name");
    			row.createCell(5).setCellValue(haw_name);
    			String date=rs.getDate("cur_date").toString();
    			row.createCell(6).setCellValue(date);
    			String area=rs.getString("area");
    			row.createCell(7).setCellValue(area);
    			index++;
			}
			
			FileOutputStream fis = null;
			try {
				fis = new FileOutputStream("customerData.xlsx");
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				wb.write(fis);
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Alert alert=new Alert(AlertType.INFORMATION);
			alert.setTitle("Information dialogue");
			alert.setHeaderText(null);
			alert.setContentText("Exported in excel");
			alert.show();
			
    	}
    	catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * Workbook workbook = new HSSFWorkbook(); Sheet spreadsheet = (Sheet)
		 * workbook.createSheet("sample");
		 * 
		 * Row row = ((org.apache.poi.ss.usermodel.Sheet) spreadsheet).createRow(0);
		 * 
		 * for (int j = 0; j < tblCustomer.getColumns().size(); j++) {
		 * row.createCell(j).setCellValue(tblCustomer.getColumns().get(j).getText()); }
		 * 
		 * for (int i = 0; i < tblCustomer.getItems().size(); i++) { row =
		 * ((org.apache.poi.ss.usermodel.Sheet) spreadsheet).createRow(i + 1); for (int
		 * j = 0; j < tblCustomer.getColumns().size(); j++) {
		 * if(tblCustomer.getColumns().get(j).getCellData(i) != null) {
		 * row.createCell(j).setCellValue(tblCustomer.getColumns().get(j).getCellData(i)
		 * .toString()); } else { row.createCell(j).setCellValue(""); } } }
		 * 
		 * FileOutputStream fileOut = null; try { fileOut = new
		 * FileOutputStream("workbook.xls");
		 * 
		 * } catch (FileNotFoundException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } try { workbook.write(fileOut); fileOut.close(); }
		 * catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
         
//    	
    }
    
    void getAreaInCombo()
    {
 	   comboArea.getItems().clear();
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
 		
 		e.printStackTrace();
 	   }
 	   
 	   
    }
    
    void getPaperInCombo()
    {
    	comboPaper.getItems().clear();
    	try
    	{
			ps=con.prepareStatement("select paper from papers");
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				String s=rs.getString("paper");
				comboPaper.getItems().add(s);
			}
    	}
    	catch (SQLException e) {
			
			e.printStackTrace();
		}
    	
    }
    
    Connection con;
    PreparedStatement ps;
    @SuppressWarnings("unchecked")
	@FXML
    void initialize() {
    	
    	con=DataBaseConnectivity.getConnection();
    	
    	getAreaInCombo();
    	getPaperInCombo();
    	
    	
    	cus=new TableColumn<UserBean, String>("customer_name");
    	cus.setCellValueFactory(new PropertyValueFactory<>("customer_name"));
    	cus.setMinWidth(100);
    	
    	mob=new TableColumn<UserBean, String>("Mobile No.");
    	mob.setCellValueFactory(new PropertyValueFactory<>("mobile"));
    	mob.setMinWidth(100);
    	
    	addr=new TableColumn<UserBean, String>("address");
    	addr.setCellValueFactory(new PropertyValueFactory<>("address"));
    	addr.setMinWidth(100);
    	
    	pap=new TableColumn<UserBean, String>("paper");
    	pap.setCellValueFactory(new PropertyValueFactory<>("paper"));
    	pap.setMinWidth(100);
    	
    	pri=new TableColumn<UserBean, String>("price");
    	pri.setCellValueFactory(new PropertyValueFactory<>("price"));
    	pri.setMinWidth(100);
    	
    	haw=new TableColumn<UserBean, String>("hawker_name");
    	haw.setCellValueFactory(new PropertyValueFactory<>("hawker_name"));
    	haw.setMinWidth(100);
    	
    	curDate=new TableColumn<UserBean, String>("cur_date");
    	curDate.setCellValueFactory(new PropertyValueFactory<>("curDate"));
    	curDate.setMinWidth(100);
    	
    	area=new TableColumn<UserBean, String>("area");
    	area.setCellValueFactory(new PropertyValueFactory<>("area"));
    	area.setMinWidth(100);
    	
    	
    	tblCustomer.getColumns().addAll(cus,mob,addr,pap,pri,haw,curDate,area);
    	
    	
        assert comboArea != null : "fx:id=\"comboArea\" was not injected: check your FXML file 'CustomerShow.fxml'.";
        assert comboPaper != null : "fx:id=\"comboPaper\" was not injected: check your FXML file 'CustomerShow.fxml'.";
        assert tblCustomer != null : "fx:id=\"tblCustomer\" was not injected: check your FXML file 'CustomerShow.fxml'.";

    }
}
