package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PREFERENCE;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class PreferenceTest {
    @Test
    public void isValidPreference() {
        // null preference
        assertThrows(NullPointerException.class, () -> Preference.isValidPreference(null));

        // invalid preferences
        assertFalse(Preference.isValidPreference("")); // empty string
        assertFalse(Preference.isValidPreference(" ")); // spaces only
        assertFalse(Preference.isValidPreference("preference!")); // special characters

        // valid preferences
        assertTrue(Preference.isValidPreference("preference"));
        assertTrue(Preference.isValidPreference("preference1")); // alphanumeric
        assertTrue(Preference.isValidPreference("preference 1")); // alphanumeric with spaces
    }

    @Test
    public void equals() {
        Preference preference = new Preference();
        preference.addPreference("preference");

        // same values -> returns true
        Preference preferenceCopy = new Preference();
        preferenceCopy.addPreference("preference");
        assertTrue(preference.equals(preferenceCopy));

        // same object -> returns true
        assertTrue(preference.equals(preference));

        // null -> returns false
        assertFalse(preference.equals(null));

        // different types -> returns false
        assertFalse(preference.equals(5.0f));

        // different values -> returns false
        Preference differentPreference = new Preference();
        differentPreference.addPreference("different preference");
        assertFalse(preference.equals(differentPreference));
    }

    @Test
    public void addPreference() {
        Preference preference = new Preference();
        preference.addPreference(VALID_PREFERENCE);
        ArrayList<String> preferences = new ArrayList<>();
        preferences.add(VALID_PREFERENCE);
        Preference expectedPreference = new Preference(preferences);
        assertEquals(preference, expectedPreference);
    }
}
