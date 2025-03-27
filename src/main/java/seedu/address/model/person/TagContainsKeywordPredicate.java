package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.model.tag.Tag;

/**
 * Tests if a Person has a tag that contains the keyword.
 */
public class TagContainsKeywordPredicate implements Predicate<Person> {

    private final Tag tag;

    public TagContainsKeywordPredicate(Tag tag) {
        this.tag = tag;
    }

    @Override
    public boolean test(Person person) {
        if (person.getTag().isEmpty()) {
            return false;
        }
        return person.getTag().get().equals(tag);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagContainsKeywordPredicate)) {
            return false;
        }

        TagContainsKeywordPredicate otherTagContainsKeywordPredicate =
                (TagContainsKeywordPredicate) other;
        return tag.equals(otherTagContainsKeywordPredicate.tag);
    }

    @Override
    public String toString() {
        return new seedu.address.commons.util.ToStringBuilder(this).add("tag", tag).toString();
    }
}
