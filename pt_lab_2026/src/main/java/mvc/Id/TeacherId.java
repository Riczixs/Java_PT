package mvc.Id;

import jakarta.persistence.Embeddable;

@Embeddable
public class TeacherId extends Id{
    public TeacherId(int id) {
        super(id);
    }
}
