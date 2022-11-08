/******************************************************

    Forza 4

******************************************************/

class Forza4{

    private static int COL = 7;
    private static int RIG = 6;

    public static void main(String[] args)
    {
        Utili.pulisci();
        
        Sequenza.riproduci("risorse/avvio", 75);  //riproduce sequenza schermata di avvio, avanzamento frame ogni 75 ms
        Utili.dormi(1000);

        menu();
    }

    static void menu()
    {
        int mode;
        
        do
        {
            Utili.pulisci();
            Sequenza.riproduci("risorse/menu", 75);    //riproduce sequenza schermata del menu, avanzamento frame ogni 75 ms
            
            Interfaccia.Separatore(64);
            System.out.print("\n");
            
            Interfaccia.Lista(true, true, 4,
                "Gioca",
                "Aiuto",
                "",
                "Esci"
            );
            
            //scelta opzione
            mode = F4Leggi.Int();

        } while(mode <= 0 || mode > 3);

        switch(mode)
        {
            case 1:
                menu_gamemode();

            case 2:
                guida();
            
            case 3:
                System.exit(0);
            
            default: return;
        }
    }

    static void guida()
    {
        Utili.pulisci();
        Sequenza.riproduci("risorse/guida", true);  //riproduce sequenza della guida, avanzamento frame manuale
        menu(); //torna al menu alla fine della guida
    }

    static void menu_gamemode()
    {
        int gamemode;
        
        do
        {
            Utili.pulisci();


            Interfaccia.Pannello(5,
                "Scegli modalità di gioco"
            );
            
            Interfaccia.Lista(true, true, 4,
                "Player Vs AI",
                "Player Vs Player",
                "",
                "Indietro"
            );

            //scelta modalità di gioco
            gamemode = F4Leggi.Int();
        
        } while(gamemode <= 0 || gamemode > 3);

        switch(gamemode)
        {
            case 1:
                menu_diffic();
            
            case 2:
                gioco(gamemode, 0); //inizia il gioco con la modalità scelta
            
            case 3:
                menu();         
        }
    }
    
    static void menu_diffic()
    {
        int diffic;
        
        do
        {
            Utili.pulisci();


            Interfaccia.Pannello(5,
                "Scegli modalità di gioco"
            );
            
            Interfaccia.Lista(true, true, 4,
                "Facile",
                "Medio",
                "Difficile",
                "",
                "Indietro"
            );

            //scelta modalità di gioco
            diffic = F4Leggi.Int();
        
        } while(diffic <= 0 || diffic > 4);

        //torna indietro
        if(diffic == 4)
            menu_gamemode();

        gioco(1, diffic);    //inizia il gioco con la modalità scelta
    }
    
    static void menu_pausa()
    {
        int mode;
        
        do
        {
            Utili.pulisci();
            
            
            Interfaccia.Pannello(15,
                "PAUSA"
            );
            
            Interfaccia.Lista(false, true, 4,
                "Riprendi",
                "",
                "Ritorna al menu principale"
            );
                
            //scelta opzione
            mode = F4Leggi.Int();
                
            switch(mode)
            {
                case 1:
                    return;

                case 2:
                    menu();
                            
                default: break;
            }
        
        } while(mode <= 0 || mode > 2);
    }

