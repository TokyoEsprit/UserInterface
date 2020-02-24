/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import utils.Database;
import utils.MailUtils;

/**
 * FXML Controller class
 *
 * @author musta
 */
public class ForgotPasswordController implements Initializable {

    @FXML
    private TextField txtemail;
    @FXML
    private Button btnEnvoyer;
    @FXML
    private Button btnVerifier;
    @FXML
    private TextField txtcode;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button btnRetour;
    
    /**
     * Initializes the controller class.
     */
        Connection con;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        private Statement ste;
    @FXML
    private Label txtlabel;
        
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public ForgotPasswordController()
    {
        con =Database.getInstance().getConnection();
    }

    @FXML
    private void sendmail(ActionEvent event) throws SQLException {
        
        String email = txtemail.getText();
        MailUtils mail = new MailUtils();
        String Key = ResetKey();
        mail.mail(email,Key);
        PreparedStatement pre=con.prepareStatement("INSERT INTO recoverycodes (Code, expires) VALUES (?, NOW() + INTERVAL 48 HOUR);");
        pre.setString(1, Key);
        pre.executeUpdate();
        
    }

    @FXML
    private void verif(ActionEvent event) throws IOException, SQLException {
        if(event.getSource() == btnVerifier){
            String code = txtcode.getText();
            ste=con.createStatement();
            ResultSet rs=ste.executeQuery("select * from recoverycodes where code="+code+";");
            if(rs.next())
            {
                AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/ChangePassword.fxml"));
                rootPane.getChildren().setAll(pane);
            }else{
                txtlabel.setTextFill(Color.TOMATO);
                txtlabel.setText("Code incorrecte");
            }
            
        }
    }

    @FXML
    private void Retourner(ActionEvent event) throws IOException {
        if(event.getSource() == btnRetour){
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
            rootPane.getChildren().setAll(pane);
        }
    }
    
    private String ResetKey()
    {
        Random r = new Random();
        String randomNumber = String.format("%04d", (Object) Integer.valueOf(r.nextInt(1001)));
        System.out.println(randomNumber);
        return randomNumber;
    }
    
}
