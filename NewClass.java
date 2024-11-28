/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travail.a.faire;

/**
 *
 * @author sarac
 */
public class NewClass {
    
    -------------tp toto-------------------------------------------------------------
 
public class A extends Thread{

    String name ;
    int max ;



    public A(String name,int max) {

        this.name=name;
        this.max=max;


    }


    @Override
    public void run() {
        for (int i = 1; i <= max; i++) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(name+" : " +i);

        }

        System.out.println(this.name+" a fini de compter jusqu'a " +max);

    }
}

public class main {
    public static void main(String[] args) throws InterruptedException {

        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.


        A toto= new A("toto" , 5);
        A tata= new A("tata" , 5);
        A titi= new A("titi" , 5);

        toto.start();
        toto.join(1000);
        tata.start();
        tata.join(1000);
        titi.start();
        titi.join(1000);
    }
------------------------------tp compteurpartage-----------------------------------------------
        
        public class compteurpartage {
    
        private int compteur=0;
        
        public synchronized void increment() {
            compteur++;
        }

    public int getCompteur() {
        return compteur;
    }
        
        

}
    public class incrementeur implements Runnable{
    
    public final compteurpartage cp;
    
    
    public incrementeur(compteurpartage cp){
        this.cp=cp;     
        
    }
    
        public void run(){
            for (int i = 0 ; i < 1000 ; i++){
                cp.increment();
            }
    }
    
}
    
public class main {
    
    public static void main(String[] args) throws InterruptedException {
        
        compteurpartage cp = new compteurpartage();
        Thread[] threads = new Thread[10];
        
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(new incrementeur(cp));
            threads[i].start();        
        }
        
        for (int i = 0; i < 10; i++) {
            threads[i].join();
            
        }
        
        System.out.println("compteur : " + cp.getCompteur() );
        
    }
    

--------------------------------------tp agentdepot-----------------------------------------------------------------------------


public class Tp3 {
    
   float salaire;

    public Tp3( int salaire) {
        this.salaire=salaire;
    }
    
    public void depot(float montant){
        
       salaire = salaire + montant ;  
    }
    
