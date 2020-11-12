package project.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SettingsMenuController implements Initializable{
    
    public static Stage settingsMenuStage = null;
    
    public Button exit,saveBtn;
    public TextField password;
    public Label statusMsg;
    private boolean fromAdminMenu=false;
    public String username;
    
    public void importUsername(String data) {
        username = data;
        System.out.println("You username is " + username);
    }
    
    public void exit(MouseEvent event) throws IOException{
        FXMLLoader fxmloader;
        Parent root = null;
        
        Stage userStage = new Stage();
        settingsMenuStage = userStage;
        
        if(fromAdminMenu){
            fxmloader = new FXMLLoader(getClass().getResource("/project/FXML/AdminMenu.fxml"));
            userStage.setTitle("Admin");            
            root = (Parent) fxmloader.load();
            
            AdminMenuController adminMenu = fxmloader.getController();
            adminMenu.exitedFromSettings(true);
            adminMenu.importUsername(username);
        } else {
            fxmloader = new FXMLLoader(getClass().getResource("/project/FXML/UserMenu.fxml"));
            userStage.setTitle("User");            
            root = (Parent) fxmloader.load();
            
            UserMenuController userMenu = fxmloader.getController();
            userMenu.exitedFromSettings(true);
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
    
    public void save(MouseEvent event) {
        if (password.getText().equals("")) {
            statusMsg.setText("Password field has no input!");
        }
        else {
            Connection conn = null;
            PreparedStatement ps = null;
            
            try {
                String sql = "update USERS set PASSWORD = ? where USERNAME = ? ";
                conn = DriverManager.getConnection("jdbc:derby://localhost:1527/PalyulDatabaseProject","root","root");
                ps = conn.prepareStatement(sql);
                ps.setString(1, password.getText());
                ps.setString(2, username);
                ps.execute();
                password.clear();
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
            
            statusMsg.setText("Password successfully changed!");
            password.setText("");
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
    
    public void closeScreen(MouseEvent event){
        System.exit(0);
    }
    
    public void enableTraversability(){
        password.setFocusTraversable(true);
        
        clearStatusMessage();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        exit.setPickOnBounds(true);
        exit.setFocusTraversable(false);
        saveBtn.setFocusTraversable(false);
        password.setFocusTraversable(false);
    }
    
    
}
