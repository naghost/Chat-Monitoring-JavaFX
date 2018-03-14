package Cliente;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MonitorLog {
    Boolean ocupado = false;
    FileWriter fichero = null;
    String [] Mensajes;
    String archivo;
    PrintWriter pw = null;
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
