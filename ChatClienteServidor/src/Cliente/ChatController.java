package Cliente;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ChatController {
    @FXML
    TableView<UsuariosModel> tabla;
    @FXML
    TableColumn<UsuariosModel, String> column;
    @FXML
    TabPane tabPane;

    Socket conexion;

    @FXML
    public void crearTab(){
        try {
            Tab tab =(Tab)FXMLLoader.load(this.getClass().getResource("Pestaña.fxml"));
            tabPane.getTabs().addAll(tab);
            tab.setText("Melon");
            tab.setClosable(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void Conexion(Socket conexion, ObjectInputStream flujo_entrada){
        this.conexion = conexion;
        ActualizarActivos actualizar = new ActualizarActivos(tabla,column,conexion,flujo_entrada);
        actualizar.start();
    }
}
