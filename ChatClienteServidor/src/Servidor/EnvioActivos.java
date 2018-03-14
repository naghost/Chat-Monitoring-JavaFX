package Servidor;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
/*
 * @author Miguel Angel Hernandez Rodriguez
 * @version 1.0
 * La funcion de esta clase es enviar un ArrayList que contiene los usuarios en activo del servidor
 * */

public class EnvioActivos extends Thread {
    ArrayList<ClienteModel> lista;
    ArrayList<String> nombre;
    int posiciones;
    boolean salir = false;
    ObjectOutputStream msgToServer ;

    /*
     * @author Miguel Angel Hernandez Rodriguez
     * @version 1.0
     * Constructor, metodo que recibe los parametros para que el hilo pueda funcionar
     * @param lista ArrayList con todas las conexiones de los clientes
     * @param nombre ArrayList con todos los nombres de los usuarios
     * */
    public EnvioActivos(ArrayList<ClienteModel> lista, ArrayList<String> nombre){
        this.lista=lista;
        this.nombre=nombre;
    }

    /*
     * @author Miguel Angel Hernandez Rodriguez
     * @version 1.0
     * Hilo que envia cada vez que hay un cambio la lista de los usuarios activos
     * */
    public void run() {
        super.run();
        posiciones=nombre.size();
        while (!salir){
            if (posiciones!=nombre.size()){
                posiciones=nombre.size();
                for (int i = 0; i<nombre.size();i++){
                    try {
                       msgToServer =lista.get(i).getFlujo_salida_informacion();
                        msgToServer.writeObject(nombre);
                        msgToServer.flush();
                        msgToServer.reset();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
