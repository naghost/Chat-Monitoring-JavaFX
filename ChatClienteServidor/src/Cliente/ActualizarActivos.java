package Cliente;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
/*
 * @author Miguel Angel Hernandez Rodriguez
 * @version 1.0
 * Clase que se encarga de la Actualizacion de la tabla que muestra los usuarios
 **/
public class ActualizarActivos extends Thread {
    TableView<UsuariosModel> tabla;
    TableColumn<UsuariosModel, String> column;
    Socket conexion;
    ArrayList<String> nombres;
    boolean salir =false;
    ObservableList<UsuariosModel> lista;
    ObjectInputStream flujo_entrada = null;
    MonitorLog mon;
    /*
     * @author Miguel Angel Hernandez Rodriguez
     * @version 1.0
     * Este metodo que se ejecuta como un hilo actualiza con un ArrayList que recibe del servidor la lista de usuarios activos
     * */
   public void run() {
        super.run();
        while (!salir) {
            ObservableList<UsuariosModel> lista=null;
            ArrayList<UsuariosModel> aux = new ArrayList<>();
            try {
                nombres = new ArrayList<>();
                nombres = (ArrayList<String>) flujo_entrada.readUnshared();
                mon.escribirLog(0,"Sistema");

                System.out.println(nombres.size());
                for (int i = 0; i < nombres.size(); i++) {
                    UsuariosModel model = new UsuariosModel(nombres.get(i));
                    aux.add(model);
                }
                this.tabla.getItems().clear();
                lista = FXCollections.observableArrayList(aux);
                this.tabla.setItems(lista);
                this.tabla.refresh();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    /*
     * @author Miguel Angel Hernandez Rodriguez
     * @version 1.0
     * Constructor, Recibe los datos basicos para poder actualizar la tabla
     * @param tabla Elemento visual de la tabla
     * @param column Elemento visual dentro de la tabla
     * @param conexion socket que tiene la conexion con el servidor
     * @param flujo_entrada el flujo de entrada de los mensajes del servidor
     * @param mon objeto que se encarga de gestionar la escritura en el archivo de log
     */

    public ActualizarActivos(TableView<UsuariosModel> tabla, TableColumn<UsuariosModel, String> column, Socket conexion, ObjectInputStream flujo_entrada, MonitorLog mon) {
        this.tabla = tabla;
        this.column=column;
        this.conexion=conexion;
        this.column.setCellValueFactory(cellData -> cellData.getValue().usuarioProperty());
        this.flujo_entrada =flujo_entrada;
        this.mon = mon;
    }
}
