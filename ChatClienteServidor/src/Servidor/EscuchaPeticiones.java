package Servidor;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
/*
 * @author Miguel Angel Hernandez Rodriguez
 * @version 1.0
 * Clase que se encarga de escuchar las peticiones de los usuarios
 * */

public class EscuchaPeticiones extends Thread {
    ServerSocket escuchar;
    ServerSocket Informacion;
    Boolean salir = false;
    ArrayList <ClienteModel> clientes;
    ArrayList<String> usuarios = new ArrayList<>();
    MonitorLog monitor = new MonitorLog();

    /*
     * @author Miguel Angel Hernandez Rodriguez
     * @version 1.0
     * Metodo que se ejecuta como hilo encargado de almacenar las conexiones con el servidor y los datos de los usuarios
     * */
    public void run() {
        super.run();
        EnvioActivos activos = new EnvioActivos(clientes, usuarios);
        activos.start();
        do{
            ClienteModel cliente;
            Socket socket;
            String identificador = "";
            try {
                socket = escuchar.accept();
                DataOutputStream flujo_salida=new DataOutputStream(socket.getOutputStream());
                DataInputStream flujo_entrada = new DataInputStream(socket.getInputStream());
                identificador=flujo_entrada.readUTF();
                boolean encontrado = false;
                System.out.println(identificador);
                int i =0;
                while (!encontrado && i<clientes.size()){
                    if (clientes.get(i).getIdentificador().equals(identificador)){
                        encontrado = true;
                    }
                    i++;
                }
                if (encontrado){
                    flujo_salida.writeUTF("DIE");
                    flujo_salida.close();
                    flujo_entrada.close();
                    socket.close();
                    monitor.escribirLog(1,"Servidor");
                }else {
                    flujo_salida.writeUTF("OK");
                    flujo_salida.flush();
                    monitor.escribirLog(0,"Servidor");
                    Socket info = Informacion.accept();
                    ObjectOutputStream flujo_salida_informacion =new ObjectOutputStream(info.getOutputStream());
                    cliente = new ClienteModel(socket, identificador,clientes,monitor,usuarios,flujo_entrada,flujo_salida, flujo_salida_informacion);
                    cliente.start();
                    clientes.add(cliente);
                    usuarios.add(identificador);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }while (!this.salir);

    }
    /*
     * @author Miguel Angel Hernandez Rodriguez
     * @version 1.0
     * Constructor, recibe los parametros necesarios para la escucha de peticiones
     * @param puerto Integer que contiene el puerto por los que se van a escuchar las peticiones de conexion
     * @param clientes ArrayList con la informacion de los clientes
     * */
    public EscuchaPeticiones(int puerto, ArrayList <ClienteModel> clientes){
        try {
            this.escuchar = new ServerSocket(puerto);
            puerto++;
            this.Informacion= new ServerSocket(puerto);
            this.clientes = clientes;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
