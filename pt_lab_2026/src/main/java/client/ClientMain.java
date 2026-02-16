package client;

public class ClientMain {
    public static void main(String[] args) {
        Client client = new Client();
        client.run();
        if(Thread.currentThread().isInterrupted()){
            System.exit(0);
        }
    }
}
