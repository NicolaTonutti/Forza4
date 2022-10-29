class Forza4{

    private static int COL = 7;
    private static int RIG = 6;

    public static void main(String[] args)
    {
        Utili.pulisci();

        Sequenza.riproduci("risorse/avvio", 75);  //riproduce animazione schermata di avvio, con 75 ms di delay tra un frame e l'altro
        Utili.dormi(1000);

        menu();
    }

    static void menu()
    {
        Utili.pulisci();
        Sequenza.riproduci("risorse/menu", 75);    //riproduce animazione schermata del menu, con 150 ms di delay tra un frame e l'altro

        int mode;
        
        Interfaccia.Lista(true, true,
            "Gioca",
            "Aiuto",
            "",
            "Esci"
        );

        //scelta opzione
        do
        {
            mode = Leggi.unInt();
            
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
        Sequenza.riproduci("risorse/guida", true);
        menu();
    }


    static void menu_gamemode()
    {
        Utili.pulisci();

        int gamemode;

        Interfaccia.Pannello(5,
            "Scegli modalità di gioco"
        );
        
        Interfaccia.Lista(true, true,
            "Player Vs AI",
            "Player Vs Player",
            "",
            "Indietro"
        );

        //scelta modalità di gioco
        do
        {
            gamemode = Leggi.unInt();
        
        } while(gamemode <= 0 || gamemode > 3);

        //torna al menu se si vuole uscire
        if(gamemode == 3)
        {
            menu();
            return;
        }
        
        if(gamemode == 2)
            gioco(gamemode, 0);    //inizia il gioco con la modalità scelta
        else
            menu_diffic();
    }
    
    
    static void menu_diffic()
    {
        Utili.pulisci();

        int diffic;

        Interfaccia.Pannello(5,
            "Scegli modalità di gioco"
        );
        
        Interfaccia.Lista(true, true,
            "Facile",
            "Medio",
            "Difficile",
            "",
            "Indietro"
        );

        //scelta modalità di gioco
        do
        {
            diffic = Leggi.unInt();
        
        } while(diffic <= 0 || diffic > 4);

        //torna al menu se si vuole uscire
        if(diffic == 4)
        {
            menu();
            return;
        }

        gioco(1, diffic);    //inizia il gioco con la modalità scelta
    }
    
    

    static void menu_pausa()
    {
        int mode;
        
        //scelta opzione
        do
        {
            Utili.pulisci();
            Interfaccia.Pannello(10,
                "PAUSA"
            );

            Interfaccia.Lista(false, true,
                "Riprendi",
                "",
                "Ritorna al menu principale"
            );

            mode = Leggi.unInt();

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
        int[] spazio_col = new int[COL]; //Array che indica quanto spazio c'è in ogni colonna
        
        azzera(m, spazio_col);
        
        int giocatore = 1;
        int pos = 0;
        
        while(true) //Loop di gioco
        {
            if(gamemode == 1 && giocatore == 2){ //Se sei in modalita' Player vs Computer e tocca al computer
                pos = ai(m, spazio_col, diffic); //Fai scegliere la mossa all'intelligenza artificiale
            }
            else{
                do
                {
                    pos = controlloInput(m, giocatore);

                    if(spazio_col[pos] < 0)
                    {
                        Interfaccia.Pannello(
                            "Spazio occupato!"
                        );
                        Utili.dormi(500);
                    }
                    
                } while(spazio_col[pos] < 0);
            }

            m[spazio_col[pos]][pos] = giocatore; //1 significa che c'è il gettone del giocatore 1, 2 = G2 oppure computer 
            spazio_col[pos]--; //fa capire al programma che c'è meno spazio nella colonna
            
            int vincitore = controlla4(m, giocatore);
            if(vincitore >= 1){ //Se la funzione controlla4 ritorna un numero uguale o maggiore a 1 significa chequalcuno ha vinto

                Utili.pulisci();
                stampa(m, pos, giocatore);
                
                if(gamemode == 1 && vincitore == 2){ //Se stai giocando in modalita Player vs computer e il vincitore e' il computer
                    Interfaccia.Pannello(
                        "Il computer ha vinto!",
                        "Andrà meglio la prossima volta"
                    );    //Stampa che il computer ha vinto
                }
                else if(vincitore == 3){ //...Altrimenti se e' pareggio stampa che c'e' pareggio
                    System.out.println("Pareggio");
                }
                else{ //...Altrimenti se ha vinto un umano stampa che l'umano ha vinto e non il computer
                    Interfaccia.Pannello(
                        "Vince il Giocatore " + vincitore,
                        "Congratulazioni!"
                    );    //Stampa chi ha vinto
                }
                
                Utili.dormi(1000);
                menu();
                return;
            }
            
            giocatore = ((giocatore+1)%(4-giocatore)); //a fine turno Cambia giocatore
        }
    }

    static int controlloInput(int[][] m, int giocatore) //fai scegliere la posizione
    {
        int pos = 0;
        char cmd;

        do  //loop di comandi
        {
            Utili.pulisci();
            stampa(m, pos, giocatore);

            //comandi per controllare la posizione
            cmd = Character.toUpperCase(Leggi.unChar());

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


    static void updateSpazioCol(int[][] m, int[] spazio_col){ //metodo per aggiornare l'array dello spazio in una colonna
        for(int i = 0; i <= 6; i++){
            for(int j = 5; j>=0; j--){
                spazio_col[i] = -1; //se la colonna e' piena diventa -1
                if(m[j][i] == 0){
                    spazio_col[i] = j; //aggiorna valore di spazio della colonna
                    break;
                }            
            }
        }
    }


    static void stampa(int m[][], int posizione, int giocatore){ //Metodo per stampare

        //stampa interfaccia
        Interfaccia.Pannello(5,
            "TURNO: Giocatore " + giocatore,
            "",
            "Controlli:",
            "  A - D : Sposta cursore",
            "  S : Conferma posizione",
            "",
            "  P : Pausa partita"
        );

        char c;
        char bx;

        for(int n=1; n<=COL; n++)
        {
            System.out.print("  " + n + " ");
        }

        System.out.print("\n");

        //ciclo per stampare il cursore indicatore della posizione
        for(int s=0; s<COL; s++)
        {
            if(posizione == s)
                System.out.print("  ^ ");
            else
                System.out.print("    ");
        }

        System.out.print("\n\n");

        //utilizza caratteri unicode per la stampa di caratteri della tabella
        
        for(int i = 0; i < RIG; i++)
        {
            for(int j = 0; j < COL; j++)
            {
                if(m[i][j] == 1){ //gettone Giocatore 1
                    c = '@';
                }
                else if(m[i][j] == 2){ //gettone Giocatore 2
                    c = '#';
                }
                else {
                    c = ' ';
                }
                
                System.out.print('\u2502');
                System.out.print(" " + c + " ");
            }

            System.out.print('\u2502');

            System.out.print("\n");

            //stampa della griglia
            for(int k=0; k<29; k++)
            {
                if(k%4 > 0)
                {
                    bx = '\u2500';
                }
                else
                {
                    if(k==0)
                        bx = ((i==RIG-1) ? '\u2514' : '\u251C');

                    else if(k==28)
                        bx = ((i==RIG-1) ? '\u2518' : '\u2524');

                    else
                        bx = ((i==RIG-1) ? '\u2534' : '\u253C');

                }

                System.out.print(bx);
            }
            System.out.print("\n");
        }
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
        int[] spazio_col = {0,0,0,0,0,0,0};
        updateSpazioCol(m,spazio_col);
        for(int i = 0; i < 7; i++){
            if(spazio_col[i] > -1){
                break;
            }
            else if(i == 6){
                return 3; //ritorna pareggio perche' la matrice e' piena
            }
        }
        
        return 0; //Returna 0 se nessuno ha ancora vinto
    }
    
    static int ai(int m[][], int spazio_col[], int diffic){ //Intelligenza artificiale
        int[] opzioni = {0,0,0,0,0,0,0}; //La priorita' di ogni mossa
        int mossa;        
        mossa = (int)Math.floor(Math.random()*7);  //genera numeri randomici da 0 a 6
        int nMossePrev = 2;  //Numero di mosse da prevedere (1 = calcola solo la mossa successiva, 4 = calcola le 4 mosse successive, ecc...)
        
        if(diffic == 1)       
            nMossePrev = 2; 
        else if(diffic == 2)       
            nMossePrev = 4;
        else if(diffic == 3)       
            nMossePrev = 6;
        
        for(int i = 0; i<7; i++){
            opzioni[i] = prevediMosse(m, i, nMossePrev, 2, 0);
            if(spazio_col[i] <= -1){
                opzioni[i] = -999999999; //rendi impossibile posizionare un gettone in una colonna piena                
            }
            //System.out.println("questo e' valore " + i + ": " + opzioni[i]);  //Linea di debug
        }    
        
        //Scegli la mossa con la priorita' piu' alta
        int max = -999999999;
        for(int i = 0; i < 7; i++){
            if(opzioni[i] > max || (opzioni[i] == max && Math.random() >= 0.5 ) ){
                mossa = i;
                max = opzioni[i]; //aggiorna max
            }
        }
        
        
        return mossa;
    }
    
    
    static int prevediMosse(int m[][], int col, int nMossePrev, int giocatore, int accw){ //matrice temporanea, colonna dove viene inserito il gettone, numero di mosse da calcolare, giocatore che compie la mossa, accumulatore vittorie (incrementa ogni volta che viene trovata una mossa vincente)
        int[] spazio_col = new int[7];
        updateSpazioCol(m, spazio_col); //ottieni il valore dello spazio delle colonne
        
        int[][] tempMatr = new int[6][7]; //crea una matrice temporanea dove vengono copiati i valori della matrice m
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 7; j++){
                tempMatr[i][j] = m[i][j];
            }
        }
        
        
        if(spazio_col[col] >= 0){
            tempMatr[spazio_col[col]][col] = giocatore; //aggiungi una pedina nel tabellone di gioco simulato
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