/******************************************************

    Classe per disegnare pannelli e altri componenti
    grafici

******************************************************/

class Interfaccia
{
    //calcola stringa più lungha tra quelle date come argomenti
    private static int lunghezzaRiga(String... args)
    {
        int max = 0;

        for(String str : args)
        {
            if(str.length() > max)
                max = str.length();
        }

        return max;
    }




    //stampa una tabella
    public static void Lista(String... args) {Lista(false, false, 0, args);}
    public static void Lista(boolean anima, String... args) {Lista(anima, false, 0, args); }
    public static void Lista(boolean anima, boolean numera, String... args) {Lista(anima, numera, 0, args); }

    public static void Lista(boolean anima, boolean numera, int spazio, String... elementi)
    {
        int i = 1;

        if(anima)
            Utili.dormi(75);
        
        for(String v : elementi)
        {
            for(int s=0; s<spazio; s++)
                System.out.print(" ");

            if(anima)
                Utili.dormi(50);

            if(numera)
            {
                if (v.length() > 1)
                {
                    System.out.print(i + "> ");
                    i++;
                }
            }

            System.out.print(v);
            System.out.print("\n");
        }
    }

    //stampa un pannello
    public static void Pannello(String... args) {Pannello(0, args);}

    public static void Pannello(int spazio, String... args)
    {
        int lenR = lunghezzaRiga(args);

        for(int i=0; i<lenR+(spazio*2); i++)
            System.out.print("\u2550");
        
        System.out.print("\n");

        for(String str : args)
        {
            for(int s=0; s<spazio; s++)
                System.out.print(" ");
            
            System.out.print(str);
            System.out.print("\n");
        }

        for(int i=0; i<lenR+(spazio*2); i++)
            System.out.print("\u2550");
        
        System.out.print("\n\n");
    }

    //stampa un separatore
    public static void Separatore() {Separatore(32, '\u2550');}
    public static void Separatore(int lunghezza) {Separatore(lunghezza, '\u2550');}

    public static void Separatore(int lunghezza, char ch)
    {
        System.out.print("\n");
        for(int i=0; i<lunghezza; i++)
        {
            System.out.print(ch);
        }
        System.out.print("\n");
    }
}