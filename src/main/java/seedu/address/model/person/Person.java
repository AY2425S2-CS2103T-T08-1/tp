package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Optional<Tag> tag;
    private final Preference preference;
    private final HashMap<String, Integer> orderHistory; // Stores dish names and their frequencies

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Optional<Tag> tag, Preference preference) {
        requireAllNonNull(name, phone, tag, preference);
        this.name = name;
        this.phone = phone;
        this.tag = tag;
        this.preference = preference;
        this.orderHistory = new HashMap<>();
    }

    /**
     * Copy constructor to create a new Person with the same details but updated order history.
     */
    public Person(Person other) {
        this.name = other.name;
        this.phone = other.phone;
        this.tag = other.tag;
        this.preference = other.preference;
        this.orderHistory = new HashMap<>(other.orderHistory); // Copy the existing order history
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Optional<Tag> getTag() {
        return tag;
    }

    public Preference getPreference() {
        return preference;
    }

    public void addPreference(String preference) {
        this.preference.addPreference(preference);
    }

    /**
     * Adds an order to the customer's order history.
     * If the dish has been ordered before, its count is incremented.
     * Otherwise, it is added to the history with a count of 1.
     *
     * @param dish The name of the dish ordered.
     */
    public void addOrder(String dish) {
        String normalizedDish = dish.trim().toLowerCase().replaceAll("\\s+", ""); // Normalize dish name
        orderHistory.put(normalizedDish, orderHistory.getOrDefault(normalizedDish, 0) + 1);
    }
    /**
     * Returns the order history of the customer as a mapping of dish names to their frequency.
     *
     * @return A {@code Map<String, Integer>} representing the customer's order history.
     */
    public Map<String, Integer> getOrderHistory() {
        return orderHistory;
    }

    /**
     * Retrieves the top three most frequently ordered dishes by the customer.
     *
     * @return A {@code List<String>} containing up to three dish names sorted by order frequency in descending order.
     */
    public List<String> getTopDishes() {
        return orderHistory.entrySet().stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue())) // Sort by frequency (descending)
                .limit(3) // Get top 3 dishes
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone())
                && otherPerson.getTag().equals(getTag())
                && otherPerson.getPreference().equals(getPreference())
                && orderHistory.equals(otherPerson.orderHistory);
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && tag.equals(otherPerson.tag)
                && preference.equals(otherPerson.preference);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, tag, preference);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("tag", tag)
                .add("preference", preference)
                .toString();
    }

}
