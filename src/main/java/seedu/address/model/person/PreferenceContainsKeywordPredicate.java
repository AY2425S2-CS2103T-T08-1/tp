package seedu.address.model.person;

import java.util.function.Predicate;

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
        return person.getPreference().getValues().contains(keyword);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof PreferenceContainsKeywordPredicate
                && keyword.equals(((PreferenceContainsKeywordPredicate) other).keyword));
    }
}
