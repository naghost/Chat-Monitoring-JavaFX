package Cliente;


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

    public MonitorLog(){
        try {
            fichero = new FileWriter("ActividadServidor.log", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mensajes= new String[]{": Conexion aceptada", ": Conexion rechazada identificador duplicado",": Recibo mensaje", ": Envio mensaje", ": Conexion cerrada"};
    }

    public synchronized  void escribirLog(int mensaje, String identificador){
        while (ocupado){
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        ocupado = true;
        PrintWriter pw = new PrintWriter(fichero );
        Date fechaActual = new Date();
        DateFormat hourdateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        pw.println(String.valueOf(hourdateFormat.format(fechaActual))+" "+identificador+Mensajes[mensaje]);
        pw.close();
        ocupado =false;
        notify();
    }
}
