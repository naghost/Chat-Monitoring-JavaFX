package Cliente;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
/*
 * @author Miguel Angel Hernandez Rodriguez
 * @version 1.0
 * Clase encargada de leer los datos del socket y mostrarlo en la interfaz
 */
public class EscuchadorClientes extends Thread {

    DataInputStream flujo_entrada;
    DataOutputStream flujo_salida;
    ArrayList<PestañaController> listaControladores;
    boolean salir = false;
    TabPane tabPane;
    MonitorLog mon;
    ChatController controller;

    /*
     * @author Miguel Angel Hernandez Rodriguez
     * @version 1.0
     * Constructor, que se encarga de recibir los datos para ejecutar el hilo
     * @param flujo_entrada con este objeto recibimos informacion
     * @param listaControladores lista con todos los tabs
     * @param tabPane zona de paneles de la interfaz
     * @param flujo_salida objeto encargado de enviar daos
     * @param mon objeto monitor encargado de escribir en el archivo de monitor
     * @param controller objeto con el controlador del chat
     */

    public EscuchadorClientes(DataInputStream flujo_entrada, ArrayList<PestañaController> listaControladores, TabPane tabPane, DataOutputStream flujo_salida, MonitorLog mon, ChatController controller) {
        this.flujo_entrada=flujo_entrada;
        this.listaControladores=listaControladores;
        this.tabPane=tabPane;
        this.flujo_salida=flujo_salida;
        this.mon=mon;
        this.controller=controller;
    }

    /*
     * @author Miguel Angel Hernandez Rodriguez
     * @version 1.0
     * metodo del que se hace hilo que lee los mensajes del servidor
     * */
    @Override
    public void run() {
        super.run();
        while (!salir){
            try {
                String mensaje = flujo_entrada.readUTF();

                String mensajeAnalizado[]= mensaje.split("//");
                mon.escribirLog(2,mensajeAnalizado[1]);
                boolean existe = false;
                for (int i=0; i<listaControladores.size();i++){
                    if (listaControladores.get(i).nombre.equals(mensajeAnalizado[0])){
                        listaControladores.get(i).getTextAreaRecibir().setText((listaControladores.get(i).getTextAreaRecibir().getText()+"\n"+mensajeAnalizado[0]+": "+mensajeAnalizado[2]));
                        existe=true;
                    }
                }
                if (!existe){
                    ChatController aux= controller;
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Pestaña.fxml"));
                    Tab tab = (Tab) fxmlLoader.load();
                    PestañaController controller = (PestañaController) fxmlLoader.getController();
                    controller.setNombre(mensajeAnalizado[0]);
                    System.out.println("Mi nombre es"+ mensajeAnalizado[0]);
                    controller.recibirDatos(flujo_salida,listaControladores,mon, mensajeAnalizado[1]);
                    controller.getTextAreaRecibir().setText(controller.getTextAreaRecibir().getText()+"\n"+mensajeAnalizado[0]+": "+mensajeAnalizado[2]);
                    tab.setText(mensajeAnalizado[0]);
                    tab.setClosable(true);
                    listaControladores.add(controller);
                    Platform.runLater(new Runnable() {
                        public void run() {
                            aux.añadirTab(tab);
                        }
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
