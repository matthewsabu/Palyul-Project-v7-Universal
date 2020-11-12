package project.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ViewEntriesController implements Initializable{
    
    public static Stage viewEntriesStage = null;
    public Button exit,searchBtn;
    public Label statusMsg;
    private boolean fromAdminMenu=false;
    public TextField searchTF;
    public ChoiceBox searchEventPresents;
    public String user;    
    
    public TableView<SearchEntry> entriesTableView;    
    public TableColumn<SearchEntry, String> ornm,nm,dtype,chknm,evnt,nts,amnt;
    
    public void importUsername(String data) {
        user = data;
        System.out.println("You username is " + user);
    }
    
    public void exit(MouseEvent event) throws IOException{
        FXMLLoader fxmloader;
        Parent root = null;
        
        Stage userStage = new Stage();
        viewEntriesStage = userStage;
        
        if(fromAdminMenu){
            fxmloader = new FXMLLoader(getClass().getResource("/project/FXML/AdminMenu.fxml"));
            userStage.setTitle("Admin");            
            root = (Parent) fxmloader.load();
            
            AdminMenuController adminMenu = fxmloader.getController();
            adminMenu.importUsername(user);
            adminMenu.exitedFromViewEntries(true);
        } else {
            fxmloader = new FXMLLoader(getClass().getResource("/project/FXML/UserMenu.fxml"));
            userStage.setTitle("User");            
            root = (Parent) fxmloader.load();
            
            UserMenuController userMenu = fxmloader.getController();
            userMenu.importUsername(user);
            userMenu.exitedFromViewEntries(true);
        } 
        
        Scene userScene = new Scene(root);
        userStage.setScene(userScene);
        
        Image icon = new Image("pics/gold ed logo2.png");
        userStage.getIcons().add(icon);
        
        userStage.setTitle("User Login");
        userStage.initStyle(StageStyle.UNDECORATED);
        userStage.show();
        
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }
    
    public void searchEntry(MouseEvent event) {
         String selectedsearch = "";
         ObservableList<SearchEntry> entries = FXCollections.observableArrayList();
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        selectedsearch = searchEventPresents.getValue().toString();
        
        if (selectedsearch == ""){
              statusMsg.setText("Please input search category");
            
        }
        else {
       
        try{
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/PalyulDatabaseProject","root","root");
            ps = conn.prepareStatement("select * from ENTRIES WHERE " + selectedsearch + " = ?");
            String searchvalue = searchTF.getText();
            ps.setString(1,searchvalue);
            rs = ps.executeQuery();
            
            while(rs.next()) {
               SearchEntry temp = new SearchEntry();
                temp.setOrnum(rs.getString("ORNUM"));
                temp.setName(rs.getString("NAME"));
                temp.setDtype(rs.getString("DONATIONTYPE"));
                temp.setChecknm(rs.getString("CHECKNO"));
                temp.setEvent(rs.getString("EVENT"));
                temp.setNotes(rs.getString("NOTES"));
                temp.setAmount(rs.getString("AMOUNT"));
               
                entries.add(temp);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                conn.close();
                ps.close();
                rs.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        
        ornm.setCellValueFactory(new PropertyValueFactory<SearchEntry, String>("ornum"));
        nm.setCellValueFactory(new PropertyValueFactory<SearchEntry, String>("name"));
        dtype.setCellValueFactory(new PropertyValueFactory<SearchEntry, String>("dtype"));
        chknm.setCellValueFactory(new PropertyValueFactory<SearchEntry, String>("checknm"));
        evnt.setCellValueFactory(new PropertyValueFactory<SearchEntry, String>("event"));
        nts.setCellValueFactory(new PropertyValueFactory<SearchEntry, String>("notes"));
        amnt.setCellValueFactory(new PropertyValueFactory<SearchEntry, String>("amount"));
        
        
        entriesTableView.setItems(entries);
        statusMsg.setText("Entry successfully searched!");
        }
    }
    
    public void clearStatusMessage(){
        statusMsg.setText("");
    }
    
    public void enteredFromAdminMenu(boolean entered){
        this.fromAdminMenu = entered;
    }
    
    public void minimizeScreen(MouseEvent event){
        if(fromAdminMenu) AdminMenuController.adminStage.setIconified(true);
        else UserMenuController.userStage.setIconified(true);
    }
    
    public void enableTraversability(){
        searchTF.setFocusTraversable(true);
        
        clearStatusMessage();
    }
    
    public void closeScreen(MouseEvent event){
        System.exit(0);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        exit.setPickOnBounds(true);
        exit.setFocusTraversable(false);
        searchBtn.setFocusTraversable(false);
        searchEventPresents.setFocusTraversable(false);
        entriesTableView.setFocusTraversable(false);
        searchEventPresents.setItems(FXCollections.observableArrayList("ORNUM","NAME","EVENT","AMOUNT"));
    }
    
    
}
