package mvc;
import lombok.Getter;

@Getter
public abstract class Id {
    private final int id;
    public Id(int id) {
        this.id = id;
    }
}