    static void gioco(int gamemode, int diffic){

        Utili.pulisci();
        
        int[][] m = new int[RIG][COL];        //Matrice di gioco
        int[] spazioCol = new int[COL]; //Array che indica quanto spazio c'è in ogni colonna

        azzera(m, spazioCol);
        
        int turno = 0;
        int giocatore;

        int pos = 0;
        int[] ultimaPos = {0, 0};
        
        while(true) //Loop di gioco
        {
            giocatore = (turno % 2) + 1;
            
            if(gamemode == 1 && giocatore == 2){ //Se sei in modalita' Player vs Computer e tocca al computer
                pos = ai(m, spazioCol, diffic); //Fai scegliere la mossa all'intelligenza artificiale
            }
            else{
                do
                {
                    pos = controlloInput(m, giocatore, ultimaPos[giocatore-1]);
                    
                    if(spazioCol[pos] < 0)
                    {
                        Interfaccia.Pannello(5,
                        "Spazio occupato!"
                        );
                        Utili.dormi(500);
                    }
                    
                } while(spazioCol[pos] < 0);
            }
            
            ultimaPos[giocatore-1] = pos;
            
            mettiPedina(m, spazioCol, giocatore, pos);
            
            int vincitore = controlla4(m, giocatore);
            if(vincitore >= 1){ //Se la funzione controlla4 ritorna un numero uguale o maggiore a 1 significa chequalcuno ha vinto
                
                Utili.pulisci();
                stampa(m, pos, giocatore);

                System.out.print("\n");
                
                if(gamemode == 1 && vincitore == 2){ //Se stai giocando in modalita Player vs computer e il vincitore e' il computer stampa che il computer ha vinto
                    Interfaccia.Pannello(5,
                        "Il computer ha vinto!",
                        "Andrà meglio la prossima volta"
                    );
                }
                else if(vincitore == 3){ //...Altrimenti se e' pareggio stampa che c'e' pareggio
                    Interfaccia.Pannello(5,
                        "Pareggio"
                    );
                }
                else{ //...Altrimenti se ha vinto un umano stampa che l'umano ha vinto e non il computer
                    Interfaccia.Pannello(5,
                        "Vince il Giocatore " + vincitore,
                        "Congratulazioni!"
                    );
                }
                
                F4Leggi.Nulla();

                menu();
                return;
            }

            turno++;
        }
    }

    static void mettiPedina(int[][] m, int[] spazioCol, int giocatore, int pos)
    {
        for(int i=0; i<=spazioCol[pos]; i++)    //itera ogni spazio
        {
            m[i][pos] = giocatore;  //imposta pedina

            if(i>0)
                m[i-1][pos] = 0;    //ripulisce pedina precedente per l'effetto caduta (non molto conveniente ma funziona)

            Utili.pulisci();
            stampa(m, pos, giocatore);
            Utili.dormi(25);
        }

        spazioCol[pos]--;   //abbiamo occupato questo spazio quindi diminuisci spazi disponibili nella colonna
    }
    
    static int controlloInput(int[][] m, int giocatore, int pos) //fai scegliere la posizione
    {
        char cmd;

        do  //loop di comandi
        {
            Utili.pulisci();
            stampa(m, pos, giocatore);

            //comandi per controllare la posizione
            cmd = Character.toUpperCase(F4Leggi.Char());

            switch(cmd)
            {
                /* PAUSA */
                case 'P': {
                    menu_pausa();
                    break;
                }
                
                /* SPOSTAMENTO DESTRA */
                case 'D': {
                    pos = (pos+1 < COL) ? pos + 1 : pos;
                    break;
                }

                /* SPOSTAMENTO SINISTRA */
                case 'A': {
                    pos = (pos-1 >= 0) ? pos - 1 : pos;
                    break;
                }

                /* CONFERMA POSIZIONE */
                case 'S': {
                    return pos;
                }

                default : {
                    Interfaccia.Pannello(
                        "Comando sconosciuto"
                    );
                    Utili.dormi(500);
                    Utili.pulisci();
                    break;
                }
            }

        } while(true);
    
    }

    static void updateSpazioCol(int[][] m, int[] spazioCol){ //metodo per aggiornare l'array dello spazio in una colonna
        for(int i = 0; i <= 6; i++){
            for(int j = 5; j>=0; j--){
                spazioCol[i] = -1; //se la colonna e' piena diventa -1
                if(m[j][i] == 0){
                    spazioCol[i] = j; //aggiorna valore di spazio della colonna
                    break;
                }            
            }
        }
    }

    static char simboloGiocatore(int g)
    {
        //identifica il carattere corrispondente al giocatore
        return
        (
            (g == 1) ? '@' :
            (g == 2) ? '#' :
            ' '
        );
    }

