package Servidor;

import java.util.ArrayList;
import java.util.Scanner;

public class Servidor {
    ArrayList<ClienteModel> clientes = new ArrayList<>();
    int puerto;
    Scanner sc = new Scanner(System.in);
    boolean error =false;

    public Servidor (){
        do {
            System.out.print("Puerto de escucha: ");
            error = false;
            try{
                String aux;
                aux = sc.nextLine();
                puerto = Integer.parseInt(aux);
            }catch (Exception e){
                error= true;
                System.out.println("El puerto no puede tener letras");
            }
        }while (error);

        EscuchaPeticiones peticiones = new EscuchaPeticiones(puerto, clientes);
        peticiones.start();
    }



}
