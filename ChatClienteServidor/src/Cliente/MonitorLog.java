package Cliente;


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
 * Clase encargada de gestionar la escritura de datos en el archivo de log
 * */

public class MonitorLog {
    Boolean ocupado = false;
    FileWriter fichero = null;
    String [] Mensajes;
    String archivo;
    PrintWriter pw = null;
    /*
     * @author Miguel Angel Hernandez Rodriguez
     * @version 1.0
     * Constructor crea el archivo en el que se va a escribir
     * @param usuario nombre del usuario que ejecuta el programa
     * */
    public MonitorLog(String usuario){

        Date fechaActual = new Date();
        DateFormat hourdateFormat = new SimpleDateFormat("HH-mm-ss-dd-MM-yyyy");
                archivo = "ActividadCliente_"+usuario+"_"+hourdateFormat.format(fechaActual)+".log";

                Mensajes= new String[]{": Recibo lista de usuarios", ": Envio mensaje",": Recibo mensaje"};

        File archivoAux = new File(archivo);
        try {
            archivoAux.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * @author Miguel Angel Hernandez Rodriguez
     * @version 1.0
     * metodo encargado de escribir en el log
     * @param mensaje indice del mensaje que se va a mostrar
     * @param identificador nombre de quien escribe el mensaje
     * */

    public synchronized  void escribirLog(int mensaje, String identificador){
        while (ocupado){
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        try {

            fichero = new FileWriter(archivo, true);
            ocupado = true;
            pw = new PrintWriter(fichero);
            Date fechaActual = new Date();
            DateFormat hourdateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
            pw.println(String.valueOf(hourdateFormat.format(fechaActual))+" "+identificador+Mensajes[mensaje]);
            pw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        ocupado =false;
        notify();
    }
}
