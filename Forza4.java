class Forza4{
    
    public static void main(String[] args){
        menu();
    }

    static void menu()
    {
        int mode;
        
        while(true){
                        
            Utili.pulisci();        
    
            System.out.print(""
                +"==========\n"
                +"  Forza4\n"
                +"==========\n\n"
    
                +"1) Gioca\n"
                +"2) Aiuto\n"
                +"3) Esci\n"
            );
    
            do
            {
                mode = Leggi.unInt();
                
            } while(mode <= 0 || mode > 3);
    
            switch(mode)
            {
                case 1: {
                    menu_gamemode();                
                    break;
                }
    
                case 2:
                case 3:
                    System.exit(0);
                
                default: return;
            }
        }
    }

    static void menu_gamemode()
    {
        Utili.pulisci();

        int gmode;
        
        System.out.print(""
            +"==========\n"
            +"  Forza4\n"
            +"==========\n\n"

            +"Scegli modalità di gioco:\n"
            +"1) Player Vs AI\n"
            +"2) Player Vs Player\n\n"

            +"3) Esci\n"
        );

        do
        {
            gmode = Leggi.unInt();
            
        } while(gmode <= 0 || gmode > 3);

        if(gmode == 3)
            menu();
        else if(gmode == 1)
            menu_diffic();
        else
            gioco(2, 0);
    }
    
    static void menu_diffic() //menu di selezione della difficolta'
    {
        Utili.pulisci();

        int diffic;
        
        System.out.print(""
            +"==========\n"
            +"  Forza4\n"
            +"==========\n\n"

            +"Scegli difficoltà:\n"
            +"1) Facile\n"
            +"2) Media\n"
            +"3) Difficile\n\n"

            +"4) Esci\n"
        );

        do
        {
            diffic = Leggi.unInt();
            
        } while(diffic <= 0 || diffic > 4);

        if(diffic == 4)
            menu();

        gioco(1, diffic);
    }

    static void gioco(int gamemode, int diffic){

        Utili.pulisci();
        
        int COL = 7;
        int RIG = 6;
        
        int[][] m = new int[RIG][COL];        //Matrice di gioco
        int[] spazio_col = new int[COL]; //Array che indica quanto spazio c'è in ogni colonna
        
        azzera(m, spazio_col);
        stampa(m, RIG, COL);
        
        int giocatore = 1;
        int mossa;
        
        while(true)
        {
            if(gamemode == 1 && giocatore == 2){ //Se sei in modalita' Player vs Computer e tocca al computer
                System.out.println("Mossa del computer: ");
                mossa = ai(m, spazio_col, diffic); //Fai scegliere la mossa all'intelligenza artificiale
            }
            else{
                do{
                    System.out.println("Giocatore " + giocatore + ":");
                    System.out.println("Inserisci il numero della colonna da 1 a 7");
                    mossa = Leggi.unInt()-1; //Leggi input. (L'utente inserira' un valore da 1 a 7, ma il programma sottrarrà 1 a quel valore così sarà da 0 a 6)
                    
                    if(spazio_col[mossa] < 0){
                        System.out.println("Non c'e' spazio in quella colonna");
                    }
                }while(mossa >= COL || mossa < 0 || spazio_col[mossa] < 0 /* se non c'e' spazio nella colonna*/);
            }
            
            
            m[spazio_col[mossa]][mossa] = giocatore; //1 significa che c'è il gettone del giocatore 1, 2 = G2 oppure computer 
            spazio_col[mossa]--; //fa capire al programma che c'è meno spazio nella colonna
            
            stampa(m, RIG, COL); //Funzione per stampare matrice di gioco
            
            int vincitore = controlla4(m, giocatore);
            if(vincitore >= 1){ //Se la funzione controlla4 ritorna un numero uguale o maggiore a 1 significa chequalcuno ha vinto
                System.out.println("\n-----------------------------------------");
                if(gamemode == 1 && vincitore == 2){ //Se stai giocando in modalita Player vs computer e il vincitore e' il computer
                    System.out.println("Il computer ha vinto!"); //Stampa che il computer ha vinto
                }
                else if(vincitore == 3){ //...Altrimenti se e' pareggio stampa che c'e' pareggio
                    System.out.println("Pareggio");
                }
                else{ //...Altrimenti se ha vinto un umano stampa che l'umano ha vinto e non il computer
                    System.out.println("Giocatore " + vincitore + " ha vinto!"); //Stampa chi ha vinto
                }
                System.out.println("-----------------------------------------\n\n\n");
                System.out.println("premi un carattere e poi invio per continuare");
                Leggi.unChar();
                return;               
            }
            
            giocatore = ((giocatore+1)%(4-giocatore)); //a fine turno Cambia giocatore
            
        }
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
        
    
    static void stampa(int m[][], int RIG, int COL){ //Metodo per stampare

        char c;
        char bx;

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

            //da ottimizzare le ripetizioni
            for(int k=0; k<29; k++)
            {
                if(k%4 > 0)
                {
                    bx = '\u2500';
                }
                else
                {
                    if(k==0)
                    {
                        if(i==RIG-1)
                            bx = '\u2514';
                        else
                            bx = '\u251C';
                    }
                    else if(k==28)
                    {
                        if(i==RIG-1)
                            bx = '\u2518';
                        else
                            bx = '\u2524';
                    }
                    else
                    {
                        if(i==RIG-1)
                            bx = '\u2534';
                        else
                            bx = '\u253C';
                    }
                }
                System.out.print(bx);
            }
            System.out.print("\n");
        }

        // metodo di stampa precedente
        /*
        for(int i = 0; i < RIG; i++){
            for(int j = 0; j < COL; j++){ 
                c = ' ';
                if(m[i][j] == 1){ //gettone Giocatore 1
                    c = '@';
                }
                else if(m[i][j] == 2){ //gettone Giocatore 2
                    c = '#';
                }
            
                System.out.print("| " + c + " ");
            }
            System.out.print("|");
            System.out.print("\n-----------------------------\n");
        }
        */
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
                        return giocatore; //returna un valore in base a chi ha vinto (1 = giocatore 1, 2 = giocatore 2 o computer )
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
                        return giocatore; //returna un valore in base a chi ha vinto (1 = giocatore 1, 2 = giocatore 2 o computer )
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
                        return giocatore; //returna un valore in base a chi ha vinto (1 = giocatore 1, 2 = giocatore 2 o computer )
                    }
                }
            }                    
        }
        
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
            nMossePrev = 8;
        
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