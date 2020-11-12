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

public class AddEntryController implements Initializable{
    
    public static Stage addEntryStage = null;
    
    public Button exit,addEntryBtn;
    public TextField OR,name,amount,checkNo,newEvent;
    public TextArea notes;
    public RadioButton cashRadio,checkRadio;
    public Label statusMsg;
    private String donationType = "Check";
    private boolean fromAdminMenu=false;
    public static String username;
    public ComboBox events;    
    
    public TableView<PreviewEntry> partialEntriesTableView;    
    public TableColumn<PreviewEntry, String> ornum,amt,evt;
    
    public void importUsername(String data) {
        username = data;
        System.out.println(username);
    }
    
    public void exit(MouseEvent event) throws IOException{
        FXMLLoader fxmloader;
        Parent root = null;
        
        Stage userStage = new Stage();
        addEntryStage = userStage;
        
        if(fromAdminMenu){
            fxmloader = new FXMLLoader(getClass().getResource("/project/FXML/AdminMenu.fxml"));
            userStage.setTitle("Admin");            
            root = (Parent) fxmloader.load();
            
            AdminMenuController adminMenu = fxmloader.getController();
            adminMenu.exitedFromAddEntry(true);
            adminMenu.importUsername(username);
        } else {
            fxmloader = new FXMLLoader(getClass().getResource("/project/FXML/UserMenu.fxml"));
            userStage.setTitle("User");            
            root = (Parent) fxmloader.load();
            
            UserMenuController userMenu = fxmloader.getController();
            userMenu.exitedFromAddEntry(true);
            userMenu.importUsername(username);
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
    
    public void showData() {
        ObservableList<PreviewEntry> entries = FXCollections.observableArrayList();
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try{
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/PalyulDatabaseProject","root","root");
            ps = conn.prepareStatement("select * from ENTRIES");
            rs = ps.executeQuery();
            
            while(rs.next()) {
                PreviewEntry temp = new PreviewEntry();
                temp.setOrnum(rs.getString("ORNUM"));
                temp.setAmount(rs.getString("AMOUNT"));
                temp.setEvent(rs.getString("EVENT"));
                System.out.println(temp.getOrnum());
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
        
        System.out.println(entries.get(0).getAmount());
        System.out.println(entries.get(0).getOrnum());
        ornum.setCellValueFactory(new PropertyValueFactory<PreviewEntry, String>("ornum"));
        amt.setCellValueFactory(new PropertyValueFactory<PreviewEntry, String>("amount"));
        evt.setCellValueFactory(new PropertyValueFactory<PreviewEntry, String>("event"));
        
        partialEntriesTableView.setItems(entries);
    }
    
    public void addEntry(MouseEvent event) {
        if(OR.getText().equals("") || name.getText().equals("") || amount.getText().equals("") || newEvent.getText().equals("")) {
            statusMsg.setText("Enter all required informations");
        }
        else {
            Connection conn = null;
            PreparedStatement ps = null;
            
            try {
                String sql = "Insert into ENTRIES (ORNUM, NAME, AMOUNT, DONATIONTYPE, CHECKNO, EVENT, CREATED, LASTEDIT, NOTES) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
                ps.setString(7, username);
                ps.setString(8, "");
                ps.setString(9, notes.getText());
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
            
            showData();
            statusMsg.setText("Entry successfully added!");
        }
        
    }
    
    public void cashOption(){
        this.donationType = "Cash";
        statusMsg.setText("Cash Selected");
        checkNo.setDisable(true);
    }
    
    public void checkOption(){
        this.donationType = "Check";
        statusMsg.setText("Check Selected");
        checkNo.setDisable(false);
    }
    
    public void clearStatusMessage(){
        statusMsg.setText("");
    }    
    
    public void enteredFromAdminMenu(boolean entered){
        this.fromAdminMenu = entered;
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
    
    
    public void minimizeScreen(MouseEvent event){
        if(fromAdminMenu) AdminMenuController.adminStage.setIconified(true);
        else UserMenuController.userStage.setIconified(true);
    }
    
    public void changeEvent()
    {
        if(((String) events.getValue()).equals("New Event"))
        {
            newEvent.setDisable(false);
            newEvent.setFocusTraversable(true);
            newEvent.clear();
        }
        else
        {
            newEvent.setDisable(true);
            newEvent.setText((String) events.getValue());
        }
            
    }
    
    public void closeScreen(MouseEvent event){
        System.exit(0);
    }
    
    public void enableTraversability(){
        OR.setFocusTraversable(true);
        name.setFocusTraversable(true); 
        amount.setFocusTraversable(true);
        checkNo.setFocusTraversable(true);
        notes.setFocusTraversable(true);
        newEvent.setFocusTraversable(true);
        
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
        addEntryBtn.setFocusTraversable(false);
        newEvent.setFocusTraversable(false);
        events.setFocusTraversable(false);
        partialEntriesTableView.setFocusTraversable(false);        
        choices();
        showData();
        
    }

    private void choices() {
        events.getItems().addAll("New Event","CNY","HW","DOD", "CMAS","EAS");
    }
}
