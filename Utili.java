/******************************************************

    Classe di utilità per pulizia dello schermo,
    pausa e numeri randomici

******************************************************/

class Utili {

    public static void dormi(int millis) //funzione delay in millisecondi
    {
        try
        {
            Thread.sleep(millis);
        }
        catch(Exception e) {}
    }

    public static void pulisci() //funzione pulisci schermo (solo per Windows perchè comando cls funziona solo su terminale windows)
    {
        //Crea istanza del "cmd" (terminale di Windows) con parametro "/c" (esegui comando e termina) e "cls" come comando (usato per pulire il terminale)
        try
        {
            new ProcessBuilder("cmd","/C","cls").inheritIO().start().waitFor(); //https://stackoverflow.com/a/33379766
        }
        catch(Exception e) {}
    }

    public static int random()  //ritorna un numero random
    {
        return (int) Math.random();
    }

    public static int random(int n) //ritorna un numero random da 0 al numero massimo dato
    {
        return (int) Math.floor(Math.random()*n);
    }

}