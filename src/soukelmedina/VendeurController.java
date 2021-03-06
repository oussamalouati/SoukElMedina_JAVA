/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soukelmedina;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Duration;
import static soukelmedina.FXMLDocumentController.id;
import static soukelmedina.FXMLDocumentController.login;
import static soukelmedina.FXMLDocumentController.nom;
import static soukelmedina.FXMLDocumentController.prenom;
import utils.Connexion;
import utils.Delta;

/**
 *
 * @author INETEL
 */
public class VendeurController implements Initializable{
    final Delta dragDelta = new Delta();
    
    String urlImgMag;
    @FXML
    private JFXTextField pass_text,nom_mag;
    @FXML
    private JFXTextField nom_field,prenom_field,username_field,email_field,tel_field;
    @FXML
    private JFXDatePicker date_picker;
    @FXML
    private JFXTextArea adresse_area,descri_mag,adresse_mag;
    @FXML
    private JFXPasswordField mdp_field;
    @FXML
    private JFXButton show_mdp;
    @FXML
    private JFXButton btn_valider;
    @FXML
    private JFXButton gest_compte,suppCompteStep1,suppCompteStep2,suppAnnuler;
    @FXML
    private AnchorPane an_gestCompte,validerSupp,an_createMagasin;
     @FXML
    private AnchorPane dashboard;
    @FXML
     private Label usr_corrd;
    @FXML
    private JFXButton logout_btn;
    @FXML
    private JFXButton createMag,upload_mag,insert_mag;

