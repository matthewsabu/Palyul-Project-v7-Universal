package project.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import static project.Controllers.AddEntryController.username;

public class EditEntryController implements Initializable{
    
    public static Stage editEntryStage = null;
    
    public Button exit,editEntryBtn,deleteEntryBtn,searchBtn;
    public TextField OR,name,amount,checkNo,newEvent,searchTF;
    public TextArea notes;
    public RadioButton cashRadio,checkRadio;
    public Label statusMsg;
    private String donationType = "Cash";
    private boolean fromAdminMenu=false,permissionGranted=false,isEdit=false, isDelete=false;
    private FXMLLoader fxmloader;
    private Stage userStage;
    public ChoiceBox searchEventPresents;
    public SearchEntry selectedEntry = new SearchEntry();
    public String selectedsearch ="";
    public ComboBox events;
    
        @FXML
    public TableView<SearchEntry> entriesTableView;
    @FXML
    public TableColumn<SearchEntry, String> ornm;
    @FXML
    public TableColumn<SearchEntry, String> nm;
    @FXML
    public TableColumn<SearchEntry, String> dtype;
      @FXML
    public TableColumn<SearchEntry, String> chknm;
    @FXML
    public TableColumn<SearchEntry, String> evnt;
    @FXML
    public TableColumn<SearchEntry, String> nts;
    @FXML
    public TableColumn<SearchEntry, String> amnt;
    
    public void importUsername(String data) {
        username = data;
        System.out.println("You username is " + username);
    }
    
