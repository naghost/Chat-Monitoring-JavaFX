package Servidor;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
/*
 * @author Miguel Angel Hernandez Rodriguez
 * @version 1.0
 * Clase encargada de la gestion de la escritura en los archivos de registro
 * */
public class MonitorLog {
    Boolean ocupado = false;
    FileWriter fichero = null;
    String [] Mensajes;
    PrintWriter pw;

    /*
     * @author Miguel Angel Hernandez Rodriguez
     * @version 1.0
     * Constructor, No recibe ningun parametro simplemente rellena el Array con los posibles mensajes y comprueba que el archivo en el que se va a escribir existe
     * */

    public MonitorLog(){
        Mensajes= new String[]{": Conexion aceptada", ": Conexion rechazada identificador duplicado",": Recibo mensaje", ": Envio mensaje", ": Conexion cerrada"};
        File archivo = new File("ActividadServidor.log");
        if (!archivo.exists()){
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * @author Miguel Angel Hernandez Rodriguez
     * @version 1.0
     * Metodo encargado de sincronizar la escritura en los archivos de registros
     * @param mensaje Integer con la posicion del mensaje que quiero guardar
     * @param identificador Quien escribe el mensaje
     * */

    public synchronized  void escribirLog(int mensaje, String identificador){
        while (ocupado){
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        ocupado = true;
        try {
            fichero = new FileWriter("ActividadServidor.log", true);
            pw = new PrintWriter(fichero );
            Date fechaActual = new Date();
            DateFormat hourdateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
            pw.println(String.valueOf(hourdateFormat.format(fechaActual))+" "+identificador+Mensajes[mensaje]);
            pw.close();
            fichero.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ocupado =false;
        notify();
    }
}
