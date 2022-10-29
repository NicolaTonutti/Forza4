class Interfaccia
{
    //stampa una tabella
    public static void Lista(String... args) {Lista(false, false, args);}   //funzione sovraccarico

    public static void Lista(boolean anima, boolean numera, String... elementi)
    {
        int i = 1;

        if(anima)
            Utili.dormi(75);
        
        for(String v : elementi)
        {
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
    public static void Pannello(String... args) {Pannello(0, args);}    //funzione sovraccarico

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
}