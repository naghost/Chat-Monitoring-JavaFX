package Servidor;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class EscuchaPeticiones extends Thread {
    ServerSocket escuchar;
    ServerSocket Informacion;
    Boolean salir = false;
    ArrayList <ClienteModel> clientes;
    ArrayList<String> usuarios = new ArrayList<>();
    MonitorLog monitor = new MonitorLog();

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
