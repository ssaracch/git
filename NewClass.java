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
            ServerSocket ecoute;
            ecoute = new ServerSocket(1111,6);
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
