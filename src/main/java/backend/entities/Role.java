package backend.entities;

public enum Role {
    USER, // 0
    ADMIN; // 1

//    @JsonCreator
//    public static Role forValue(String value) {
//        for (Role role : values()) {
//            if (role.toString().equalsIgnoreCase(value)) {
//                return role;
//            }
//        }
//        throw new IllegalArgumentException(format("No role with text %s found.", value));
//    }
}
