import java.io.*;

class Sequenza
{
    static void riproduci(String nome, boolean abilitaUscita)
    {
        riproduci(nome, 0, true);
    }

    static void riproduci(String nome, int t)
    {
        riproduci(nome, t, false);
    }

    static void riproduci(String nome, int t, boolean abilitaUscita)
    {
        char cmd;

        try
        {
            BufferedReader br = new BufferedReader(new FileReader(nome + ".sequenza"));   //inizializza BufferedReader con il file da leggere

            String curStr;   //stringa contenente la linea corrente che viene letta

            while((curStr = br.readLine()) != null)  //controlla la linea retta se raggiunge il nulla (la fine in caso non ci sia ^F) e avanza linea da leggere allo stesso tempo
            {
                if(curStr.startsWith("^F"))  //rompi il ciclo di lettura se inizia con ^F
                    break;

                if(curStr.startsWith(":?"))  //salta stampa (commento)
                    continue;

                if(curStr.startsWith("^p"))  //passaggio al prossimo frame manuale (utile per la guida)
                {
                    do
                    {
                        cmd = Character.toUpperCase(Leggi.unChar());

                        if(cmd == 'E')  //se si preme E si esce dalla sequenza
                        {
                            br.close();
                            Utili.pulisci();
                            return;
                        }

                    } while(cmd != 'A');    //se si preme A si prosegue con il prossimo frame

                    Utili.pulisci();
                    continue;
                }

                if(curStr.startsWith("^n"))  //passa al prossimo frame se inizia con ^n
                {
                    Utili.dormi(t); //pausa tra un frame e l'altro
                    Utili.pulisci();
                    continue;
                }

                System.out.println(curStr);  //stampa linea corrente
            }

            br.close(); //chiudi lo stream
        }
        catch(Exception e)
        {
            System.out.println("Errore lettura del file: " + nome);
            Utili.dormi(1000);
        }
    }
}