    private FadeTransition fadeIn1 = new FadeTransition(
            Duration.millis(500)
    );
    @FXML
    private void handleClose() {
            System.exit(0);
    }
     @FXML
    private void handleMinimize(ActionEvent event) {
           Stage stage =(Stage) ((Node) event.getSource()).getScene().getWindow();
           stage.setIconified(true);
    }
    @FXML
    private void logout(ActionEvent event) throws IOException, Exception {
           Stage  current_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
           
           Parent main_interface =FXMLLoader.load(getClass().getResource("/gui/Acceuil.fxml"));
                Scene  main_scene = new Scene(main_interface);
                main_scene.setFill(Color.TRANSPARENT);
                current_stage.close();
                current_stage.setScene(main_scene);
                current_stage.show();
             main_interface.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent mouseEvent) {
                  dragDelta.x = current_stage.getX() - mouseEvent.getScreenX();
                  dragDelta.y = current_stage.getY() - mouseEvent.getScreenY();
                }
              });
            main_interface.setOnMouseDragged(new EventHandler<MouseEvent>() {
              @Override public void handle(MouseEvent mouseEvent) {
                current_stage.setX(mouseEvent.getScreenX() + dragDelta.x);
                current_stage.setY(mouseEvent.getScreenY() + dragDelta.y);
              }
            });
           
    }
    
    @FXML
    private void gestionCompte(ActionEvent event) throws SQLException{
            dashboard.setVisible(false);
            an_gestCompte.setVisible(true);
            
            Connexion cn1 =Connexion.getInstance();
            Connection conn = cn1.getConnection();
            String reqGet ="SELECT * FROM users WHERE username=?";
            String reqUpd="UPDATE users SET nom=?,prenom=?,username=?,date_naissance=?,email=?,tel=?,adresse=?,mdp=?  WHERE id= ?";
            String reqSup="DELETE FROM users where id=?";
            PreparedStatement st=conn.prepareStatement(reqGet);
            st.setString(1,login);
            ResultSet rs ;
            rs =st.executeQuery();
            
            while (rs.next()) {
            nom_field.setText(rs.getString("nom"));
            prenom_field.setText(rs.getString("prenom")); 
            username_field.setText(rs.getString("username"));
            date_picker.setValue(LocalDate.parse(rs.getString("date_naissance")));
            email_field.setText(rs.getString("email"));
            tel_field.setText(rs.getString("tel"));
            adresse_area.setText(rs.getString("adresse"));
            mdp_field.setText(rs.getString("mdp"));
           
            }
            
            btn_valider.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    String nom = nom_field.getText();
                    String prenom = prenom_field.getText();
                    String username = username_field.getText();
                    String date_naiss = date_picker.getValue().toString();
                    String email = email_field.getText();
                    String tel = tel_field.getText();
                    String adresse = adresse_area.getText();
                    String mdp = mdp_field.getText();
                    try {
                        PreparedStatement st2=conn.prepareStatement(reqUpd);
                        st2.setString(1,nom);
                        st2.setString(2,prenom);
                        st2.setString(3,username);
                        st2.setString(4,date_naiss);
                        st2.setString(5,email);
                        st2.setString(6,tel);
                        st2.setString(7,adresse);
                        st2.setString(8,mdp);
                        st2.setInt(9,id);
                        st2.executeUpdate();
                    } catch (SQLException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            });
            suppCompteStep2.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    try {
                        PreparedStatement st3=conn.prepareStatement(reqSup);
                        st3.setInt(1,id);
                        st3.executeUpdate();
                        logout_btn.fire();
                    } catch (SQLException ex) {
                        System.out.println("delete error");                    }
                }
            });
             suppCompteStep1.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                              validerSupp.setVisible(true);
                }
            });
              suppAnnuler.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                           
                           validerSupp.setVisible(false);
                           fadeIn1.playFromStart();
                           
                }
            });
    }
    
    @FXML
    private void showpass() {
        show_mdp.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mdp_field.setVisible(false);
                pass_text.setVisible(true);
                pass_text.setText(mdp_field.getText());
            }

        });
        show_mdp.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mdp_field.setVisible(true);
                pass_text.setVisible(false);
            }
        });
                
    }
    
    @FXML
    private void backtodash(ActionEvent event){
            dashboard.setVisible(true);
            an_gestCompte.setVisible(false);           
            an_createMagasin.setVisible(false);
    }
    
     @FXML
    void createMagasin(ActionEvent event) {
            dashboard.setVisible(false);
            an_createMagasin.setVisible(true);
            
            Connexion cn1 =Connexion.getInstance();
            Connection conn = cn1.getConnection();
            String reqAjtMag="INSERT INTO magazin (nom_magazin,description,adresse,latitude,longitude,img,proprietaire) VALUES(?,?,?,?,?,?,?)";
            //upload de l'image/logo du magasin 
            upload_mag.setOnMousePressed(new EventHandler<MouseEvent>(){
                @Override
                public void handle(MouseEvent event) {
                    FileChooser fc_mag = new FileChooser();
                    fc_mag.getExtensionFilters().addAll(new ExtensionFilter[]{
                        new ExtensionFilter("Image Files", new String[]{"*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp"}),
                        new ExtensionFilter("JPG", new String[]{"*.jpg"}),
                        new ExtensionFilter("JPEG", new String[]{"*.jpeg"}), 
                        new ExtensionFilter("BMP", new String[]{"*.bmp"}), 
                        new ExtensionFilter("PNG", new String[]{"*.png"}), 
                        new ExtensionFilter("GIF", new String[]{"*.gif"})});
                    File img = fc_mag.showOpenDialog(null);
                    Path pathdest = Paths.get("C:/Users/USER/Documents/GitHub/SoukElMedina_JAVA/src/gui/magimg/"+img.getName());
                    urlImgMag=pathdest.toString();
                    try {         
                        Files.copy(img.toPath(),pathdest, StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException ex) {
                        Logger.getLogger(VendeurController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            insert_mag.setOnMousePressed(new EventHandler<MouseEvent>(){
                @Override
                public void handle(MouseEvent event) {
                     String nomMag=nom_mag.getText();
                     String discMag=descri_mag.getText();
                     String adrMag=adresse_mag.getText();
                    try {
                        PreparedStatement st4=conn.prepareStatement(reqAjtMag);
                        st4.setString(1,nomMag);
                        st4.setString(2,discMag);
                        st4.setString(3,adrMag);
                        st4.setInt(4,0);
                        st4.setInt(5,0);
                        st4.setString(6,urlImgMag);
                        st4.setInt(7,id);
                        st4.executeUpdate();
                    } catch (SQLException ex) {
                        Logger.getLogger(VendeurController.class.getName()).log(Level.SEVERE, null, ex);
                    }                   
                }
            });
            
            
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usr_corrd.setText(nom+" "+prenom);
        fadeIn1.setNode(validerSupp);
        fadeIn1.setFromValue(0.0);
        fadeIn1.setToValue(1.0);
        fadeIn1.setCycleCount(1);
        fadeIn1.setAutoReverse(true);
    }
    
}
