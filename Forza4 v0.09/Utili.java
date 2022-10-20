class Utili {

    public static void dormi(int millis) //funzione delay in millisecondi
    {
        try {
            Thread.sleep(millis);
        } catch(Exception ex) {}
    }

    public static void pulisci() //funzione pulisci schermo
    {
        try {
            new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor(); //https://stackoverflow.com/a/33379766
        } catch(Exception ex) {}
    }

}