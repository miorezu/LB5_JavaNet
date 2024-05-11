package Work;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class ActiveUsers implements Serializable {

    @Serial
    private final static long serialVersionUID = -1L;

    private ArrayList<User> users = new ArrayList<User>();

    public ActiveUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ActiveUsers() {
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        for (User user : users)
            buf.append(user + "\n");
        return buf.toString();
    }

    public void add(User user) {
        users.add(user);
    }

    public boolean isEmpty() {
        return users.isEmpty();
    }

    public int size() {
        return users.size();
    }

    public boolean contains(User user) {
        return users.contains(user);
    }

    public User get(int index) {
        return users.get(index);
    }

    public void remove(int index) {
        users.remove(index);
    }

    public void remove(User user) {
        users.remove(user);
    }

    public void clear() {
        users.clear();
    }
}