    static void stampa(int m[][], int posizione, int giocatore){ //Metodo per stampare

        //stampa interfaccia
        Interfaccia.Pannello(5,
            "TURNO: Giocatore " + giocatore,
            "",
            "Controlli:",
            "   A <> D : Sposta cursore",
            "   S : Conferma posizione",
            "",
            "   P : Pausa partita"
        );

        char c;
        char bx;

        //ciclo per stampare il cursore in posizione
        for(int s=0; s<COL; s++)
        {
            if(posizione == s)
                System.out.print("  " + simboloGiocatore(giocatore) + " ");
            else
                System.out.print("    ");
        }

        System.out.print("\n\n");

        //utilizza caratteri ASCII per la stampa di caratteri della tabella
        
        for(int i = 0; i < RIG; i++)
        {
            for(int j = 0; j < COL; j++)
            {
                c = simboloGiocatore(m[i][j]);
                
                System.out.print('|');
                System.out.print(" " + c + " ");
            }

            System.out.print('|');
            System.out.print("\n");

            //stampa della griglia
            for(int k=0; k<=28; k++)
            {
                bx = ( (k%4>0) ? '-' : '+');

                System.out.print(bx);
            }
            System.out.print("\n");
        }
    }

    static void vittoria(int tipo)
    {

    }
    
