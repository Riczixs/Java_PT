package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable{
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private boolean done = false;
    private String message;
    @Override
    public void run() {
        try{
            socket = new Socket("127.0.0.1", 4000);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            InputHandler inputHandler = new InputHandler();
            Thread threadInput = new Thread(inputHandler);
            threadInput.start();
            String inMessage;
            while((inMessage = in.readLine()) != null){
                if(inMessage.contains("/exit")){
                    System.out.println("Exit received!");
                    shutdown();
                    break;
                }else{
                    System.out.println(inMessage);
                }
            }
        }catch(Exception e){
            shutdown();
        }
    }

    public void shutdown(){
        done = true;
        try{
            in.close();
            out.close();
            if(socket.isClosed()){
                socket.close();
            }
            Thread.currentThread().interrupt();
        }catch(IOException e){
            e.printStackTrace();
        }
    }


    class InputHandler implements Runnable {
        private BufferedReader inReader;
        @Override
        public void run() {
            try{
                inReader = new BufferedReader(new InputStreamReader(System.in));
                while(!done){
                    String message = inReader.readLine();
                    out.println(message);
                }
                inReader.close();
            } catch (IOException e) {
                shutdown();
            }
        }
    }
}
