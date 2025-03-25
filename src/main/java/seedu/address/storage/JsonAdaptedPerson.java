package seedu.address.storage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Preference;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String tag;
    private final String preference;
    private final Map<String, Integer> orderHistory; // Stores dish names and their order frequencies

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name,
                             @JsonProperty("phone") String phone,
                             @JsonProperty("tag") String tag,
                             @JsonProperty("preference") String preference,
                             @JsonProperty("orderHistory") Map<String, Integer> orderHistory) {
        this.name = name;
        this.phone = phone;
        this.tag = tag;
        this.preference = preference;
        if (orderHistory == null) {
            this.orderHistory = new HashMap<>();
        } else {
            this.orderHistory = new HashMap<>(orderHistory);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        tag = source.getTag().map(Tag::toString).orElse("");
        preference = source.getPreference().toString();
        orderHistory = new HashMap<>(source.getOrderHistory()); // Copying order history from Person
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (tag == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Tag.class.getSimpleName()));
        }

        if (!tag.isEmpty() && !Tag.isValidTagName(tag)) {
            throw new IllegalValueException(Tag.MESSAGE_CONSTRAINTS);
        }

        Optional<Tag> modelTag = Optional.empty();
        if (!tag.isEmpty()) {
            modelTag = Optional.of(new Tag(tag));
        }

        if (preference == null || preference.length() < 2) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Preference"));
        }

        List<String> list = new ArrayList<>();
        String trimmedPref = preference.substring(1, preference.length() - 1);
        if (!trimmedPref.isBlank()) {
            list.addAll(Arrays.asList(trimmedPref.split(", ")));
        }
        final Preference modelPreference = new Preference(list);

        Person person = new Person(modelName, modelPhone, modelTag, modelPreference);

        if (orderHistory == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Order History"));
        }

        // Restore the order history into the person object
        for (Map.Entry<String, Integer> entry : orderHistory.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                person.addOrder(entry.getKey()); // Re-add the orders based on frequency
            }
        }

        return person;
    }

    /**
     * Returns the order history of the customer.
     *
     * @return A map of dish names to order frequencies.
     */
    @JsonProperty("orderHistory")
    public Map<String, Integer> getOrderHistory() {
        return orderHistory;
    }
}
