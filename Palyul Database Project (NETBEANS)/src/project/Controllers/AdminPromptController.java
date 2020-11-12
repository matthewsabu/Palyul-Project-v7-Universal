package project.Controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AdminPromptController implements Initializable{

    public TextField username,password;
    public Button OK,cancel;
    public boolean status=false;
    private FXMLLoader fxmloader;
    public Label statusMsg;
    
    private static DynamicArray<String> usernameArray = new DynamicArray<>();
    private static DynamicArray<String> passwordArray = new DynamicArray<>();
    private static DynamicArray<String> restrictionArray = new DynamicArray<>();
    
    public void ok(){
        for(int i=0; i<usernameArray.size(); i++) {
            if(usernameArray.get(i).equals(username.getText()) && passwordArray.get(i).equals(password.getText())) {
                if(restrictionArray.get(i).equals("1")) {
                    Stage stage = (Stage) OK.getScene().getWindow();
                    stage.close();
                    statusMsg.setText("Edit/Delete granted!");
                    status = true;
                    EditEntryController editEntry = fxmloader.getController();
                    editEntry.OKstatus(true);                    
                }
                else {
                    statusMsg.setText("User is not an admin!");
                    break;
                }
            }
        }
        statusMsg.setText("Incorrect admin username or password!");
        
    }
    
    public void cancel(){
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
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
    
    public void passVariables(FXMLLoader fxmloader,boolean status){
        this.fxmloader = fxmloader;
        this.status = status;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        username.setFocusTraversable(false);
        password.setFocusTraversable(false);
        OK.setFocusTraversable(false);
        cancel.setFocusTraversable(false);
        getUserData();
    }
    
    
    
}
