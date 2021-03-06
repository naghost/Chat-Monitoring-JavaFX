package Cliente;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/*
 * @author Miguel Angel Hernandez Rodriguez
 * @version 1.0
 * Clase encargada de gestionar la ventanda principal del chat
 */

public class ChatController {
    @FXML
    TableView<UsuariosModel> tabla;
    @FXML
    TableColumn<UsuariosModel, String> column;
    @FXML
    TabPane tabPane;

    Socket conexion;
    Socket conexionDatos;

    DataInputStream flujo_entrada;
    DataOutputStream flujo_salida;

    ObjectInputStream flujo_entrada_objetos;
    FXMLLoader fxmlLoader =null;

    ArrayList<PestañaController> clientes = new ArrayList<>();

    MonitorLog mon;
    String usuario;
    /*
     *
     * @author Miguel Angel Hernandez Rodriguez
     * @version 1.0
     * metodo que se encarga de controlar que no muestra ninguna tab de mas
     */

    @FXML
    public void crearTab() {

            try {
                boolean existe = false;
                String nombre = tabla.getSelectionModel().getSelectedItem().getUsuario();
                for (int i = 0; i < clientes.size(); i++) {
                    if (clientes.get(i).getNombre().equals(nombre)) {
                        existe = true;
                    }
                }
                if (!existe) {

                    fxmlLoader = new FXMLLoader(getClass().getResource("Pestaña.fxml"));
                    Tab tab = (Tab) fxmlLoader.load();
                    PestañaController controller = (PestañaController) fxmlLoader.getController();
                    controller.setNombre(nombre);
                    controller.recibirDatos(flujo_salida,clientes,mon, usuario);
                    tab.setText(nombre);
                    tab.setClosable(true);
                    tabPane.getTabs().add(tab);
                    clientes.add(controller);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

    }
    /*
     * @author Miguel Angel Hernandez Rodriguez
     * @version 1.0
     * Constructor, recibe datos de flujo y nombre de usuario
     * @param conexion Socket para realizar la conexion
     * @param flujo_entrada objeto encargado de datos
     * @param flujo_salida objeto encargado del envio de datos
     * @param flujo_entrada_objetos encargado de recibir las actualizaciones de usuarios
     * @param usuario String con el usuario que inicia el programa
     * */
    public void Conexion(Socket conexion, DataInputStream flujo_entrada, DataOutputStream flujo_salida, ObjectInputStream flujo_entrada_objetos, String usuario){
        this.conexion = conexion;
        this.flujo_entrada=flujo_entrada;
        this.flujo_salida=flujo_salida;
        this.flujo_entrada_objetos=flujo_entrada_objetos;
        this.usuario=usuario;
        mon = new MonitorLog(this.usuario);
        ActualizarActivos actualizar = new ActualizarActivos(tabla,column,conexionDatos,flujo_entrada_objetos,mon);
        actualizar.start();
        EscuchadorClientes escuchar = new EscuchadorClientes(flujo_entrada, clientes,tabPane,flujo_salida,mon, this);
        escuchar.start();
    }

    /**
     * Zona de getters y setters
     *
     */

    public void añadirTab(Tab tab){
        tabPane.getTabs().add(tab);
    }
    public TableView<UsuariosModel> getTabla() {
        return tabla;
    }

    public void setTabla(TableView<UsuariosModel> tabla) {
        this.tabla = tabla;
    }

    public TableColumn<UsuariosModel, String> getColumn() {
        return column;
    }

    public void setColumn(TableColumn<UsuariosModel, String> column) {
        this.column = column;
    }

    public TabPane getTabPane() {
        return tabPane;
    }

    public void setTabPane(TabPane tabPane) {
        this.tabPane = tabPane;
    }

    public Socket getConexion() {
        return conexion;
    }

    public void setConexion(Socket conexion) {
        this.conexion = conexion;
    }

    public Socket getConexionDatos() {
        return conexionDatos;
    }

    public void setConexionDatos(Socket conexionDatos) {
        this.conexionDatos = conexionDatos;
    }

    public DataInputStream getFlujo_entrada() {
        return flujo_entrada;
    }

    public void setFlujo_entrada(DataInputStream flujo_entrada) {
        this.flujo_entrada = flujo_entrada;
    }

    public DataOutputStream getFlujo_salida() {
        return flujo_salida;
    }

    public void setFlujo_salida(DataOutputStream flujo_salida) {
        this.flujo_salida = flujo_salida;
    }

    public ObjectInputStream getFlujo_entrada_objetos() {
        return flujo_entrada_objetos;
    }

    public void setFlujo_entrada_objetos(ObjectInputStream flujo_entrada_objetos) {
        this.flujo_entrada_objetos = flujo_entrada_objetos;
    }

    public FXMLLoader getFxmlLoader() {
        return fxmlLoader;
    }

    public void setFxmlLoader(FXMLLoader fxmlLoader) {
        this.fxmlLoader = fxmlLoader;
    }

    public ArrayList<PestañaController> getClientes() {
        return clientes;
    }

    public void setClientes(ArrayList<PestañaController> clientes) {
        this.clientes = clientes;
    }

    public MonitorLog getMon() {
        return mon;
    }

    public void setMon(MonitorLog mon) {
        this.mon = mon;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
