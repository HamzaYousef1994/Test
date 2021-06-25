package app.model.user.helper;

public enum Permissions {


    APPOINTMENTS_PERMISSION("Appointments", "APPOINTMENTS_PERMISSION"),

    RECORDS_PERMISSION("Records", "RECORDS_PERMISSION"),

    ADD_USER_PERMISSION("Add User", "ADD_USER_PERMISSION");


    public final String label;
    public final String value;


    Permissions(String label, String value) {
        this.label = label;
        this.value = value;
    }
}
