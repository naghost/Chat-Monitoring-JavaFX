package Cliente;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
/*
 * @author Miguel Angel Hernandez Rodriguez
 * @version 1.0
 * Clase encargada de la gestion de cada TAB de el chat
 * */
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

    /*
     * @author Miguel Angel Hernandez Rodriguez
     * @version 1.0
     * Clase encargada de gestionar la ventanda principal del chat de enviar los datos recogidos de la interfaz
     */

    @FXML
    public void enviarDatos(){
    String mensaje = nombreUSR+"//"+nombre+"//"+textArea.getText();
        try {
            if(!textArea.getText().equals("")) {
                flujo_salida.writeUTF(mensaje);
                flujo_salida.flush();
                mon.escribirLog(1, nombreUSR);
                String aux = "YO: "+textArea.getText();
                textAreaRecibir.setText(textAreaRecibir.getText()+"\n"+aux);
                textArea.setText("");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*
     * @author Miguel Angel Hernandez Rodriguez
     * @version 1.0
     * Metodo que elimina el tab de la lista de controladores activos
     * */
    @FXML
    public void eliminar(){
     for(int i = 0; i<listaControladores.size();i++){
         if (listaControladores.get(i).getNombre().equals(nombre)){
             listaControladores.remove(i);
         }
     }
    }
    /*
     * @author Miguel Angel Hernandez Rodriguez
     * @version 1.0
     * Metodo que hace de constructor para recibir los datos
     * @param flujo_salida objeto para enviar informacion a los usuarios
     * @param listaControladores lista de los controladores activos
     * @param mon monitor encargado de la escritura de los archivos de la interfaz
     * @param NombreUSR variable que contiene el nombre del usuario actual
     * */
    public void recibirDatos(DataOutputStream flujo_salida, ArrayList<PestañaController> listaControladores, MonitorLog mon, String NombreUSR){
        this.flujo_salida=flujo_salida;
        this.listaControladores = listaControladores;
        this.mon=mon;
        this.nombreUSR = NombreUSR;
    }

    /*
     * @author Miguel Angel Hernandez Rodriguez
     * @version 1.0
     * Zona de getters y setters
     * */

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
