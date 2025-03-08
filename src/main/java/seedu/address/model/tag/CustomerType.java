package seedu.address.model.tag;

public enum CustomerType {
    VIP, REGULAR, NEW;

    public static CustomerType fromString(String value) {
        for (CustomerType type : CustomerType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown customer type: " + value);
    }
}
