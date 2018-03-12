package Servidor;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClienteModel extends Thread {
    Socket conexion;
    String identificador;
    ArrayList<ClienteModel> lista;
    ArrayList<String> nombres;
    Boolean salir = false;
    MonitorLog monitor;
    ObjectInputStream flujo_entrada;
    ObjectOutputStream flujo_salida;


    public void run() {
        super.run();
        while(!salir){

            try {
                if (flujo_entrada.available()>0) {
                    String mensaje = flujo_entrada.readUTF();
                    int i = 0;
                    boolean encontrado = false;
                    String cadena[] = mensaje.split("//");
                    monitor.escribirLog(2, identificador);
                    do {
                        if (lista.get(i).getIdentificador().equals(cadena[1])) {
                            lista.get(i).enviarMensaje(mensaje);
                        }
                        i++;
                    } while (!encontrado && i < lista.size());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {

            }

        }

    }

    public void enviarMensaje(String mensaje) {
        try {
            flujo_salida.writeUTF(mensaje);
            flujo_salida.flush();
            monitor.escribirLog(3,identificador);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public ClienteModel(Socket conexion, String identificador, ArrayList<ClienteModel> lista, MonitorLog monitor, ArrayList<String> usuarios, ObjectInputStream flujo_entrada, ObjectOutputStream flujo_salida){
        this.conexion=conexion;
        this.identificador=identificador;
        this.lista=lista;
        this.monitor = monitor;
        this.nombres = usuarios;
        this.flujo_entrada =flujo_entrada;
        this.flujo_salida =flujo_salida;
    }

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

    public ObjectInputStream getFlujo_entrada() {
        return flujo_entrada;
    }

    public void setFlujo_entrada(ObjectInputStream flujo_entrada) {
        this.flujo_entrada = flujo_entrada;
    }

    public ObjectOutputStream getFlujo_salida() {
        return flujo_salida;
    }

    public void setFlujo_salida(ObjectOutputStream flujo_salida) {
        this.flujo_salida = flujo_salida;
    }
}
