package project.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AdminMenuController implements Initializable{
    
    public static Stage adminStage = null;
    
    public static String username;
    
    public Button addEntryBtn,editEntryBtn,createUserBtn,manageUsersBtn,viewEntriesBtn,settingsBtn,signOutBtn;
    private boolean fromAddEntry=false,fromEditEntry=false,fromCreateUser=false,fromManageUsers=false,fromViewEntries=false,
            fromSettings=false,isAdmin=false;
    public Label message;
    public ImageView imageDisplay;
    private int prevImageNumber=0,nextImageNumber=0;
    
    public void importUsername(String data) {
        username = data;
        message.setText("Welcome, " + username + "!");
        System.out.println("You username is " + username);
    }
    
    public void signOut(MouseEvent event) throws IOException{
        FXMLLoader fxmloader = new FXMLLoader(getClass().getResource("/project/FXML/Login.fxml"));
        Parent root = (Parent) fxmloader.load();
        
        Stage userStage = new Stage();
        adminStage = userStage;        
                
        LoginController login = fxmloader.getController();
        login.isSignedOut(true);
        
        Scene userScene = new Scene(root);
        userStage.setScene(userScene);
        
        Image icon = new Image("pics/gold ed logo2.png");
        userStage.getIcons().add(icon);
        
        userStage.initStyle(StageStyle.UNDECORATED);
        userStage.show();
        
        Stage stage = (Stage) signOutBtn.getScene().getWindow();
        stage.close();
        
    }  
    
    public void addEntry(MouseEvent event) throws IOException{
        FXMLLoader fxmloader = new FXMLLoader(getClass().getResource("/project/FXML/AddEntry.fxml"));
        Parent root = (Parent) fxmloader.load();
        
        Stage userStage = new Stage();
        adminStage = userStage;
        
        AddEntryController addEntry = fxmloader.getController();
        addEntry.enteredFromAdminMenu(true);
        addEntry.importUsername(username);
        
        Scene userScene = new Scene(root);        
        userStage.setScene(userScene);
        
        Image icon = new Image("pics/gold ed logo2.png");
        userStage.getIcons().add(icon);
        
        userStage.setTitle("Add Entry");
        userStage.initStyle(StageStyle.UNDECORATED);
        userStage.show();
        
        Stage stage = (Stage) addEntryBtn.getScene().getWindow();
        stage.close();
    }
    
    public void editEntry(MouseEvent event) throws IOException{
        FXMLLoader fxmloader = new FXMLLoader(getClass().getResource("/project/FXML/EditEntry.fxml"));
        Parent root = (Parent) fxmloader.load();
        
        Stage userStage = new Stage();
        adminStage = userStage;
        
        EditEntryController editEntry = fxmloader.getController();
        editEntry.importUsername(username);
        editEntry.enteredFromAdminMenu(true);
        
        Scene userScene = new Scene(root);        
        userStage.setScene(userScene);
        
        Image icon = new Image("pics/gold ed logo2.png");
        userStage.getIcons().add(icon);
        
        userStage.setTitle("Edit Entry");
        userStage.initStyle(StageStyle.UNDECORATED);
        userStage.show();
        
        Stage stage = (Stage) editEntryBtn.getScene().getWindow();
        stage.close();
    }
    
    public void createUser(MouseEvent event) throws IOException{
        FXMLLoader fxmloader = new FXMLLoader(getClass().getResource("/project/FXML/CreateUser.fxml"));
        Parent root = (Parent) fxmloader.load();
        
        Stage userStage = new Stage();
        adminStage = userStage;
        
        CreateUserController createUser = fxmloader.getController();
        createUser.importUsername(username);
        
        Scene userScene = new Scene(root);        
        userStage.setScene(userScene);
        
        Image icon = new Image("pics/gold ed logo2.png");
        userStage.getIcons().add(icon);
        
        userStage.setTitle("Create User");
        userStage.initStyle(StageStyle.UNDECORATED);
        userStage.show();
        
        Stage stage = (Stage) createUserBtn.getScene().getWindow();
        stage.close();
    }
    
    public void manageUsers(MouseEvent event) throws IOException{
        FXMLLoader fxmloader = new FXMLLoader(getClass().getResource("/project/FXML/ManageUsers.fxml"));
        Parent root = (Parent) fxmloader.load();
        
        Stage userStage = new Stage();
        adminStage = userStage;
        
        ManageUsersController manageUser = fxmloader.getController();
        manageUser.importUsername(username);
        
        Scene userScene = new Scene(root);        
        userStage.setScene(userScene);
        
        Image icon = new Image("pics/gold ed logo2.png");
        userStage.getIcons().add(icon);
        
        userStage.setTitle("Manage Users");
        userStage.initStyle(StageStyle.UNDECORATED);
        userStage.show();
        
        Stage stage = (Stage) manageUsersBtn.getScene().getWindow();
        stage.close();
    }
    
    public void viewEntries(MouseEvent event) throws IOException{
        FXMLLoader fxmloader = new FXMLLoader(getClass().getResource("/project/FXML/ViewEntries.fxml"));
        Parent root = (Parent) fxmloader.load();
        
        Stage userStage = new Stage();
        adminStage = userStage;
        
        ViewEntriesController viewEntries = fxmloader.getController();
        viewEntries.importUsername(username);
        viewEntries.enteredFromAdminMenu(true);
        
        Scene userScene = new Scene(root);        
        userStage.setScene(userScene);
        
        Image icon = new Image("pics/gold ed logo2.png");
        userStage.getIcons().add(icon);
        
        userStage.setTitle("View Entries");
        userStage.initStyle(StageStyle.UNDECORATED);
        userStage.show();
        
        Stage stage = (Stage) viewEntriesBtn.getScene().getWindow();
        stage.close();
    }
    
    public void settingsMenu(MouseEvent event) throws IOException{
        FXMLLoader fxmloader = new FXMLLoader(getClass().getResource("/project/FXML/SettingsMenu.fxml"));
        Parent root = (Parent) fxmloader.load();
        
        Stage userStage = new Stage();
        adminStage = userStage;
        
        SettingsMenuController settingsMenu = fxmloader.getController();
        settingsMenu.importUsername(username);
        settingsMenu.enteredFromAdminMenu(true);
        
        Scene userScene = new Scene(root);        
        userStage.setScene(userScene);
        
        Image icon = new Image("pics/gold ed logo2.png");
        userStage.getIcons().add(icon);
        
        userStage.setTitle("Settings Menu");
        userStage.initStyle(StageStyle.UNDECORATED);
        userStage.show();
        
        Stage stage = (Stage) settingsBtn.getScene().getWindow();
        stage.close();
    }
    
    public void previous(){
        prevImageNumber--;
        if(prevImageNumber==-1){
            imageDisplay.setImage(new Image("project/Controllers/menuPics/bg4.jpg"));
            imageDisplay.setFitWidth(550);
            imageDisplay.setFitHeight(345);
            imageDisplay.setPreserveRatio(false);
            imageDisplay.setSmooth(true);  
        } else if(prevImageNumber==-2){
            imageDisplay.setImage(new Image("project/Controllers/menuPics/bg3.jpg"));
            imageDisplay.setFitWidth(550);
            imageDisplay.setFitHeight(345);
            imageDisplay.setPreserveRatio(false);
            imageDisplay.setSmooth(true);
            prevImageNumber=1;
        } else {
            imageDisplay.setImage(new Image("project/Controllers/menuPics/bg2.jpg"));
            imageDisplay.setFitWidth(550);
            imageDisplay.setFitHeight(345);
            imageDisplay.setPreserveRatio(false);
            imageDisplay.setSmooth(true);  
        } //continue if else to add more pictures but in reverse order from function next()   
    }
    
    public void next(){
        nextImageNumber++;
        if(nextImageNumber==1){
            imageDisplay.setImage(new Image("project/Controllers/menuPics/bg3.jpg"));
            imageDisplay.setFitWidth(550);
            imageDisplay.setFitHeight(345);
            imageDisplay.setPreserveRatio(false);
            imageDisplay.setSmooth(true);  
        } else if(nextImageNumber==2){
            imageDisplay.setImage(new Image("project/Controllers/menuPics/bg4.jpg"));
            imageDisplay.setFitWidth(550);
            imageDisplay.setFitHeight(345);
            imageDisplay.setPreserveRatio(false);
            imageDisplay.setSmooth(true);
            nextImageNumber=-1;
        } else {
            imageDisplay.setImage(new Image("project/Controllers/menuPics/bg2.jpg"));
            imageDisplay.setFitWidth(550);
            imageDisplay.setFitHeight(345);
            imageDisplay.setPreserveRatio(false);
            imageDisplay.setSmooth(true);  
        } //continue if else to add more pictures               
    }
    
    public void exitedFromAddEntry(boolean exited){
        this.fromAddEntry = exited;
    }
    
    public void exitedFromEditEntry(boolean exited){
        this.fromEditEntry = exited;
    }
    
    public void exitedFromCreateUser(boolean exited){
        this.fromCreateUser = exited;
    }
    
    public void exitedFromManageUsers(boolean exited){
        this.fromManageUsers = exited;
    }
    
    public void exitedFromViewEntries(boolean exited){
        this.fromViewEntries = exited;
    }
    
    public void exitedFromSettings(boolean exited){
        this.fromSettings = exited;
    }
    
    public void setAdminStatus(boolean condition){
        this.isAdmin = condition;
    }
    
    public void minimizeScreen(MouseEvent event){
        if(fromAddEntry) {
            AddEntryController.addEntryStage.setIconified(true);
            fromAddEntry = false;
        }
        else if(fromEditEntry){
            EditEntryController.editEntryStage.setIconified(true);
            fromEditEntry = false;
        }
        else if(fromCreateUser){
            CreateUserController.createUserStage.setIconified(true);
            fromCreateUser = false;
        }
        else if(fromManageUsers){
            ManageUsersController.manageUsersStage.setIconified(true);
            fromManageUsers = false;
        }
        else if(fromViewEntries){
            ViewEntriesController.viewEntriesStage.setIconified(true);
            fromViewEntries = false;
        }
        else if(fromSettings) {
            SettingsMenuController.settingsMenuStage.setIconified(true);
            fromSettings = false;
        }
        else LoginController.loginStage.setIconified(true);
    }
    
    public void closeScreen(MouseEvent event){
        System.exit(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addEntryBtn.setFocusTraversable(false);
        editEntryBtn.setFocusTraversable(false);
        createUserBtn.setFocusTraversable(false);
        manageUsersBtn.setFocusTraversable(false);
        viewEntriesBtn.setFocusTraversable(false);
        settingsBtn.setFocusTraversable(false);
        signOutBtn.setFocusTraversable(false);
        signOutBtn.setPickOnBounds(true);
    }
    
    
    
}
