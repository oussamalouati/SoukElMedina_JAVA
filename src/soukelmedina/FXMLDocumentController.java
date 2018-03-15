/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soukelmedina;

import utils.Connexion;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.awt.Button;
import static java.awt.SystemColor.window;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import utils.Delta;

/**
 *
 * @author INETEL
 */
public class FXMLDocumentController implements Initializable {
    final Delta dragDelta = new Delta(); 
    @FXML
    private JFXButton btn_ins, btn_return,btn_cnx;
    @FXML
    private AnchorPane an_cnx, an_ins, cnx_ban,ins_ban,left_ctrl;
    @FXML
    private JFXTextField cnx_f_field,nom_field,prenom_field,username_field,email_field,tel_field;
    @FXML
    private JFXPasswordField cnx_mdp_field,mdp_field;
    @FXML
    private JFXComboBox<String> type_acc;
    @FXML
    private JFXDatePicker date_picker;
    @FXML
    private JFXTextArea adresse_area;
    
    private FadeTransition fadeIn1 = new FadeTransition(
    Duration.millis(1000)
                );
    private FadeTransition fadeIn2 = new FadeTransition(
    Duration.millis(500)
                );
    
    @FXML
    private void handleClose() {
            System.exit(0);
    }
     @FXML
    private void handleMinimize(ActionEvent event) {
           Stage stage =(Stage) an_cnx.getScene().getWindow();
           stage.setIconified(true);
    }
    
    @FXML
    private void btnSwipHandler(ActionEvent event) {

        //le bloc ci dessous permet l'affichage des 'AnchorPane's relatives a l'inscription
        if (event.getSource() == btn_ins) {
            an_ins.setVisible(true);
            ins_ban.setVisible(true);
            fadeIn1.playFromStart();
            left_ctrl.setVisible(false);
            an_cnx.setVisible(false);
            cnx_ban.setVisible(false);
        }

        // le bloc ci dessous permet  un retour a l'etat initiale (la connexion)     
        if (event.getSource() == btn_return) {
            an_ins.setVisible(false);
            ins_ban.setVisible(false);

            left_ctrl.setVisible(true);
            an_cnx.setVisible(true);
            cnx_ban.setVisible(true);
            fadeIn2.playFromStart();
        }

        // focus dans le premier JFXTextField du form de connexion
        if (event.getSource() == btn_cnx) {
            cnx_f_field.requestFocus();
        }
    }
    @FXML
    private void Connexion (ActionEvent event) throws SQLException, IOException{
            Connexion cn1 =Connexion.getInstance();
            Connection conn = cn1.getConnection();
            String login =   cnx_f_field.getText();
            String reqcnx ="SELECT * FROM users WHERE username=?";
            PreparedStatement st=conn.prepareStatement(reqcnx);
            st.setString(1,login);
            ResultSet rs;
            rs=st.executeQuery();
             
            if (rs.isBeforeFirst()) {
              while (rs.next()) {
                  String mdp = rs.getString("mdp");
                  if (cnx_mdp_field.getText().equals(mdp)) {
                     String stat = rs.getString("status");
                      switch (stat) {
            case "Admin": 
                Parent admin_interface =FXMLLoader.load(getClass().getResource("/gui/Admin.fxml"));
                Scene  admin_scene = new Scene(admin_interface);
                Stage  main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                main_stage.hide();
                main_stage.setScene(admin_scene);
                main_stage.show();
                
                /*les deux fonction "setOnMousePressed" et "setOnMouseDragged"
              servent à deplacer la fenetre.  */
            admin_interface.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent mouseEvent) {
                  dragDelta.x = main_stage.getX() - mouseEvent.getScreenX();
                  dragDelta.y = main_stage.getY() - mouseEvent.getScreenY();
                }
              });
            admin_interface.setOnMouseDragged(new EventHandler<MouseEvent>() {
              @Override public void handle(MouseEvent mouseEvent) {
                main_stage.setX(mouseEvent.getScreenX() + dragDelta.x);
                main_stage.setY(mouseEvent.getScreenY() + dragDelta.y);
              }
            });
                     break;
            case "Vendeur": ;
                     break;
            case "Client":  ;
                     break;
            default: System.out.println("Probléme de connexion");
                     break;
        }
                  } else {
                      System.out.println("mdp incorrect");
                  }
              }

          } else {
              System.out.println("username incorrect");
          }
      }
    @FXML
    private void inscription () throws SQLException{
            Connexion cn1 =Connexion.getInstance();
            Connection conn = cn1.getConnection();
            //recuperation des information a partir du formulaire d'inscription
            String nom = nom_field.getText();
            String prenom = prenom_field.getText();
            String username = username_field.getText();
            String date_naiss= date_picker.getValue().toString();
            String email = email_field.getText();
            String tel = tel_field.getText();
            String adresse = adresse_area.getText();
            String typeCompte = type_acc.getSelectionModel().getSelectedItem();
            String mdp = mdp_field.getText();
            //test
            System.out.println(nom+"-"+prenom+"-"+username+"-"+date_naiss+"-"+email+"-"+tel+"-"+adresse+"-"+typeCompte+"-"+mdp);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fadeIn1.setNode(ins_ban);
        fadeIn2.setNode(cnx_ban);
        //initialisation des animations
        fadeIn1.setFromValue(0.0);
        fadeIn1.setToValue(1.0);
        fadeIn1.setCycleCount(1);
        fadeIn1.setAutoReverse(false);

        fadeIn2.setFromValue(0.0);
        fadeIn2.setToValue(1.0);
        fadeIn2.setCycleCount(1);
        fadeIn2.setAutoReverse(false);
        //ajout des items pour le combo box  "type de compte"
        type_acc.getItems().add("Client");
        type_acc.getItems().add("Vendeur");
        //valeur par defaut si l'utilisateur n'a pas slectionner une date de naissance
        date_picker.setValue(LocalDate.now());
    }    

   

    
    
}
