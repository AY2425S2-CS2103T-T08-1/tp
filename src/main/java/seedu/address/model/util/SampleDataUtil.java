package seedu.address.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Preference;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static final Remark EMPTY_REMARK = new Remark("");

    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), EMPTY_REMARK, Optional.of(new Tag("VIP"))
            , new Preference(new ArrayList(Arrays.asList("No spicy food")))),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), EMPTY_REMARK,
                Optional.of(new Tag("New")), new Preference()),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), EMPTY_REMARK,
                Optional.of(new Tag("Regular")), new Preference()),
            new Person(new Name("David Li"), new Phone("91031282"), EMPTY_REMARK,
                Optional.empty(),
                    new Preference(new ArrayList(Arrays.asList("No beef", "No pork")))),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), EMPTY_REMARK,
                Optional.of(new Tag("New")),
                    new Preference(new ArrayList(Arrays.asList("No beef", "No pork", "No seafood")))),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), EMPTY_REMARK,
            Optional.empty(), new Preference(new ArrayList(Arrays.asList("No beef", "No pork", "No seafood")))),
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

}
