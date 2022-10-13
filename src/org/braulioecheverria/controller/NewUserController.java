package org.braulioecheverria.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import org.braulioecheverria.dao.Conexion;
import org.braulioecheverria.models.Persona;
import org.braulioecheverria.models.Usuario;
import org.braulioecheverria.system.MainApp;

/**
 * FXML Controller class
 *
 * @author jbmon
 */
public class NewUserController implements Initializable {

    private Persona personaNueva = new Persona();
    private Usuario usuarioNuevo = new Usuario();

    private MainApp escenarioPrincipal;

    public MainApp getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(MainApp escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPassword;
    @FXML
    private Button btnAceptar;
    @FXML
    private Button btnCancelar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void aceptar() {
        agregarPersona();
        agregarUsuario(idPersona());
        Alert alerta = new Alert(AlertType.INFORMATION);
        alerta.setTitle("Laboratorio 1");
        alerta.setHeaderText("Éxito!!!");
        alerta.setContentText("El registro fue ingresado exitosamente");
        alerta.showAndWait();
        limpiarControles();
         Alert alerta1  = new Alert(AlertType.WARNING,"Deseas iniciar sesión?",
                            ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> rs = alerta1.showAndWait();
        if(rs.get() == ButtonType.YES){
            escenarioPrincipal.login();
        }else{
            Alert alerta2 = new Alert(AlertType.INFORMATION);
            alerta2.setTitle("Laboratorio 1");
            alerta2.setHeaderText("Adiós!!!");
            alerta2.setContentText("Gracias por utilizar este programa");
            alerta2.showAndWait();
            Platform.exit();
        }
    }

    private void limpiarControles() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
    }

    private void agregarPersona() {
        personaNueva.setNombres((txtNombre.getText()));
        personaNueva.setApellidos(txtApellido.getText());
        personaNueva.setTelefono(Integer.parseInt(txtTelefono.getText()));
        try {
            PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("call sp_agregar_persona(?,?,?);");
            sp.setString(1, personaNueva.getNombres());
            sp.setString(2, personaNueva.getApellidos());
            sp.setInt(3, personaNueva.getTelefono());
            sp.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int idPersona() {
        int id = 0;
        try {
            PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("call sp_buscarPersona();");
            ResultSet rs = sp.executeQuery();
            while (rs.next()) {
                id = rs.getInt(1);
                System.out.println(id);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }

    private void agregarUsuario(int persona) {
        usuarioNuevo.setEmail(txtEmail.getText());
        usuarioNuevo.setContrasenia(txtPassword.getText());
        try {
            PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("call sp_agregar_usuario(?,?,?);");
            sp.setString(1, usuarioNuevo.getEmail());
            sp.setString(2, usuarioNuevo.getContrasenia());
            sp.setInt(3, persona);
            sp.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void cancelar() {
        escenarioPrincipal.login();
    }

}
