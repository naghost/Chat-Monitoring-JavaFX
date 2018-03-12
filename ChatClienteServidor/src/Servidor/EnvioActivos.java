package Servidor;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class EnvioActivos extends Thread {
    ArrayList<ClienteModel> lista;
    ArrayList<String> nombre;
    int posiciones;
    boolean salir = false;
    ObjectOutputStream msgToServer ;
    public EnvioActivos(ArrayList<ClienteModel> lista, ArrayList<String> nombre){
        this.lista=lista;
        this.nombre=nombre;
    }


    public void run() {
        super.run();
        posiciones=nombre.size();
        while (!salir){
            if (posiciones!=nombre.size()){
                posiciones=nombre.size();
                for (int i = 0; i<nombre.size();i++){
                    try {
                        msgToServer =lista.get(i).getFlujo_salida();
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
