package project.Controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ManageUsersController implements Initializable{
 
    public static Stage manageUsersStage = null;
    
    public Button exit,imageDp,editUserBtn,deleteUserBtn;
    public TextField username,password;
    public RadioButton userRadio,adminRadio; 
    public Label statusMsg;
    private String userType = "User";
    public PreviewUsers selectedUser = new PreviewUsers();
    public String user;
    
       @FXML
    private TableView<PreviewUsers> existingUsersTableView;
    @FXML
    private TableColumn<PreviewUsers, String> un;
    @FXML
    private TableColumn<PreviewUsers, String> pw;
    @FXML
    private TableColumn<PreviewUsers, String> acs;
    
    public void importUsername(String data) {
        user = data;
        System.out.println("You username is " + user);
    }
    
    public void exit(MouseEvent event) throws IOException{
        FXMLLoader fxmloader = new FXMLLoader(getClass().getResource("/project/FXML/AdminMenu.fxml"));
        Parent root = (Parent) fxmloader.load();
        
        Stage userStage = new Stage();
        manageUsersStage = userStage;
        
        AdminMenuController adminMenu = fxmloader.getController();
        adminMenu.importUsername(user);
        adminMenu.exitedFromManageUsers(true);
        
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
    
    public void setDp(MouseEvent event){
        FileChooser fileChooser = new FileChooser();
        
        fileChooser.setTitle("Choose a Display Picture");
        File selectedFile = fileChooser.showOpenDialog(null);
        
        String file = selectedFile.getPath();
        Path path = Paths.get(file);
        
        if(selectedFile != null){
            imageDp.setStyle("-fx-background-image: url('/pics/" + path.getFileName() + "')");
            statusMsg.setText("Display picture changed!");
        } /*else {  //doesn't work lmao
            statusMsg.setText("Display picture unchanged!");
        }*/
        
    }
    
    public void displayUserInfo(){
         ObservableList<PreviewUsers> users = FXCollections.observableArrayList();
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try{
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/PalyulDatabaseProject","root","root");
            ps = conn.prepareStatement("select * from USERS");
            rs = ps.executeQuery();
            
            while(rs.next()) {
                PreviewUsers temp = new PreviewUsers();
                temp.setUsername(rs.getString("USERNAME"));
                temp.setPassword(rs.getString("PASSWORD"));
                temp.setAccess(rs.getString("ACCESS"));
                System.out.println(temp.getPassword());
                users.add(temp);
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
        
        System.out.println(users.get(0).getUsername());
        System.out.println(users.get(0).getPassword());
        un.setCellValueFactory(new PropertyValueFactory<PreviewUsers, String>("Username"));
        pw.setCellValueFactory(new PropertyValueFactory<PreviewUsers, String>("Password"));
        acs.setCellValueFactory(new PropertyValueFactory<PreviewUsers, String>("Access"));
        
        existingUsersTableView.setItems(users);
    }
    
    public void selectUser(){
        selectedUser = existingUsersTableView.getSelectionModel().getSelectedItem();
        username.setText(selectedUser.getUsername());
        password.setText(selectedUser.getPassword());
        if (selectedUser.getAccess().toString().equalsIgnoreCase("1")){
            userRadio.setSelected(false);
            adminRadio.setSelected(true);
              
        }
        else if (selectedUser.getAccess().toString().equalsIgnoreCase("0")){
              userRadio.setSelected(true);
            adminRadio.setSelected(false);
        }
        
    }
    
    public void editUser(MouseEvent event) {
        
        if (existingUsersTableView.getSelectionModel().getSelectedItem() == null){
             statusMsg.setText("No User Selected");
            
        }
        else {
          Alert confirmEdit = new Alert(Alert.AlertType.CONFIRMATION);
           Alert editResult = new Alert(Alert.AlertType.INFORMATION);
           confirmEdit.setContentText("Are you sure you want to edit the information?");
           Optional<ButtonType> result = confirmEdit.showAndWait();
    
           if (result.get()== ButtonType.OK){
               
            Connection conn = null;
            PreparedStatement ps = null;
            
            try {
                String sql = "update USERS set USERNAME = ?, PASSWORD = ?, ACCESS = ? where USERNAME = ? ";
                conn = DriverManager.getConnection("jdbc:derby://localhost:1527/PalyulDatabaseProject","root","root");
                ps = conn.prepareStatement(sql);
                ps.setString(1, username.getText());
                ps.setString(2, password.getText());
                if(adminRadio.isSelected()) {
                    ps.setString(3, "1");
                }
                else {
                    ps.setString(3, "0");
                }
                ps.setString(4,username.getText());
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
                 statusMsg.setText("USER successfully edited!");
                 displayUserInfo();
           }
      
                 else {
          editResult.setContentText("Nothing was Edited");
                 editResult.show();
        }

        }
    }
    
    public void deleteUser(MouseEvent event) {
        
          if (existingUsersTableView.getSelectionModel().getSelectedItem() == null){
             statusMsg.setText("No User Selected");
            
        }
          else {
        Alert confirmEdit = new Alert(Alert.AlertType.CONFIRMATION);
           Alert editResult = new Alert(Alert.AlertType.INFORMATION);
           confirmEdit.setContentText("Are you sure you want to DELETE this USER?");
           Optional<ButtonType> result = confirmEdit.showAndWait();
           
            if (result.get()== ButtonType.OK){
               
            Connection conn = null;
            PreparedStatement ps = null;
            
            try {
                String sql = "DELETE FROM USERS where USERNAME = ? ";
                conn = DriverManager.getConnection("jdbc:derby://localhost:1527/PalyulDatabaseProject","root","root");
                ps = conn.prepareStatement(sql);
                ps.setString(1, username.getText());
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
                 statusMsg.setText("USER successfully deleted!");
                 displayUserInfo();
           }
      
                 else {
          editResult.setContentText("Nothing was deleted");
                 editResult.show();
        }
          }
    }
    
    public void clearStatusMessage(){
        statusMsg.setText("");
    }
    
    public void userOption(){
        this.userType = "User";
        statusMsg.setText("User Selected");
    }
    
    public void adminOption(){
        this.userType = "Admin";
        statusMsg.setText("Admin Selected");
    }
    
    public void minimizeScreen(MouseEvent event){
        AdminMenuController.adminStage.setIconified(true);
    }
    
    public void closeScreen(MouseEvent event){
        System.exit(0);
    }
    
    public void enableTraversability(){
        username.setFocusTraversable(true);
        password.setFocusTraversable(true);
        
        clearStatusMessage();
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        exit.setPickOnBounds(true);
        exit.setFocusTraversable(false);
        imageDp.setFocusTraversable(false);
        editUserBtn.setFocusTraversable(false);
        deleteUserBtn.setFocusTraversable(false); 
        username.setFocusTraversable(false);
        password.setFocusTraversable(false);
        userRadio.setFocusTraversable(false);
        adminRadio.setFocusTraversable(false);
        existingUsersTableView.setFocusTraversable(false);
        displayUserInfo();
    }

    private void clearText() {
        username.clear();
        password.clear();
    }
}
