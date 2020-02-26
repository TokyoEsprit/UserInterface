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
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import models.User;
import utils.ConnectionUtil;
import controllers.UserController;
import java.sql.SQLException;
import java.util.regex.Pattern;
import javafx.scene.control.PasswordField;

/**
 * FXML Controller class
 *
 * @author musta
 */
public class SignupController implements Initializable {
    
    ObservableList<String> typelist = FXCollections
            .observableArrayList("Client","Fournisseur","Organisateur");

    @FXML
    private TextField txtFirstname;
    @FXML
    private TextField txtLastname;
    @FXML
    private TextField txtLogin;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private TextField txtEmail;
    @FXML
    private ComboBox<String> txtType;
    @FXML
    private Button btnSubmit;
    @FXML
    private Button btnLogin;
    @FXML
    private AnchorPane rootPane;
    
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    @FXML
    private Label lblErrors;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        txtType.setValue("Client");
        txtType.setValue("Fournisseur");
        txtType.setValue("Organisateur");
        txtType.setItems(typelist);
        
        if (con == null) {
            lblErrors.setTextFill(Color.TOMATO);
            lblErrors.setText("Server Error : Check");
        } else {
            lblErrors.setTextFill(Color.GREEN);
            lblErrors.setText("Server is up : Good to go");
        }
        
        
    }
    
    public SignupController() {
        con = ConnectionUtil.conDB();
    }

    @FXML
    private void LoginRedirect(ActionEvent event) throws IOException {
        
        if(event.getSource() == btnLogin){
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
            rootPane.getChildren().setAll(pane);
        }
    }

    @FXML
    private void InsertUser(ActionEvent event) throws SQLException, IOException {
        if(event.getSource() == btnSubmit)
        {
            String status = "Success";
            String Login = txtLogin.getText();
            String Firstname = txtFirstname.getText();
            String Lastname = txtLastname.getText();
            String Password = txtPassword.getText();
            String email = txtEmail.getText();
            String type = txtType.getValue();
            if(Login.isEmpty() || Password.isEmpty() || email.isEmpty() || Firstname.isEmpty() || Lastname.isEmpty() || type.isEmpty()) {
                setLblError(Color.TOMATO, "Empty credentials");
                status = "Error";
            }else {
                if(!validMail(email))
                {
                    setLblError(Color.TOMATO, "Veuillez entrer un email correcte");
                    status = "Error";
                    
                }else{
                    if(!validname(Firstname)){
                        setLblError(Color.TOMATO, "Veuillez entrer un email correcte");
                        status = "Error";
                    }else{
                        if(!validname(Lastname)){
                            setLblError(Color.TOMATO, "Veuillez entrer un email correcte");
                            status = "Error";
                        }else{
                                    User u = new User(Login,Password,email,Lastname,Firstname,type);
                                    UserController uc = new UserController();
                                    uc.Add(u);
                                    AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
                                    rootPane.getChildren().setAll(pane);}
                         }
                    }
                }
        }
    }
    
    private void setLblError(Color color, String text) {
        lblErrors.setTextFill(color);
        lblErrors.setText(text);
        System.out.println(text);
    }
    
    public boolean validMail(String mail) throws SQLException {
        
        
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                            "[a-zA-Z0-9_+&*-]+)*@" +
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                            "A-Z]{2,7}$";
                             
        Pattern pat = Pattern.compile(emailRegex);
        if (mail == null)
            return false;
        return pat.matcher(mail).matches();
    }
        public boolean validname(String name) throws SQLException {
            if (name.length()==0){return false;}
        
            for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (!(((c >= 'a' && c <= 'z'))||((c >= 'A' && c <= 'Z'))||(c==' '))){return false;}
          }return true;}
    
    
}
