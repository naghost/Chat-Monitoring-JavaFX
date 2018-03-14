package Cliente;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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
    Socket conexionDatos;
    DataOutputStream flujo_salida;
    DataInputStream flujo_entrada;
    ObjectInputStream flujo_entrada_objetos;
    @FXML
    public void conexion(){



        try {
            this.conexion = new Socket(IP.getText(), Integer.parseInt(Puerto.getText()));
            flujo_salida = new DataOutputStream(conexion.getOutputStream());
            flujo_salida.writeUTF(Usuario.getText());
            flujo_salida.flush();
            flujo_entrada = new DataInputStream(conexion.getInputStream());
            String mensaje = flujo_entrada.readUTF();
            if(mensaje.equals("DIE")){
                JOptionPane.showMessageDialog(null, "Conexion no realizada: Nombre de usuario en uso");
            }else{
                int aux = Integer.parseInt(Puerto.getText());
                aux++;
                conexionDatos = new Socket(IP.getText(),aux);
                flujo_entrada_objetos = new ObjectInputStream(conexionDatos.getInputStream());
                invocar();
            }



        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void invocar(){
        Stage stage = null;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Chat.fxml"));
        Parent root1= null;
        try {
            root1 = (Parent) fxmlLoader.load();
            stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
            ChatController inicio = (ChatController) fxmlLoader.getController();
            inicio.Conexion(conexion,flujo_entrada, flujo_salida, flujo_entrada_objetos, Usuario.getText());

            stage.setOnCloseRequest((WindowEvent event1) -> {
                try {
                    flujo_salida.writeUTF("Disconnect//"+Usuario.getText());
                    flujo_salida.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    System.exit(0);
                }

            });
            Stage st =(Stage)IP.getScene().getWindow();
            st.close();

        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
