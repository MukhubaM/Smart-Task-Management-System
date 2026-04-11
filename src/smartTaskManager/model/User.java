package smartTaskManager.model;

import java.io.Serializable;

public record User(String name, int id) implements Serializable {

   // @Serial     //Thought it's a good idea to annotate it
    private static final long serialVersionUID = 1L;
}
