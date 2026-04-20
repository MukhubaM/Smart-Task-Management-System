package smartTaskManager.model;

import java.io.Serial;
import java.io.Serializable;

public record User(String name, int id) implements Serializable {

    @Serial     
    private static final long serialVersionUID = 1L;
}
