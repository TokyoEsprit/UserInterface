/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import utils.PasswordUtils;

/**
 * FXML Controller class
 *
 * @author musta
 */
public class ChangePasswordController implements Initializable {

    @FXML
    private PasswordField txtpwd;
    @FXML
    private PasswordField txtpwd1;
    @FXML
    private Button btnconfirmer;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField txtemail;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void confirmermotdepasse(ActionEvent event) throws IOException, SQLException {
        if(event.getSource() == btnconfirmer){
            if(txtpwd.getText().equals(txtpwd1.getText())){
                UserController uc = new UserController();
                PasswordUtils pu = new PasswordUtils();
                uc.update(txtemail.getText(), pu.hashPassword(txtpwd.getText()));
                AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
                rootPane.getChildren().setAll(pane);
            }
            
        }
    }

    
}
