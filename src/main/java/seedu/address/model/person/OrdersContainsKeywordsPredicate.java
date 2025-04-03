package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Orders} matches the keywords given.
 */
public class OrdersContainsKeywordsPredicate implements Predicate<Person> {
    private final String keywords;

    public OrdersContainsKeywordsPredicate(String keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return person.getOrderHistory().keySet().stream().anyMatch(order -> order.contains(keywords));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof OrdersContainsKeywordsPredicate)) {
            return false;
        }

        OrdersContainsKeywordsPredicate otherOrdersContainsKeywordsPredicate = (OrdersContainsKeywordsPredicate) other;
        return keywords.equals(otherOrdersContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
