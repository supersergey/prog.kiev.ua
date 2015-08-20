package JSON;

import java.util.ArrayList;

/**
 * Created by sergey on 19.08.2015.
 */
public class RoomsJSON {
    ArrayList<RoomJSON> rooms = new ArrayList<>();

    public RoomsJSON() {
    }

    public ArrayList<RoomJSON> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<RoomJSON> rooms) {
        this.rooms = rooms;
    }
}
