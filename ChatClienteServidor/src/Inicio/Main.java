package Inicio;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
* @author Miguel Angel Hernandez Rodriguez
* @version 1.0
*
* */
public class Main extends Application {

    @Override

    /*
     * @author Miguel Angel Hernandez Rodriguez
     * @version 1.0
     * Metodo que ejecuta la primera interfaz para seleccionar al usuario
     *
     * @param primaryStage escenario principal para la carga de archivos fxml
     * */
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    /*
     * @author Miguel Angel Hernandez Rodriguez
     * @version 1.0
     * metododo que ejecuta el hilo de javaFX
     * */
    public static void main(String[] args) {
        launch(args);
    }
}
