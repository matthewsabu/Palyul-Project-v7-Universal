package project.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController implements Initializable {
    
    public static Stage loginStage = null;
    
    public TextField username, password;
    public Button signIn;
    private boolean signedOut = false;
    public Label statusMsg;
    
    private static DynamicArray<String> usernameArray = new DynamicArray<>();
    private static DynamicArray<String> passwordArray = new DynamicArray<>();
    private static DynamicArray<String> restrictionArray = new DynamicArray<>();
    private static DynamicArray<String> pictureArray = new DynamicArray<>();
    
    public void signIn(MouseEvent event) throws IOException{
        FXMLLoader fxmloader;
        Parent root = null;
        
        //Stage signInStage = new Stage();
        //loginStage = signInStage;
        
        for(int i=0; i<usernameArray.size(); i++) {
            if (username.getText().equals(usernameArray.get(i)) && password.getText().equals(passwordArray.get(i))) {
                System.out.println(usernameArray.get(i) + " " + passwordArray.get(i) + " " + restrictionArray.get(i));
                if (restrictionArray.get(i).equals("0")) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/project/FXML/UserMenu.fxml"));
                    Parent tableViewParent = loader.load();
                    Scene tableViewScene = new Scene(tableViewParent);
                    
                    UserMenuController controller = loader.getController();
                    controller.importUsername((String)usernameArray.get(i));
                    
                    Stage signInStage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    loginStage = signInStage;
                    signInStage.setScene(tableViewScene);
                    signInStage.centerOnScreen();
                    //signInStage.initStyle(StageStyle.UNDECORATED);
                    signInStage.show();
                }
                else if (restrictionArray.get(i).equals("1")) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/project/FXML/AdminMenu.fxml"));
                    Parent tableViewParent = loader.load();
                    Scene tableViewScene = new Scene(tableViewParent);
                    
                    AdminMenuController controller = loader.getController();
                    controller.importUsername((String) usernameArray.get(i));
                    
                    Stage signInStage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    loginStage = signInStage;
                    signInStage.setScene(tableViewScene);
                    signInStage.centerOnScreen();
                    //signInStage.initStyle(StageStyle.UNDECORATED);
                    signInStage.show();
                }
                
            }
            else {
//                username.setText("");
//                password.setText("");
                statusMsg.setText("Incorrect Username or Password!");
            }
        }
               
        
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
    
    public void isSignedOut(boolean signedOut){
        this.signedOut = signedOut;
    }
    
    public void minimizeScreen(MouseEvent event){
        if(signedOut) AdminMenuController.adminStage.setIconified(true);
        else Main.primaryStage.setIconified(true);
    }
    
    public void closeScreen(MouseEvent event){
        System.exit(0);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        username.setFocusTraversable(false);
        password.setFocusTraversable(false);
        signIn.setFocusTraversable(false);
        
        getUserData();
        System.out.println(usernameArray.get(0) + " " +passwordArray.get(0));
    }
    
}

