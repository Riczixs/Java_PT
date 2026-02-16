package computation;

import java.util.UUID;

public class Computation {
    private UUID id = UUID.randomUUID();
    private final int start;
    private final int end;
    private int result = 0;

    public Computation(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getStart() {
        return start;
    }
    public int getEnd() {
        return end;
    }
    public String toString(){
        return "Range "+"[ "+start+", "+end+" ]" + " has " + result + " prime-fibonacci numbers.";
    }
}

