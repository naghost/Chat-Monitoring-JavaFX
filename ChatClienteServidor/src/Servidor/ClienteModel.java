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
    DataInputStream flujo_entrada;
    DataOutputStream flujo_salida;
    ObjectOutputStream flujo_salida_informacion;


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

    public void enviarMensaje(String mensaje) {
        try {
            flujo_salida.writeUTF(mensaje);
            flujo_salida.flush();
            monitor.escribirLog(3, identificador);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


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
