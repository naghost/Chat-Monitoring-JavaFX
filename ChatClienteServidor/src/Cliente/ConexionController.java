package Cliente;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class ConexionController {
    @FXML
    TextField IP;
    @FXML
    TextField Puerto;
    @FXML
    TextField Usuario;
    Socket conexion;
    ObjectOutputStream flujo_salida;
    ObjectInputStream flujo_entrada;
    @FXML
    public void conexion(){



        try {
            this.conexion = new Socket(IP.getText(), Integer.parseInt(Puerto.getText()));
            flujo_salida = new ObjectOutputStream(conexion.getOutputStream());
            flujo_salida.writeUTF(Usuario.getText());
            flujo_salida.flush();
            flujo_entrada = new ObjectInputStream(conexion.getInputStream());
            String mensaje = flujo_entrada.readUTF();
            if(mensaje.equals("DIE")){
                JOptionPane.showMessageDialog(null, "Conexion no realizada: Nombre de usuario en uso");
            }else{
                invocar(flujo_entrada, flujo_salida);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void invocar(ObjectInputStream flujo_entrada, ObjectOutputStream flujo_salida){
        Stage stage = null;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Chat.fxml"));
        Parent root1= null;
        try {
            root1 = (Parent) fxmlLoader.load();
            stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
            ChatController inicio = (ChatController) fxmlLoader.getController();
            inicio.Conexion(conexion,flujo_entrada, flujo_salida);
            Stage st =(Stage)IP.getScene().getWindow();
            st.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
