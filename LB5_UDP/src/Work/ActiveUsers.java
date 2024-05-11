package Work;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class ActiveUsers implements Serializable {

    @Serial
    private final static long serialVersionUID = -1L;

    private ArrayList<User> users;

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        for (User u : users)
            buf.append(u+"\n");
        return buf.toString();
    }

}
