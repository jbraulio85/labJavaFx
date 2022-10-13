package org.braulioecheverria.controller;

import com.sun.glass.ui.Window;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import org.braulioecheverria.system.MainApp;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.braulioecheverria.dao.Conexion;
import org.braulioecheverria.models.Usuario;


public class LoginController implements Initializable {
    private Usuario us = new Usuario();
    private MainApp escenarioPrincipal;

    public MainApp getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(MainApp escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    @FXML
    private Button btnAceptar;
    
    @FXML
    private Button btnCancelar;
    
    @FXML
    private TextField txtUsuario;
    
    @FXML
    private PasswordField txtPassword;
    
    @Override
    public void initialize (URL url, ResourceBundle rb){
        
    }

    @FXML
    public void eventoAceptar(ActionEvent actionEvent) {
        buscarUsuario();
    }
    
    private void buscarUsuario(){
        int resultado = 0;
        try{
            PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("call sp_buscar_usuario(?,?)");
            sp.setString(1, txtUsuario.getText());
            sp.setString(2, txtPassword.getText());
            ResultSet rs = sp.executeQuery();
            if (rs.next()){
                resultado = 1;
                if (resultado == 1){
                    us.setEmail(rs.getString(2));
                    Alert alerta = new Alert(AlertType.INFORMATION);
                    alerta.setTitle("Bienvenido!!!");
                    alerta.setHeaderText("Este es un laboratorio de prueba");
                    alerta.setContentText("Si estás viendo esto es porque todo está nítido, bienvenido: " + us.getEmail());
                    alerta.showAndWait();
                }
            }else{
                Alert alerta = new Alert(AlertType.ERROR);
                alerta.setContentText("La cagaste");
                alerta.showAndWait();
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void eventoCancelar(ActionEvent actionEvent) {
        Alert alerta  = new Alert(AlertType.WARNING,"Está seguro que quieres salir",
                            ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> rs = alerta.showAndWait();
        if(rs.get() == ButtonType.YES){
            Platform.exit();
        }else{
            getEscenarioPrincipal();
        }
    }

    public void eventoRegistrarse(MouseEvent mouseEvent) {        
        escenarioPrincipal.newUser();
    }
}
