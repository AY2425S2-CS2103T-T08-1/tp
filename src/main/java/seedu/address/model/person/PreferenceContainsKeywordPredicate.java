package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests if a Person has a preference that contains the keyword.
 */
public class PreferenceContainsKeywordPredicate implements Predicate<Person> {
    private final String keyword;

    public PreferenceContainsKeywordPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Person person) {
        return person.getPreference().getValues().stream()
                .anyMatch(preference -> keyword.toLowerCase().equals(preference));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NameContainsKeywordsPredicate)) {
            return false;
        }

        PreferenceContainsKeywordPredicate otherPreferenceContainsKeywordsPredicate
                = (PreferenceContainsKeywordPredicate) other;
        return keyword.equals(otherPreferenceContainsKeywordsPredicate.keyword);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keyword", keyword).toString();
    }
}
