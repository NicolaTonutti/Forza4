/******************************************************

    Classe per la lettura semplice di input da
    tastiera utilizzando uno Scanner

******************************************************/

import java.util.Scanner;

public class F4Leggi
{
    private static Scanner scan = new Scanner(System.in);

    public static void Nulla()  //attende input da tastiera
    {
        try
        {
            System.in.read();
        }
        catch(Exception e) {}
    }

    public static int Int() //ritorna un intero
    {
        return scan.nextInt();
    }

    public static char Char()   //ritorna carattere in posizione 0 nella stringa inserita
    {
        return scan.next().charAt(0);
    }
}