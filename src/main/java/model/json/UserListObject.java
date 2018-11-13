package model.json;

import java.util.List;

public class UserListObject {
    private List<UserObject> users;

    public UserListObject(List<UserObject> users) {
        this.users = users;
    }

    public List<UserObject> getUsers() {
        return users;
    }

    public void setUsers(List<UserObject> users) {
        this.users = users;
    }
}
