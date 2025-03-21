package seedu.address.testutil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Preference;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";

    private Name name;
    private Phone phone;
    private Optional<Tag> tag;
    private Preference preference;
    private Map<String, Integer> orderHistory;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        tag = Optional.empty();
        preference = new Preference();
        orderHistory = new HashMap<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        tag = personToCopy.getTag();
        preference = personToCopy.getPreference();
        orderHistory = personToCopy.getOrderHistory();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tag} into a {@code Optional<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTag(String tag) {
        if (tag.isBlank()) {
            this.tag = Optional.empty();
        } else if (Tag.isValidTagName(tag)) {
            this.tag = Optional.of(new Tag(tag));
        }
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Preference} of the {@code Person} that we are building.
     */
    public PersonBuilder withPreference(String... preference) {
        this.preference = new Preference(Arrays.asList(preference));
        return this;
    }

    /**
     * Adds the specified {@code orders} to the {@code orderHistory} of the {@code Person} being built.
     * If an order already exists in the history, its count is incremented; otherwise, it is added with a count of 1.
     *
     * @param orders One or more order names to be recorded in the order history.
     * @return This {@code PersonBuilder} instance with the updated order history.
     */
    public PersonBuilder withOrderHistory(String... orders) {
        for (String order: orders) {
            orderHistory.put(order, orderHistory.getOrDefault(order, 0) + 1);
        }
        return this;
    }

    public Person build() {
        Person person = new Person(name, phone, tag, preference);

        for (Map.Entry<String, Integer> entry : orderHistory.entrySet()) {
            String orderName = entry.getKey();
            int times = entry.getValue();
            while (times > 0) {
                person.addOrder(orderName);
                times--;
            }
        }
        return person;
    }

}