    public void exit(MouseEvent event) throws IOException{
        FXMLLoader fxmloader1;
        Parent root = null;
        
        Stage userStage = new Stage();
        editEntryStage = userStage;
        
        if(fromAdminMenu){
            fxmloader1 = new FXMLLoader(getClass().getResource("/project/FXML/AdminMenu.fxml"));
            userStage.setTitle("Admin");            
            root = (Parent) fxmloader1.load();
            
            AdminMenuController adminMenu = fxmloader1.getController();
            adminMenu.importUsername(username);
            adminMenu.exitedFromEditEntry(true);
        } else {
            fxmloader1 = new FXMLLoader(getClass().getResource("/project/FXML/UserMenu.fxml"));
            userStage.setTitle("User");            
            root = (Parent) fxmloader1.load();
            
            UserMenuController userMenu = fxmloader1.getController();
            userMenu.importUsername(username);
            userMenu.exitedFromEditEntry(true);
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
    
    public void displayEntryData(){
         
         selectedEntry = entriesTableView.getSelectionModel().getSelectedItem();
         OR.setText(selectedEntry.getOrnum());
         name.setText(selectedEntry.getName());
         amount.setText(selectedEntry.getAmount());
         checkNo.setText(selectedEntry.getChecknm());
         newEvent.setText(selectedEntry.getEvent());
         notes.setText(selectedEntry.getNotes());
         System.out.println(selectedEntry.getDtype());
         if (selectedEntry.getDtype().toString().toUpperCase().equals("CASH")){
             cashRadio.setSelected(true);
             checkRadio.setSelected(false);
             checkNo.setDisable(true);
         }
         else{
               cashRadio.setSelected(false);
               checkRadio.setSelected(true);
         }
         }
       
    
    public void editEntry(MouseEvent event) throws IOException {    
        
        if (entriesTableView.getSelectionModel().getSelectedItem() == null){
            statusMsg.setText("No Entry Selected");
        }        
        else {                 
            if(fromAdminMenu) {

               Alert confirmEdit = new Alert(AlertType.CONFIRMATION);
               Alert editResult = new Alert(AlertType.INFORMATION);
               confirmEdit.setContentText("Are you sure you want to edit the information?");
               Optional<ButtonType> result = confirmEdit.showAndWait();

               if (result.get()== ButtonType.OK){

                Connection conn = null;
                PreparedStatement ps = null;

                try {
                    String sql = "update ENTRIES set ORNUM = ?, NAME = ?, AMOUNT = ?, DONATIONTYPE = ?, CHECKNO = ?, EVENT = ?, NOTES = ? where ORNUM = ? ";
                    conn = DriverManager.getConnection("jdbc:derby://localhost:1527/PalyulDatabaseProject","root","root");
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, OR.getText());
                    ps.setString(2, name.getText());
                    ps.setString(3, amount.getText());
                    if(cashRadio.isSelected()) {
                        ps.setString(4, "Cash");
                        ps.setString(5, "N/A");
                    }
                    else {
                        ps.setString(4, "Check");
                        ps.setString(5, checkNo.getText());
                    }
                    ps.setString(6, newEvent.getText());
                    ps.setString(7, notes.getText());
                    ps.setString(8,selectedEntry.getOrnum());
                    ps.execute();

                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    finally {
                        try {
                            conn.close();
                            ps.close();
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                     editResult.setContentText("EDIT SUCCESSFUL");
                     editResult.show();
                     statusMsg.setText("Entry successfully edited!");
                     displaySearch(selectedsearch);
               }

               else {
                   editResult.setContentText("Nothing was Edited");
                   editResult.show();
               }
            }
            else {
                FXMLLoader fxmloader = new FXMLLoader(getClass().getResource("/project/FXML/AdminPrompt.fxml"));
                Parent root = (Parent) fxmloader.load();

                userStage = new Stage();

                AdminPromptController adminPrompt = fxmloader.getController();
                adminPrompt.passVariables(this.fxmloader,permissionGranted);

                Scene userScene = new Scene(root);        
                userStage.setScene(userScene);

                Image icon = new Image("/project/FXML/icons/alert.png");
                userStage.getIcons().add(icon);

                userStage.setTitle("Admin Prompt");
                userStage.initStyle(StageStyle.UTILITY);
                userStage.show();    

                isEdit = true;
                isDelete = false;
    //            statusMsg.setText("Entry successfully edited!");
            }
        }
    }
    
  
    
    public void deleteEntry(MouseEvent event) throws IOException {
        if (entriesTableView.getSelectionModel().getSelectedItem() == null){
            statusMsg.setText("No Entry Selected");
        }        
        else {
            if(fromAdminMenu) {
                Alert confirmEdit = new Alert(AlertType.CONFIRMATION);
                Alert editResult = new Alert(AlertType.INFORMATION);
                confirmEdit.setContentText("Are you sure you want to DELETE this ENTRY?");
                Optional<ButtonType> result = confirmEdit.showAndWait();

                if (result.get()== ButtonType.OK){  

                    Connection conn = null;
                    PreparedStatement ps = null;

                    try {
                        String sql = "DELETE FROM ENTRIES where ORNUM = ? ";
                        conn = DriverManager.getConnection("jdbc:derby://localhost:1527/PalyulDatabaseProject","root","root");
                        ps = conn.prepareStatement(sql);
                        ps.setString(1, OR.getText());
                        ps.execute();

                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    finally {
                        try {
                            conn.close();
                            ps.close();
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                     editResult.setContentText("DELETE SUCCESSFUL");
                     editResult.show();
                     statusMsg.setText("Entry successfully deleted!");
                     displaySearch(selectedsearch);
                }

                else {
                    editResult.setContentText("Nothing was deleted");
                    editResult.show();
                }                 
            }           
            else {
                FXMLLoader fxmloader = new FXMLLoader(getClass().getResource("/project/FXML/AdminPrompt.fxml"));
                Parent root = (Parent) fxmloader.load();

                userStage = new Stage();

                AdminPromptController adminPrompt = fxmloader.getController();
                adminPrompt.passVariables(this.fxmloader,permissionGranted);

                Scene userScene = new Scene(root);        
                userStage.setScene(userScene);

                Image icon = new Image("/project/FXML/icons/alert.png");
                userStage.getIcons().add(icon);

                userStage.setTitle("Admin Prompt");
                userStage.initStyle(StageStyle.UTILITY);
                userStage.show();    

                isEdit = false;
                isDelete = true;
    //            statusMsg.setText("Entry successfully deleted!");
            }
        }
    }
    
    public void displaySearch(String selectedcategory){
         ObservableList<SearchEntry> entries = FXCollections.observableArrayList();
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
       
        try{
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/PalyulDatabaseProject","root","root");
            ps = conn.prepareStatement("select * from ENTRIES WHERE " + selectedcategory + " = ?");
            String searchvalue = searchTF.getText();
            if (searchTF.getText() == ""){
                 statusMsg.setText("No Search Input");
            }
            else {
            ps.setString(1,searchvalue);
            rs = ps.executeQuery();
            }
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
    
    public void searchEntry(MouseEvent event) {
        selectedsearch = searchEventPresents.getValue().toString();
        if(searchEventPresents == null){
            statusMsg.setText("Please select a category");
        }
        else {
        displaySearch(selectedsearch);
        }
    }
    
    public void cashOption(){
        this.donationType = "Cash";
        checkRadio.setSelected(false);
        statusMsg.setText("Cash Selected");
        checkNo.setDisable(true);
    }
    
    public void checkOption(){
        this.donationType = "Check";
        cashRadio.setSelected(false);
        statusMsg.setText("Check Selected");
        checkNo.setDisable(false);
    }
    
    public void clearStatusMessage(){
        statusMsg.setText("");
    }
    
    public void OKstatus(boolean status){
        this.permissionGranted = status;
        
        if(permissionGranted && isEdit) {
//            Alert confirmEdit = new Alert(AlertType.CONFIRMATION);
            Alert editResult = new Alert(AlertType.INFORMATION);
//            confirmEdit.setContentText("Are you sure you want to edit the information?");
//            Optional<ButtonType> result = confirmEdit.showAndWait();
//
//            if (result.get()== ButtonType.OK){

             Connection conn = null;
             PreparedStatement ps = null;

             try {
                 String sql = "update ENTRIES set ORNUM = ?, NAME = ?, AMOUNT = ?, DONATIONTYPE = ?, CHECKNO = ?, EVENT = ?, NOTES = ? where ORNUM = ? ";
                 conn = DriverManager.getConnection("jdbc:derby://localhost:1527/PalyulDatabaseProject","root","root");
                 ps = conn.prepareStatement(sql);
                 ps.setString(1, OR.getText());
                 ps.setString(2, name.getText());
                 ps.setString(3, amount.getText());
                 if(cashRadio.isSelected()) {
                     ps.setString(4, "Cash");
                     ps.setString(5, "N/A");
                 }
                 else {
                     ps.setString(4, "Check");
                     ps.setString(5, checkNo.getText());
                 }
                 ps.setString(6, newEvent.getText());
                 ps.setString(7, notes.getText());
                 ps.setString(8,selectedEntry.getOrnum());
                 ps.execute();
                 clearText();
                 }
                 catch (Exception e) {
                     e.printStackTrace();
                 }
                 finally {
                     try {
                         conn.close();
                         ps.close();
                     }
                     catch (Exception e) {
                         e.printStackTrace();
                     }
                 }

                  editResult.setContentText("EDIT SUCCESSFUL");
                  editResult.show();
                  statusMsg.setText("Entry successfully edited!");
                  displaySearch(selectedsearch);
//            }
//
//                  else {
//                    editResult.setContentText("Nothing was Edited");
//                    editResult.show();
//                    }
                statusMsg.setText("Entry successfully edited!");
                isEdit = false;
        } 
        
        if(permissionGranted && isDelete) {
            Alert confirmEdit = new Alert(AlertType.CONFIRMATION);
            Alert editResult = new Alert(AlertType.INFORMATION);
            confirmEdit.setContentText("Are you sure you want to DELETE this ENTRY?");
            Optional<ButtonType> result = confirmEdit.showAndWait();
           
            if (result.get()== ButtonType.OK) {
               
            Connection conn = null;
            PreparedStatement ps = null;
            
            try {
                String sql = "DELETE FROM ENTRIES where ORNUM = ? ";
                conn = DriverManager.getConnection("jdbc:derby://localhost:1527/PalyulDatabaseProject","root","root");
                ps = conn.prepareStatement(sql);
                ps.setString(1, OR.getText());
                ps.execute();
                clearText();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    try {
                        conn.close();
                        ps.close();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                 
                 editResult.setContentText("DELETE SUCCESSFUL");
                 editResult.show();
                 statusMsg.setText("Entry successfully deleted!");
                 displaySearch(selectedsearch);
           }
      
                 else {
          editResult.setContentText("Nothing was deleted");
                 editResult.show();
        }
            statusMsg.setText("Entry successfully deleted!");
            isEdit = false;
        }
    } 
    
    public void passFxml(FXMLLoader fxmloader){
        this.fxmloader = fxmloader;
    }
    
    public void enteredFromAdminMenu(boolean entered){
        this.fromAdminMenu = entered;
    }
    
    public void minimizeScreen(MouseEvent event){
        if(fromAdminMenu) AdminMenuController.adminStage.setIconified(true);
        else UserMenuController.userStage.setIconified(true);
    }
    private void clearText()
    {
        OR.clear();
        name.clear();
        amount.clear();
        checkNo.clear();
        newEvent.clear();
        notes.clear();
    }
    public void closeScreen(MouseEvent event){
        System.exit(0);
    }
    public void changeEvent()
    {
        if(((String) events.getValue()).equals("New Event"))
        {
            newEvent.setDisable(false);
            newEvent.clear();
        }
        else
        {
            newEvent.setDisable(true);
            newEvent.setText((String) events.getValue());
        }
            
    }
    
    public void enableTraversability(){
        OR.setFocusTraversable(true);
        name.setFocusTraversable(true); 
        amount.setFocusTraversable(true);
        checkNo.setFocusTraversable(true);
        notes.setFocusTraversable(true);
        newEvent.setFocusTraversable(true);
        searchTF.setFocusTraversable(true);
        
        clearStatusMessage();
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        exit.setPickOnBounds(true);
        exit.setFocusTraversable(false);
        OR.setFocusTraversable(false);
        name.setFocusTraversable(false); 
        amount.setFocusTraversable(false);
        checkNo.setFocusTraversable(false);
        notes.setFocusTraversable(false);
        cashRadio.setFocusTraversable(false);
        checkRadio.setFocusTraversable(false);
        newEvent.setFocusTraversable(false);
        editEntryBtn.setFocusTraversable(false);
        deleteEntryBtn.setFocusTraversable(false);
        searchBtn.setFocusTraversable(false);
        searchTF.setFocusTraversable(false);
        searchEventPresents.setFocusTraversable(false);
        entriesTableView.setFocusTraversable(false);
        searchEventPresents.setItems(FXCollections.observableArrayList("ORNUM","NAME","EVENT","AMOUNT"));
        choices();
    }
    private void choices() {
        events.getItems().addAll("New Event","CNY","HW","DOD", "CMAS","EAS");
    }
}
