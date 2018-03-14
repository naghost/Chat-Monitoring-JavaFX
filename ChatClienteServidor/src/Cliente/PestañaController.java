package Cliente;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class PestañaController {
    String nombreUSR;
    String nombre;
    @FXML
    TextArea textArea;
    @FXML
    TextArea textAreaRecibir;

    DataOutputStream flujo_salida;

    ArrayList<PestañaController> listaControladores;

    MonitorLog mon;

    @FXML
    public void enviarDatos(){
    String mensaje = nombreUSR+"//"+nombre+"//"+textArea.getText();
        try {
            if(!mensaje.equals("")) {
                flujo_salida.writeUTF(mensaje);
                flujo_salida.flush();
                String aux = "YO: "+textArea.getText();
                textAreaRecibir.setText(textAreaRecibir.getText()+"\n"+aux);
                textArea.setText("");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void eliminar(){
     for(int i = 0; i<listaControladores.size();i++){
         if (listaControladores.get(i).getNombre().equals(nombre)){
             listaControladores.remove(i);
         }
     }
    }

    public void recibirDatos(DataOutputStream flujo_salida, ArrayList<PestañaController> listaControladores, MonitorLog mon, String NombreUSR){
        this.flujo_salida=flujo_salida;
        this.listaControladores = listaControladores;
        this.mon=mon;
        this.nombreUSR = NombreUSR;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TextArea getTextArea() {
        return textArea;
    }

    public void setTextArea(TextArea textArea) {
        this.textArea = textArea;
    }

    public TextArea getTextAreaRecibir() {
        return textAreaRecibir;
    }

    public void setTextAreaRecibir(TextArea textAreaRecibir) {
        this.textAreaRecibir = textAreaRecibir;
    }
}
