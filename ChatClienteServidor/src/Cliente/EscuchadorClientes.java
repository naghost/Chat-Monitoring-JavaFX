package Cliente;

import javafx.scene.control.TabPane;

import java.io.IOException;
import java.io.ObjectInputStream;

public class EscuchadorClientes extends Thread {

    ObjectInputStream flujo_entrada;
    TabPane tabPane;
    boolean salir = false;
    public EscuchadorClientes(ObjectInputStream flujo_entrada, TabPane tabPane) {
        this.flujo_entrada=flujo_entrada;
        this.tabPane=tabPane;
    }

    @Override
    public void run() {
        super.run();
        while (!salir){
            try {
                String mensaje = flujo_entrada.readUTF();
                String mensajeAnalizado[]= mensaje.split("//");

                

            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
