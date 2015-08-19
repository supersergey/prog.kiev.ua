package ChatProject.ChatServerPackage.ServletPackage;

import ChatProject.ChatServerPackage.User;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 12.08.2015.
 */
public class UsersDB {
    private final String userdbFile = "c:\\temp\\usersdb.txt";
    private Map<String, String> users = new HashMap<>();
    private static UsersDB ourInstance = new UsersDB();

    public static UsersDB getInstance() {
        return ourInstance;
    }

    private UsersDB() {
        users.put("st", "st");
        users.put("koala", "bear");
        /*try (InputStream stream = new FileInputStream(userdbFile);
             BufferedReader br = new BufferedReader(new InputStreamReader(stream));) {
            while (br.ready()) {
                String[] entry = br.readLine().split("::");
                users.put(entry[0], entry[1]);
            }
            br.close();
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    public void addNewUser(String login, String password) {
        users.put(login, password);
        /*try (FileWriter writer = new FileWriter(userdbFile, true);) {
            writer.write(login + "::" + password + "\r\n");
            writer.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }*/
    }

    protected String getUserPassword(String name)
    {
        return users.get(name);
    }


}
