package Servidor;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/*
 * @author Miguel Angel Hernandez Rodriguez
 * @version 1.0
 * Modelo del cliente se utliza para todas las acciones que realizara el cliente (Envio, recepcion de informacion y cerrar conexion)
 * */
public class ClienteModel extends Thread {
    Socket conexion;
    String identificador;
    ArrayList<ClienteModel> lista;
    ArrayList<String> nombres;
    Boolean salir = false;
    MonitorLog monitor;
    DataInputStream flujo_entrada;
    DataOutputStream flujo_salida;
    ObjectOutputStream flujo_salida_informacion;

    /*
     * @author Miguel Angel Hernandez Rodriguez
     * @version 1.0
     * metodo del hilo que siempre va a estar escuchando la informacion del usuario
     * Detecta la entrada de mensajes, si es un mensaje normal lo enviara al destinatario
     * Si es un mensaje de desconexion cerrara su socket y lo sacara de la lista
     * @see enviarMensaje()
     * */
    public void run() {
        super.run();
        while (!salir) {

            try {
                if (flujo_entrada.available() > 0) {
                    String mensaje = flujo_entrada.readUTF();
                    int i = 0;
                    boolean encontrado = false;
                    String cadena[] = mensaje.split("//");
                    System.out.println(mensaje);
                    do {
                        if (cadena.length>0)
                        if (lista.get(i).getIdentificador().equals(cadena[1]) && !cadena[0].equals("Disconnect")) {
                            lista.get(i).enviarMensaje(mensaje);
                            monitor.escribirLog(2, cadena[0]);
                            encontrado=true;
                        }else{
                            if(cadena[0].equals("Disconnect") && lista.get(i).getIdentificador().equals(cadena[1])){
                                encontrado=true;
                                lista.get(i).setSalir(true);
                                lista.remove(i);
                                nombres.remove(i);
                                monitor.escribirLog(4, cadena[1]);
                            }
                        }
                        i++;
                    } while (!encontrado && i < lista.size());
                    System.out.println(encontrado);
                        if (!encontrado){
                            i=0;
                            do{
                                if (lista.get(i).getIdentificador().equals(cadena[0])){
                                    lista.get(i).getFlujo_salida().writeUTF(cadena[1]+"//"+cadena[0]+"//Usuario desconectado");
                                    lista.get(i).getFlujo_salida().flush();
                                    monitor.escribirLog(2, "Server");
                                    encontrado=true;
                                }
                                i++;
                            }while (i<lista.size() && !encontrado);
                        }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    /*
     * @author Miguel Angel Hernandez Rodriguez
     * @version 1.0
     * Este metodo se encarga de enviar los mensajes al usuario del socket
     * @see run()
     * @param mensaje es una variable String que contiene el mensaje que el cliente va a recibir
     * */

    public void enviarMensaje(String mensaje) {
        try {
            flujo_salida.writeUTF(mensaje);
            flujo_salida.flush();
            monitor.escribirLog(3, identificador);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * @author Miguel Angel Hernandez Rodriguez
     * @version 1.0
     * Constructor, recibe todos los datos para que el hilo pueda trabajar
     *
     * @param conexion es el socket que une el cliente con el servidor
     * @param identificador nombre unico del cliente
     * @param lista ArraList con todos los clientes
     * @param monitor objeto monitor para hacer los registros en el log
     * @param usuarios ArrayList con los nombres de usuario de cada cliente
     * @param flujo_entrada con este objeto podremos escuchar mensajes
     * @param flujo_salida con este objeto podremos enviar mensajes
     * @param flujo_salida_informacion con este flujo de salida se utiliza para enviar el arraylist de los clientes conectados
     * */

    public ClienteModel(Socket conexion, String identificador, ArrayList<ClienteModel> lista, MonitorLog monitor, ArrayList<String> usuarios, DataInputStream flujo_entrada, DataOutputStream flujo_salida, ObjectOutputStream flujo_salida_informacion) {
        this.conexion = conexion;
        this.identificador = identificador;
        this.lista = lista;
        this.monitor = monitor;
        this.nombres = usuarios;
        this.flujo_entrada = flujo_entrada;
        this.flujo_salida = flujo_salida;
        this.flujo_salida_informacion = flujo_salida_informacion;
    }

    /*
     * @author Miguel Angel Hernandez Rodriguez
     * @version 1.0
     * Zona Getters y Setters
     * */
    public Socket getConexion() {
        return conexion;
    }

    public void setConexion(Socket conexion) {
        this.conexion = conexion;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public ArrayList<ClienteModel> getLista() {
        return lista;
    }

    public void setLista(ArrayList<ClienteModel> lista) {
        this.lista = lista;
    }

    public ArrayList<String> getNombres() {
        return nombres;
    }

    public void setNombres(ArrayList<String> nombres) {
        this.nombres = nombres;
    }

    public Boolean getSalir() {
        return salir;
    }

    public void setSalir(Boolean salir) {
        this.salir = salir;
    }

    public MonitorLog getMonitor() {
        return monitor;
    }

    public void setMonitor(MonitorLog monitor) {
        this.monitor = monitor;
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

    public ObjectOutputStream getFlujo_salida_informacion() {
        return flujo_salida_informacion;
    }

    public void setFlujo_salida_informacion(ObjectOutputStream flujo_salida_informacion) {
        this.flujo_salida_informacion = flujo_salida_informacion;
    }
}
