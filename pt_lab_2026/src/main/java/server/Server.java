package server;

import computation.Computation;
import computation.PrimeFib;
import computation.ResultSet;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.*;

public class Server implements Runnable {
    private ExecutorService connectionThreadPool;
    private ExecutorService computationThreadPool = Executors.newCachedThreadPool();
    private ResultSet resultSet = new ResultSet();
    private ArrayList<ConnectionHandler> connectionHandlers = new ArrayList<ConnectionHandler>();
    private ServerSocket serverSocket;
    private boolean done;

    @Override
    public void run() {
        try {
            connectionThreadPool = Executors.newCachedThreadPool();
            serverSocket = new ServerSocket(4000);
            while( !done ){
                Socket client = serverSocket.accept();
                ConnectionHandler connectionHandler = new ConnectionHandler(client);
                connectionHandlers.add(connectionHandler);
                connectionThreadPool.execute(connectionHandler);
            }
            shutDownThreads();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void broadCast(String message){
        for(ConnectionHandler connectionHandler : connectionHandlers){
            connectionHandler.sendMessage(message);
        }
    }
    public void shutDownThreads() {
        connectionThreadPool.shutdown();
        try {
            if (!connectionThreadPool.awaitTermination(1, TimeUnit.SECONDS)) {
                connectionThreadPool.shutdownNow();
                if (!connectionThreadPool.awaitTermination(1, TimeUnit.SECONDS)) {
                    System.err.println("Pool did not terminate");
                }
            }
        } catch (InterruptedException e) {
            connectionThreadPool.shutdownNow();
            Thread.currentThread().interrupt();
        }
        System.out.println("Threads shut down");
    }

    class ConnectionHandler implements Runnable {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;
        private String message;
        private String name;
        public ConnectionHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        public void sendMessage(String message) {
            out.println(message);
        }

        public void startCalc(Computation computation) {
            Future<Computation> comp = computationThreadPool.submit(new PrimeFib(computation));
            try{
                out.println(comp.get().toString());
            }catch(InterruptedException | ExecutionException e){
                //
            }
            resultSet.add(computation);
        }

        @Override
        public void run() {
            try{
                System.out.println("Client handled by " + Thread.currentThread().getName());
                out = new PrintWriter(clientSocket.getOutputStream(), true); //getting messages from Client
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //sending messages to Client
                sendMessage("Welcome, enter '/help' to see available commands!"); //sendMessage to client
                System.out.println("New client connected");
                while((message = in.readLine()) != null){
                    if(message.contains("/nick")){
                        String[] splitMessage = message.split(" ");
                        name = splitMessage[1];
                        broadCast(name + " changed his nickname!");
                        System.out.println(name + " changed his nickname!");
                    }else if(message.contains("/exit")){
                        System.out.println("Closing connection...");
                        exit();
                    }else if(message.contains("/count")){
                        String[] splitMessage = message.split(" ");
                        int num1 = Integer.parseInt(splitMessage[1]);
                        int num2 = Integer.parseInt(splitMessage[2]);
                        Computation computation = new Computation(num1, num2);
                        startCalc(computation);
                    }else if(message.contains("/help")){
                        printCommands();
                    }else if(message.contains("/results")){
                        resultSet.printResults(out);
                    }
                }
            }catch (IOException e){
                exit();
            }
        }
        public void exit(){
            out.println("/exit");
            done = true;
            try
            {
                in.close();
                out.close();
                if(!clientSocket.isClosed()){
                    clientSocket.close();
                    shutDownThreads();
                }
            }catch (IOException e){
                //
            }
        }
        public void printCommands(){
            out.println("Commands: \n" +
                    "'nick' => Change your nickname\n" +
                    "'count <num1> <num2>' => Count PrimeFib numbers in given range\n" +
                    "'results' => Display all made computations\n" +
                    "'help' => Display this help message\n" +
                    "'exit' => Close connection");
        }

    }
}
