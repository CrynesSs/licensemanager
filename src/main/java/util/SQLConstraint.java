package util;

public record SQLConstraint(String name, int length) {
    public static final SQLConstraint PRIMARY_KEY = new SQLConstraint("PRIMARY KEY", -1);
    public static final SQLConstraint SERIAL = new SQLConstraint("SERIAL",-1);
    public static final SQLConstraint UNIQUE = new SQLConstraint("UNIQUE", -1);
    public static final SQLConstraint NOT_NULL = new SQLConstraint("NOT NULL", -1);
    public static final SQLConstraint DEFAULT = new SQLConstraint("DEFAULT",-1);

    public static SQLConstraint VARCHAR(int length) {
        return new SQLConstraint("VARCHAR", length);
    }

    @Override
    public String toString() {
        return name;
    }
}
