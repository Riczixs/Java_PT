import java.util.*;
import java.util.concurrent.*;

public class Main {
    public static final Random rand = new Random();
    public static void shutDownExecutor(ExecutorService executor) {
        System.out.println("Shutting down executor...");
        executor.shutdown();
        try{
            if(!executor.awaitTermination(60, TimeUnit.SECONDS)){
                executor.shutdownNow();
                if(!executor.awaitTermination(60, TimeUnit.SECONDS)){
                    System.err.println("Executor did not terminate");
                }
            }
        }catch (InterruptedException e){
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
        System.out.println("Executor shut down Correctly!");
    }

    public static void main(String[] args) {
        int thread_count = Integer.parseInt(args[0]);
        Scanner sc = new Scanner(System.in);
        //Synchronized resource Set
        Set<Computation> resources = Collections.synchronizedSet(new HashSet<>());
        //Synchronized result Set
        ResultSet results = new ResultSet();
        //Executor Service
        ExecutorService executor = Executors.newFixedThreadPool(thread_count);
        for(int i = 0; i<10; i++){
            resources.add(new Computation(i*10000,(i+1)*(rand.nextInt(1,10))*10000));
        }
        for (Computation resource : resources) {
            Future<Computation> f = executor.submit(new PrimeFib(resource));
            results.add(f);
        }
        try{
            if(executor.awaitTermination(10,TimeUnit.SECONDS)){
                shutDownExecutor(executor);
                results.printResults();
            }
        }catch(InterruptedException e){
            executor.shutdownNow();
        }
    }
}
