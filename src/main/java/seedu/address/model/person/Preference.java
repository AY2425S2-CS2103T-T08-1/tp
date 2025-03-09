package seedu.address.model.person;

import java.util.List;

/**
 * Represents a Person's preference in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPreference(String)}
 */
public class Preference {
    public static final String MESSAGE_CONSTRAINTS =
            "Preferences should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the preference must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final List<String> preferences;

    /**
     * Constructs a {@code Preference}.
     *
     * @param preferences A list of valid preference.
     */
    public Preference(List<String> preferences) {
        this.preferences = preferences;
    }

    public Preference() {
        this.preferences = List.of();
    }

    /**
     * Returns true if a given string is a valid preference.
     */
    public static boolean isValidPreference(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public Preference addPreference(String preference) {
        List<String> newPreferences = preferences;
        newPreferences.add(preference);
        return new Preference(newPreferences);
    }

    @Override
    public String toString() {
        return preferences.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Preference)) {
            return false;
        }

        Preference otherPreference = (Preference) other;
        return preferences.equals(otherPreference.preferences);
    }

    @Override
    public int hashCode() {
        return preferences.hashCode();
    }

}
