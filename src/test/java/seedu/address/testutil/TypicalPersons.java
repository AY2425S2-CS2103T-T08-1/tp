package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_REGULAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_VIP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withPhone("12345678").withTag("VIP").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withTag("REGULAR").withPreference("no seafood", "no fish").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withOrderHistory("soup", "chickenrice", "milo").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withTag("VIP").withPreference("No spicy food").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("94823224")
            .withPreference("No spicy food", "no beef").withOrderHistory("pizza", "milo").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("94827427")
            .withOrderHistory("milo").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("94828442")
            .withPreference("No spicy food", "no beef").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("84812424")
            .build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("84812131")
            .build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withTag(VALID_TAG_VIP).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withTag(VALID_TAG_REGULAR)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
