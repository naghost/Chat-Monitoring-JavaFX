package Inicio;

import Servidor.Servidor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    @FXML
    Button boton;

    @FXML
    public void lanzarServidor(){
    cerrarVentana();
    Servidor s = new Servidor();
    }
    @FXML
    public void lanzarUsuario(){
        Stage stage = null;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Cliente/Conexion.fxml"));
        Parent root1= null;
        try {
            root1 = (Parent) fxmlLoader.load();
            stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    cerrarVentana();
    }

    public void cerrarVentana(){
        Stage s = (Stage) boton.getScene().getWindow();
        s.close();
    }
}
