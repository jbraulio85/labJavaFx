package org.braulioecheverria.system;

import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.braulioecheverria.controller.LoginController;
import org.braulioecheverria.controller.NewUserController;

/**
 *
 * @author BraulioEcheverria
 */
public class MainApp extends Application {

    private final String PAQUETE_VISTA = "/org/braulioecheverria/views/";
    private Stage escenarioPrincipal;
    private Scene escena;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("Laboratorio de prueba");
        login();
        escenarioPrincipal.show();
    }

    public Initializable cambiarEscena(String fxml, int ancho, int alto) throws Exception {
        Initializable resultado = null;
        FXMLLoader cargadorFXML = new FXMLLoader();
        InputStream archivo = MainApp.class.getResourceAsStream(PAQUETE_VISTA + fxml);
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        cargadorFXML.setLocation(MainApp.class.getResource(PAQUETE_VISTA + fxml));
        escena = new Scene((AnchorPane) cargadorFXML.load(archivo), ancho, alto);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        resultado = (Initializable) cargadorFXML.getController();
        return resultado;
    }

    public void login() {
        try {
            LoginController login = (LoginController) cambiarEscena("LoginView.fxml", 450, 250);
            login.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void newUser(){
        try{
            NewUserController newUser = (NewUserController) cambiarEscena("NewUserView.fxml",600,708);
            newUser.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

}