    static void azzera(int m[][], int s[]){
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 7; j++){           
                m[i][j] = 0;                //0 significa che non c'è il gettone di alcun giocatore
            }
        }  
        
        for(int j = 0; j < 7; j++){                          
            s[j] = 5; //Numero riga in cui viene piazzato il gettone all'inizio del gioco (RIG - 1)
        }
    }
    
    static int controlla4(int m[][], int giocatore){      
            
        //CONTROLLO FORZA 4 ORIZZONTALE E VERTICALE
        int[] cnt_vert = new int[7];
        for(int i = 0; i<6; i++){     //6 = quantita' righe
            int cnt_oriz = 0; //Contatore consecutivo orizzontale
            
            for(int j = 0; j<7; j++){ // 7 = quantita' colonne
              
                if(m[i][j] == giocatore){
                    cnt_oriz++;
                    cnt_vert[j]++;
                    if(cnt_oriz == 4 || cnt_vert[j] == 4){ //VITTORIA 4 IN FILA ORIZZONTALE O VERTICALE
                        return giocatore; //returna un valore in base a chi ha vinto 
                    }
                }
                else{
                    cnt_oriz = 0;
                    cnt_vert[j] = 0;
                }
            }                    
        }
        
        //CONTROLLO DIAGONALI
        //Inclinate verso destra
        for(int i = 0; i<3; i++){     //6 = quantita' righe               
            for(int j = 0; j<=3; j++){ // 7 = quantita' colonne
                
                for(int cntd = 0; cntd < 4; cntd++){
                    if(m[i+cntd][j+cntd] != giocatore){ //Quando capisci che non ci sono 4 pedine in fila in quella posizione, esci dal ciclo
                        break;
                    }
                    else if(cntd >= 3){ //VITTORIA 4 IN FILA DIAGONALE
                        return giocatore; //returna un valore in base a chi ha vinto 
                    }
                }
            }                    
        }
        
        //Inclinate verso sinistra
        for(int i = 0; i<3; i++){     //6 = quantita' righe               
            for(int j = 6; j>=3; j--){ // 7 = quantita' colonne
                
                for(int cntd = 0; cntd < 4; cntd++){
                    if(m[i+cntd][j-cntd] != giocatore){ //Quando capisci che non ci sono 4 pedine in fila in quella posizione, esci dal ciclo
                        break;
                    }
                    else if(cntd >= 3){ //VITTORIA 4 IN FILA DIAGONALE
                        return giocatore; //returna un valore in base a chi ha vinto 
                    }
                }
            }                    
        }

        //CONTROLLO PAREGGIO
        int[] spazioCol = {0,0,0,0,0,0,0};
        updateSpazioCol(m,spazioCol);
        for(int i = 0; i < 7; i++){
            if(spazioCol[i] > -1){
                break;
            }
            else if(i == 6){
                return 3; //ritorna pareggio perche' la matrice e' piena
            }
        }
        
        return 0; //Returna 0 se nessuno ha ancora vinto
    }
    
    static int ai(int m[][], int spazioCol[], int diffic){ //Intelligenza artificiale
        int[] opzioni = {0,0,0,0,0,0,0}; //La priorita' di ogni mossa
        int mossa;        
        mossa = Utili.random(7);
        int nMossePrev = 2;  //Numero di mosse da prevedere (1 = calcola solo la mossa successiva, 4 = calcola le 4 mosse successive, ecc...)
        
        if(diffic == 1)       
            nMossePrev = 2; 
        else if(diffic == 2)       
            nMossePrev = 4;
        else if(diffic == 3)       
            nMossePrev = 6;
        
        for(int i = 0; i<7; i++){
            opzioni[i] = prevediMosse(m, i, nMossePrev, 2, 0);
            if(spazioCol[i] <= -1){
                opzioni[i] = -999999999; //rendi impossibile posizionare un gettone in una colonna piena                
            }
            //System.out.println("questo e' valore " + i + ": " + opzioni[i]);  //Linea di debug
        }    
        
        //Scegli la mossa con la priorita' piu' alta
        int max = -999999999;
        for(int i = 0; i < 7; i++){
            if(opzioni[i] > max || (opzioni[i] == max && Utili.random() >= 0.5 ) ){
                mossa = i;
                max = opzioni[i]; //aggiorna max
            }
        }
                
        return mossa;
    }
    
    static int prevediMosse(int m[][], int col, int nMossePrev, int giocatore, int accw){ //matrice temporanea, colonna dove viene inserito il gettone, numero di mosse da calcolare, giocatore che compie la mossa, accumulatore vittorie (incrementa ogni volta che viene trovata una mossa vincente)
        int[] spazioCol = new int[7];
        updateSpazioCol(m, spazioCol); //ottieni il valore dello spazio delle colonne
        
        int[][] tempMatr = new int[6][7]; //crea una matrice temporanea dove vengono copiati i valori della matrice m
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 7; j++){
                tempMatr[i][j] = m[i][j];
            }
        }
        
        
        if(spazioCol[col] >= 0){
            tempMatr[spazioCol[col]][col] = giocatore; //aggiungi una pedina nel tabellone di gioco simulato
        }
        else{
            return accw;    //se pero' non c'e' spazio nella colonna ritorna l'accumulatore delle vittorie
        }
        
       
        if(controlla4(tempMatr, giocatore) == giocatore){ //controlla se la pedina aggiunta fa vincere la partita a chi l'ha giocata
            if(giocatore == 2){
                return accw + (int)(Math.pow(8, nMossePrev-1)); //aumenta l'accumulatore di vittorie di 51
            }    
            else{
                return accw - (int)(Math.pow(8, nMossePrev-1)); //diminuisci l'accumulatore di vittorie di 50, in questo modo eviti che il giocatore possa fare una mossa vincente
            } 
        }
        
        
        //RICORSIONE per prevedere ulteriori mosse future
        if(nMossePrev > 1){ //solo se le mosse da prevedere non sono finite
            if(giocatore == 2){
                giocatore = 1; //cambia il valore del giocatore
            }
            else{
                giocatore = 2;
            }
            
            for(int i = 0; i < 7; i++){
                
                accw = prevediMosse(tempMatr, i, nMossePrev-1, giocatore, accw); //ricorsione che diminuisce di 1 nMosseprev 
            }
                       
        }
        
        
        //se nMossePrev è a 1 significa che le mosse da prevedere sono finite, quindi si può già ritornare l'accumulatore
        return accw;
    }
}