import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Future;

public class ResultSet {
    private Set<Future<Computation>> results = Collections.synchronizedSet(new HashSet<>());

    public void add(Future<Computation> c) {
        synchronized (results) {
            results.add(c);
        }
    }
    public void printResults() {
        synchronized (results) {
            results.forEach(System.out::println);
        }
    }

}
