/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soukelmedina;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.awt.Button;
import static java.awt.SystemColor.window;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author INETEL
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private JFXButton btn_ins, btn_return,btn_cnx;
    @FXML
    private AnchorPane an_cnx, an_ins, cnx_ban,ins_ban,left_ctrl;
    @FXML
    private JFXTextField cnx_f_field;
    
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
                       if(event.getSource()== btn_ins){
                        an_ins.setVisible(true);
                        ins_ban.setVisible(true);
                        fadeIn1.playFromStart();
                        left_ctrl.setVisible(false);
                        an_cnx.setVisible(false);
                        cnx_ban.setVisible(false);
                       }
                 // le bloc ci dessous permet  un retour a l'etat initiale (la connexion)     
                       if(event.getSource()== btn_return){
                        an_ins.setVisible(false);
                        ins_ban.setVisible(false);
                        
                        left_ctrl.setVisible(true);
                        an_cnx.setVisible(true);
                        cnx_ban.setVisible(true);
                        fadeIn2.playFromStart();
                       }
                  // focus dans le premier JFXTextField du form de connexion
                       if(event.getSource()== btn_cnx){
                          cnx_f_field.requestFocus();
                       }
                }
            
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fadeIn1.setNode(ins_ban);
        fadeIn2.setNode(cnx_ban);
        
    fadeIn1.setFromValue(0.0);
    fadeIn1.setToValue(1.0);
    fadeIn1.setCycleCount(1);
    fadeIn1.setAutoReverse(false);
    
    fadeIn2.setFromValue(0.0);
    fadeIn2.setToValue(1.0);
    fadeIn2.setCycleCount(1);
    fadeIn2.setAutoReverse(false);
    }    

   

    
    
}
