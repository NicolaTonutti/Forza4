/******************************************************

    Classe contenente sequenze di escape
    per colorare il testo

    Sequenze ANSI di escape:
    https://en.wikipedia.org/wiki/ANSI_escape_code

******************************************************/

public class Colori
{
    //RIPRISTINA COLORI
    public static final String RIPRISTINA = "\033[0m";

    //COLORI NORMALI

        //COLORI TESTO
        public static final String TESTO_NORMALE_NERO         = "\033[0;30m";
        public static final String TESTO_NORMALE_ROSSO        = "\033[0;31m";
        public static final String TESTO_NORMALE_VERDE        = "\033[0;32m";
        public static final String TESTO_NORMALE_GIALLO       = "\033[0;33m";
        public static final String TESTO_NORMALE_BLU          = "\033[0;34m";
        public static final String TESTO_NORMALE_MAGENTA      = "\033[0;35m";
        public static final String TESTO_NORMALE_CIANO        = "\033[0;36m";
        public static final String TESTO_NORMALE_BIANCO       = "\033[0;37m";
    
        //COLORI SFONDO
        public static final String SFONDO_NORMALE_NERO         = "\033[40m";
        public static final String SFONDO_NORMALE_ROSSO        = "\033[41m";
        public static final String SFONDO_NORMALE_VERDE        = "\033[42m";
        public static final String SFONDO_NORMALE_GIALLO       = "\033[43m";
        public static final String SFONDO_NORMALE_BLU          = "\033[44m";
        public static final String SFONDO_NORMALE_MAGENTA      = "\033[45m";
        public static final String SFONDO_NORMALE_CIANO        = "\033[46m";
        public static final String SFONDO_NORMALE_BIANCO       = "\033[47m";
    
    //COLORI CHIARI

        //COLORI TESTO
        public static final String TESTO_CHIARO_NERO         = "\033[0;90m";
        public static final String TESTO_CHIARO_ROSSO        = "\033[0;91m";
        public static final String TESTO_CHIARO_VERDE        = "\033[0;92m";
        public static final String TESTO_CHIARO_GIALLO       = "\033[0;93m";
        public static final String TESTO_CHIARO_BLU          = "\033[0;94m";
        public static final String TESTO_CHIARO_MAGENTA      = "\033[0;95m";
        public static final String TESTO_CHIARO_CIANO        = "\033[0;96m";
        public static final String TESTO_CHIARO_BIANCO       = "\033[0;97m";

        //COLORI SFONDO
        public static final String SFONDO_CHIARO_NERO         = "\033[100m";
        public static final String SFONDO_CHIARO_ROSSO        = "\033[101m";
        public static final String SFONDO_CHIARO_VERDE        = "\033[102m";
        public static final String SFONDO_CHIARO_GIALLO       = "\033[103m";
        public static final String SFONDO_CHIARO_BLU          = "\033[104m";
        public static final String SFONDO_CHIARO_MAGENTA      = "\033[105m";
        public static final String SFONDO_CHIARO_CIANO        = "\033[106m";
        public static final String SFONDO_CHIARO_BIANCO       = "\033[107m";
}