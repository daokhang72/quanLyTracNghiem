package Component;

import DTO.UserDTO;

public class Session {
    private static UserDTO
    currentUser; 

    public static void setCurrentUser(UserDTO user) {
        currentUser = user;
    }

    public static UserDTO getCurrentUser() {
        return currentUser;
    }
}
