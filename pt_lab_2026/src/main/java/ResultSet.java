import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Future;

public class ResultSet {
    private Set<Computation> results = Collections.synchronizedSet(new HashSet<>());

    public void add(Computation c) {
        System.out.println("Adding result => " + c.getResult());
        synchronized (results) {
            results.add(c);
        }
    }
    public void printResults() {
        synchronized (results) {
            results.forEach(c -> System.out.println(c.toString()));
        }
    }
}
