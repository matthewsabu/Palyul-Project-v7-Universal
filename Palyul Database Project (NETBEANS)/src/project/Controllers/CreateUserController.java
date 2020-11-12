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
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import static project.Controllers.AddEntryController.username;

public class CreateUserController implements Initializable{

    public static Stage createUserStage = null;
    
    public Button exit,imageDp,createUserBtn;
    public TextField username,password;
    public RadioButton userRadio,adminRadio;
    public Label statusMsg;
    private String userType = "User";
    public String user;
    
    private static DynamicArray<String> usernameArray = new DynamicArray<>();
    private static DynamicArray<String> passwordArray = new DynamicArray<>();
    private static DynamicArray<String> restrictionArray = new DynamicArray<>();
    private static DynamicArray<String> pictureArray = new DynamicArray<>();
    
    public void importUsername(String data) {
        user = data;
        System.out.println("You username is " + user);
    }
    
    public void exit(MouseEvent event) throws IOException{
        FXMLLoader fxmloader = new FXMLLoader(getClass().getResource("/project/FXML/AdminMenu.fxml"));
        Parent root = (Parent) fxmloader.load();
        
        Stage userStage = new Stage();
        createUserStage = userStage;
        
        AdminMenuController adminMenu = fxmloader.getController();
        adminMenu.importUsername(user);
        adminMenu.exitedFromCreateUser(true);
        
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
    
    public void getUserData() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try{
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/PalyulDatabaseProject","root","root");
            ps = conn.prepareStatement("select * from USERS");
            rs = ps.executeQuery();
            
            while(rs.next()) {
                usernameArray.add(rs.getString("USERNAME"));
                passwordArray.add(rs.getString("PASSWORD"));
                restrictionArray.add(rs.getString("ACCESS"));
                //pictureArray.add(rs.getString("PICTURE"));
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
    }
    
    public void createUser(MouseEvent event) {
        int repeat = 0;
        
        for(int i=0; i<usernameArray.size(); i++) {
            if(usernameArray.get(i).equals(username.getText())) {
                repeat = 1;
            }
        }
        
        if(repeat == 1) {
            statusMsg.setText("Similar username found in database!");
        }
        else if (username.getText().equals("") || password.getText().equals("")) {
            statusMsg.setText("Please input all fields!");
        }
        else {
            Connection conn = null;
            PreparedStatement ps = null;
            
            try {
                String sql = "Insert into USERS (USERNAME, PASSWORD, ACCESS, PICTURE) values (?, ?, ?, ?)";

                conn = DriverManager.getConnection("jdbc:derby://localhost:1527/PalyulDatabaseProject","root","root");
                ps = conn.prepareStatement(sql);
                ps.setString(1, username.getText());
                ps.setString(2, password.getText());
                if(userRadio.isSelected()) {
                    ps.setString(3, "0");
                }
                else {
                    ps.setString(3, "1");
                }
                ps.setString(4, "");    //this will change if picture will be added
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
            statusMsg.setText("User successfully created!");
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
    
    private void clearText()
    {
     username.clear();
     password.clear();
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
        createUserBtn.setFocusTraversable(false); 
        username.setFocusTraversable(false);
        password.setFocusTraversable(false);
        userRadio.setFocusTraversable(false);
        adminRadio.setFocusTraversable(false);
        getUserData();
    }
    
}
