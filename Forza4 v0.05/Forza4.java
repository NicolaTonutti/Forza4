class Forza4{
    
    public static void main(String[] args){
        
        while(true){  //Loop infinito
            int gamemode = menu();
            if(gamemode == 3)
            {
                return;
            }
            gioco(gamemode);
        }        
    }
    
    static int menu(){
        int gamemode;
        
        System.out.println("Che modalita' vuoi giocare?\n1 - Player vs AI\n2 - Player vs Player\n3 - esci"); //Chiedi all'utente che modalita' vuole giocare
        do{
            gamemode = Leggi.unInt();
            
        }while(gamemode <= 0 || gamemode >= 4);
        
        return gamemode;
    }
    
    
        
    static void gioco(int gamemode){
        
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
                mossa = ai(m, spazio_col); //Fai scegliere la mossa all'intelligenza artificiale
            }
            else{
                do{
                    System.out.println("Giocatore " + giocatore + ":");
                    System.out.println("Inserisci il numero della colonna da 1 a 7");
                    mossa = Leggi.unInt()-1; //Leggi input. (L'utente inserira' un valore da 1 a 7, ma il programma sottrarrà 1 a quel valore così sarà da 0 a 6)
                    
                    if(spazio_col[mossa] < 0){
                        System.out.println("Non c'e' spazio in quella colonna, pagliaccio");
                    }
                }while(mossa >= COL || mossa < 0 || spazio_col[mossa] < 0 /* se non c'e' spazio nella colonna*/);
            }
            
            
            m[spazio_col[mossa]][mossa] = giocatore; //1 significa che c'è il gettone del giocatore 1, 2 = G2 oppure computer 
            spazio_col[mossa]--; //fa capire al programma che c'è meno spazio nella colonna
            
            stampa(m, RIG, COL); //Funzione per stampare matrice di gioco
            
            int vincitore = controlla4(m, giocatore);
            if(vincitore >= 1){ //Se la funzione controlla4 ritorna un numero uguale o maggiore a 1 significa chequalcuno ha vinto
                if(gamemode == 1 && vincitore == 2){ //Se stai giocando in modalita Player vs computer e il vincitore e' il computer
                    System.out.println("Il computer ha vinto!"); //Stampa che il computer ha vinto
                }
                else{ //...Altrimenti se ha vinto un umano stampa che l'umano ha vinto e non il computer
                    System.out.println("Giocatore " + vincitore + " ha vinto!"); //Stampa chi ha vinto
                }
                return;
            }
            
            giocatore = ((giocatore+1)%(4-giocatore)); //a fine turno Cambia giocatore
            
        }
    }
    
    static void stampa(int m[][], int RIG, int COL){ //Metodo per stampare
        for(int i = 0; i < RIG; i++){
            for(int j = 0; j < COL; j++){           
                System.out.print(m[i][j] + " ");
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
        
        
        
        return 0; //Returna 0 se nessuno ha ancora vinto
    }
    
    
    static int ai(int m[][], int spazio_col[]){ //Intelligenza artificiale
        int mossa;        
        mossa = (int)Math.floor(Math.random()*(6-0+1)+0); //genera numeri randomici
        
        
        //CONTROLLA SE C'E' una mossa da bloccare all'avversario per evitare che vinca
        for(int j = 0; j<7; j++){     //6 = quantita' righe  
            //CONTROLLO A DESTRA
            if(j <= 3){               
                //ORIZZONTALE
                for(int cnt = 0; cnt < 3; cnt++){ 
                    if(m[spazio_col[j]][j + cnt] != 1){
                        break;
                    }
                    else if(cnt >= 2){
                        mossa = j;
                    }                
                }
                
                //DIAGONALE BASSO
                if(spazio_col[j] <= 2 ){
                    for(int cnt = 0; cnt < 3; cnt++){ 
                        if(m[spazio_col[j] - cnt] [j + cnt] != 1){
                            break;
                        }
                        else if(cnt >= 2){
                            mossa = j;
                        }                
                    }
                }
                
                //DIAGONALE ALTO
                if(spazio_col[j] >= 3 ){
                    for(int cnt = 0; cnt < 3; cnt++){ 
                        if(m[spazio_col[j] + cnt][j + cnt] != 1){
                            break;
                        }
                        else if(cnt >= 2){
                            mossa = j;
                        }                
                    }
                }
                
                
            }  
            
            //CONTROLLO A SINISTRA
            if(j >= 3){               
                //ORIZZONTALE               
                for(int cnt = 0; cnt < 3; cnt++){ 
                    if(m[spazio_col[j]][j - cnt] != 1){
                        break;
                    }
                    else if(cnt >= 2){
                        mossa = j;
                    }                
                }
                
                //DIAGONALE BASSO
                if(spazio_col[j] <= 2 ){
                    for(int cnt = 0; cnt < 3; cnt++){ 
                        if(m[spazio_col[j] - cnt][j - cnt] != 1){
                            break;
                        }
                        else if(cnt >= 2){
                            mossa = j;
                        }                
                    }
                }
                
                //DIAGONALE ALTO
                if(spazio_col[j] >= 3 ){
                    for(int cnt = 0; cnt < 3; cnt++){ 
                        if(m[spazio_col[j] + cnt][j - cnt] != 1){
                            break;
                        }
                        else if(cnt >= 2){
                            mossa = j;
                        }                
                    }
                }
                
                
            }
            
            
            
            //CONTROLLO VERTICALE IN BASSO
            if(spazio_col[j] <= 2 ){
                for(int cnt = 0; cnt < 3; cnt++){ 
                    if(m[spazio_col[j] - cnt][j] != 1){
                        break;
                    }
                    else if(cnt >= 2){
                        mossa = j;
                    }                
                } 
            }
        
            
            
        }
        
        
        
        
        System.out.println("\n\nMossa del Computer:\n");
        return mossa;
    }
}