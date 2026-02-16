package computation;

import java.util.concurrent.Callable;

public class PrimeFib implements Callable<Computation> {
    private final Computation computation;
    public PrimeFib(Computation computation) {
        this.computation = computation;
    }

    public boolean isPrime(int number){
        if(number%2==0) return false;
        if(number==1) return true;
        for(int i=3; i<number; i++){ //i*i is faster
            if(number%i==0) return false;
        }
        return true;
    }
    public boolean isFibonacci(int number){
        if(number < 3 && number >=0) return true;
        int fib1=1, fib2=1, tmp=0;
        while(number > fib1){
            if(number == fib2) return true;
            tmp = fib2;
            fib2 = fib2 + fib1;
            fib1 = tmp;
        }
        return false;
    }
    public int makeCalc(){
        int counter = 0;
        for(int i=computation.getStart(); i<computation.getEnd(); i++){
           if((isPrime(i) && isFibonacci(i))){
               //System.out.println("Liczba " + i + "to computation.PrimeFib");
               counter++;
           }
        }
        return counter;
    }

    @Override
    public Computation call() throws Exception{
        System.out.println(Thread.currentThread().getName() + "starts the calculations for => (" + computation.getStart() + ", " + computation.getEnd() + ")");
        computation.setResult(makeCalc());
        System.out.println(Thread.currentThread().getName() + "has ended the computation.Computation!");
        return computation;
    }
}
