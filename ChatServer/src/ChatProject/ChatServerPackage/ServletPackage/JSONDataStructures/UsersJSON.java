package ChatProject.ChatServerPackage.ServletPackage.JSONDataStructures;

import java.util.ArrayList;

/**
 * Created by sergey on 13.08.2015.
 */
public class UsersJSON {
    ArrayList<UserJSON> users = new ArrayList<>();

    public UsersJSON() {
    }

    public ArrayList<UserJSON> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<UserJSON> users) {
        this.users = users;
    }
}


