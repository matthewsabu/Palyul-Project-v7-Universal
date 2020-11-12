/*
 * Matthew Sabularse
 * 11813652
 */
package project.Controllers;

//import java.awt.GraphicsDevice;
//import java.awt.GraphicsEnvironment;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application{
    
    public static Stage primaryStage = null;
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("/project/FXML/Login.fxml"));
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/project/FXML/Login.fxml"));
        Parent root = loader.load();
        
        /*GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();*/
        
        Image icon = new Image("pics/gold ed logo2.png");
        primaryStage.getIcons().add(icon);
                
        Scene projScene = new Scene(root, 950, 550); //1920x1000
        primaryStage.setScene(projScene);
        
        this.primaryStage = primaryStage;
               
        primaryStage.setTitle("User Login");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
