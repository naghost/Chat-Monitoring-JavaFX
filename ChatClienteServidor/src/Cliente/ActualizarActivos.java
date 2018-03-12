package Cliente;

import Servidor.ClienteModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ActualizarActivos extends Thread {
    TableView<UsuariosModel> tabla;
    TableColumn<UsuariosModel, String> column;
    Socket conexion;
    ArrayList<String> nombres;
    boolean salir =false;
    ObservableList<UsuariosModel> lista;
    ObjectInputStream flujo_entrada = null;

   public void run() {
        super.run();
        while (!salir) {
            ObservableList<UsuariosModel> lista=null;
            ArrayList<UsuariosModel> aux = new ArrayList<>();
            try {
                nombres = new ArrayList<>();
                nombres = (ArrayList<String>) flujo_entrada.readUnshared();


                System.out.println(nombres.size());
                for (int i = 0; i < nombres.size(); i++) {
                    UsuariosModel model = new UsuariosModel(nombres.get(i));
                    aux.add(model);
                    System.out.println("hola");
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

    public ActualizarActivos(TableView<UsuariosModel> tabla, TableColumn<UsuariosModel, String> column, Socket conexion, ObjectInputStream flujo_entrada) {
        this.tabla = tabla;
        this.column=column;
        this.conexion=conexion;
        this.column.setCellValueFactory(cellData -> cellData.getValue().usuarioProperty());
        this.flujo_entrada =flujo_entrada;
    }
}
