package computation;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ResultSet {
    private Set<Computation> results = Collections.synchronizedSet(new HashSet<>());

    public void add(Computation c) {
        System.out.println("Adding result => " + c.getResult());
        synchronized (results) {
            results.add(c);
        }
    }
    public void printResults(PrintWriter out) {
        synchronized (results) {
            results.forEach(c -> out.println(c.toString()));
        }
    }
}