    public void retrait(float montant){ 
        
        if (montant<=salaire) {
           salaire = salaire - montant ; 
        } else {
            System.out.println("impossible");
        }
        

 
public class agentdepot extends Thread{
    
        Tp3  compte;
        
        public agentdepot(Tp3 compte){                       
            this.compte=compte;           
        }
        
        public void run(){
            for (int i = 0; i < 8; i++) {
                compte.depot(3);
                
            }
        }
        
public class agentretrait {
    
      Tp3  compte;
        
        public agentretrait(Tp3 compte){            
            this.compte=compte;           
        }
        
         public void run(){
            for (int i = 0; i < 8; i++) {
                compte.retrait(300);
                
            }
        }
        
}

-------------------------------tp consumer/producer--------------------------------------------------


public class producer extends Thread{
	
	static final int MAXQUEUE=5;
	private Vector messages = new Vector();
	
	
	private synchronized void putMessage() 
        throws InterruptedException {
	while (messages.size() ==MAXQUEUE) {
		wait();
		System.out.println("waiting");		
	}
	messages.addElement(new java.util.Date().toString());
	notify();
	}
	
	public synchronized String getMessage()
			throws InterruptedException {
		while (messages.size() ==0) 
			wait();
		
			try {
				sleep(1000);
			} catch(InterruptedException e) {}
			String message=(String) messages.firstElement();
			messages.removeElement(message);
			notify();
			return message;
					
			}
        
        public void run() {
		try {
			while(true) {
				putMessage();
				sleep(1000);
			}
		}catch(InterruptedException e) {}				
	}
        
		}
	
	
public class consumer extends Thread{
	producer producer;
	
	consumer(producer p) {	producer = p ; }
	public void run() {
		try {
			while(true) {
				String message=producer.getMessage();
				System.out.println("got message:"+message);
				sleep(2000);
			}
		}catch (InterruptedException e) {}
	
	}
	public static void main(String args[]) {
		producer producer= new producer();
		producer.start();
                consumer consumer= new consumer();
		consumer.start();
		
	}
	
}

----------------------------------tp c/p message etudiant ------------------------------------------------------------------

public class Message {
    private String content;
    private boolean hasNewMessage = false;

    public synchronized void sendMessage(String message) {
        while (hasNewMessage) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        content = message;
        hasNewMessage = true;
        System.out.println("Message envoyé : " + message);
        notifyAll();
    }

    public synchronized String receiveMessage() {
        while (!hasNewMessage) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        hasNewMessage = false;
        notifyAll();
        System.out.println("Message reçu : " + content);
        return content;
    }
}


public class Etudiant extends Thread{
        private String name;
        private Message message;
        private boolean isSender;

        public Etudiant(String name, Message message, boolean isSender) {
            this.name = name;
            this.message = message;
            this.isSender = isSender;
        }

        @Override
        public void run() {
            if (isSender) {
                String[] messagesToSend = {
                        "Salut, comment ça va?",
                        "Qu'est-ce que tu fais?",
                        "Bonne chance pour tes examens!",
                        "Au revoir!"
                };
                for (String msg : messagesToSend) {
                    message.sendMessage(name + ": " + msg);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            } else {
                for (int i = 0; i < 4; i++) {
                    message.receiveMessage();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }

public class Main {
    public static void main(String[] args) {
        Message sharedMessage = new Message();

        Etudiant etud1 = new Etudiant("Étudiant 1", sharedMessage, true);
        Etudiant etud2 = new Etudiant("Étudiant 2", sharedMessage, false);

        etud1.start();
        etud2.start();
    }
    
---------------------------------------client/serveur------------------------------------------------------------

public class Serveur {

   
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            ServerSocket ecoute = new ServerSocket(1111,6);
            Socket service=null ;
            {
                service=ecoute.accept();
                System.out.println("client connected !");
                
                DataInputStream is = new DataInputStream(service.getInputStream());
                
                int val = is.readInt();
                String op = is.readUTF();
                int resultat ;
                
                if (op.equals( "+" )) {
                    resultat = val +val ;
                } else if(op.equals( "+" )) {
                    resultat = val - val ;
                } else if (op.equals( "+" )){
                    resultat = val * val ;
                } else if (op.equals( "+" )){
                    resultat = val / val ;
                } else{
                    System.out.println("error");
                    return;
                }
                
                DataOutputStream os = new DataOutputStream(service.getOutputStream());
                os.writeInt(resultat);
                service.close();
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
 
    }
    
}


public class Client {

    public static void main(String[] args) {
        // TODO code application logic here
        try {
            Socket s = new Socket("localhost" , 1111);
            System.out.println("connected to server");
            DataOutputStream os = new DataOutputStream(s.getOutputStream());
            
            os.writeInt(6);
            os.writeUTF("+");
            DataInputStream is =new DataInputStream(s.getInputStream());
            System.out.println("la valeur calcule est :" + is.readInt());
           
        } catch (Exception e) {
            e.printStackTrace();
        }
       
    }
    
}


/**
 * 1- Quelles sont les architectures de systèmes parallèles ?
A. SI, SD, MI, MD

2- Quelle sont les deux méthodes pour créer un thread en Java ?
C. Implémenter l'interface Runnable et Étendre la classe Thread

3- L'interface Runnable - qu'est-ce qu'il contient comme méthode ?
A. run()

4- Différents états du cycle de vie d'un thread ?
B. NEW, RUNNABLE, BLOCKED, WAITING, TIMED_WAITING, TERMINATED

* 5- Quelle est l'exclusion mutuelle et la différence entre Dekker et Peterson ?
B. Dekker est spécifique à deux threads, Peterson est plus général.

* 6- Quelle méthode permet de bloquer les threads jusqu'à l'appel d'une méthode notify() ?
B. wait()

* 7- Bloquer le thread pour une période déterminée :
B. Faux

* 9- Comment éviter que les données partagées soient rompues dans un environnement
multithread ?
A. Utiliser des méthodes synchronisées

* 10- Pour échanger la priorité d'un thread, on utilise la méthode :
A. setPriority()

* 11- C'est quoi le problème de l'incrémentation sans synchronisation ?
B. Les threads peuvent accéder simultanément à la ressource.

* 12- Quelle méthode permet de vérifier si un thread est en cours d'exécution ?
A. isAlive()

* 13- Quelle est la différence entre run() et start() ?
B. start() crée un nouveau thread, run() l'exécute directement.

* 14- Comment créer un thread en utilisant l'interface Runnable ?
D. Implanter la méthode run() et passer l'objet à un thread.

* 1. Qu'est-ce qu'un Mutex ?
A. Un mécanisme pour gérer l'accès concurrent à une ressource partagée

* 2. Quelle est la différence entre les algorithmes de Dekker et Peterson ?
B. Les threads de Dekker sont altruistes et ceux de Peterson sont égoïstes

* 7. Quelles sont les transitions possibles entre les états d'un thread ?
B. RUNNABLE ↔ WAITING, BLOCKED → TERMINATED

* 8. Quel est le rôle de la méthode join() ?
  B. Attendre la fin de l'exécution d'un autre thread

* 9. Quel est le rôle de la méthode wait() ?
B. Bloquer un thread jusqu'à l'appel de la méthode notify()

* 11. Que signifie un "deadlock" ?
C. Un blocage mutuel de deux ou plusieurs threads attendant des ressources

* 12. Quelle est la différence entre wait() et sleep() ?
B. wait() ne libère pas le verrou, sleep() le libère

* 13. Que permet la synchronisation dans le contexte des threads ?
B. Contrôler l'accès aux ressources partagées pour éviter les conflits
 * /
