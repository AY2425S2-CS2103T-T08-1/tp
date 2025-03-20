package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_REGULAR;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonTest {

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same name, all other attributes different -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB)
                .withTag(VALID_TAG_REGULAR).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // different phone, all other attributes same -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        Person editedBob = new PersonBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSamePerson(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTag(VALID_TAG_REGULAR).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", tag=" + ALICE.getTag() + ", preference=" + ALICE.getPreference()
                + ", orderHistory=" + ALICE.getOrderHistory() + "}";
        assertEquals(expected, ALICE.toString());
    }

    /*** NEW TESTS FOR ORDER TRACKING ***/

    @Test
    public void addOrder_storesOrderCorrectly() {
        Person person = new Person(new Name("Alice"), new Phone("12345678"),
                Optional.empty(), new Preference(List.of()));

        person.addOrder("Chicken Rice");
        person.addOrder("Chicken Rice");
        person.addOrder("Nasi Lemak");

        assertEquals(2, person.getOrderHistory().get("chicken rice")); // Order count should be 2
        assertEquals(1, person.getOrderHistory().get("nasi lemak")); // Order count should be 1
    }

    @Test
    public void addOrder_normalizesCase() {
        Person person = new Person(new Name("Alice"), new Phone("12345678"),
                Optional.empty(), new Preference(List.of()));

        person.addOrder("McFlurry");
        person.addOrder("mcflurry");
        person.addOrder("MCFLURRY");

        assertEquals(3, person.getOrderHistory().get("mcflurry")); // Should be case-insensitive
    }

    @Test
    public void addOrder_normalizesSpacing() {
        Person person = new Person(new Name("Alice"), new Phone("12345678"),
                Optional.empty(), new Preference(List.of()));

        person.addOrder("Mc Flurry");
        person.addOrder("mc   flurry");
        person.addOrder(" mc flurry ");

        assertEquals(3, person.getOrderHistory().get("mc flurry")); // Should normalize to "mcflurry"
    }

    @Test
    public void getTopDishes_returnsTopThreeSorted() {
        Person person = new Person(new Name("Alice"), new Phone("12345678"),
                Optional.empty(), new Preference(List.of()));

        person.addOrder("Chicken Rice");
        person.addOrder("Chicken Rice");
        person.addOrder("Nasi Lemak");
        person.addOrder("McFlurry");
        person.addOrder("McFlurry");
        person.addOrder("McFlurry");

        List<String> topDishes = person.getTopDishes();

        assertEquals(List.of("mcflurry", "chicken rice", "nasi lemak"), topDishes); // Sorted by highest frequency
    }
}
