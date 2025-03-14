package seedu.address.logic.parser;

import seedu.address.logic.commands.FindPreferencesCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
import seedu.address.model.person.PreferenceContainsKeywordPredicate;

import java.util.function.Predicate;

/**
 * Parses input arguments and creates a new FindPreferencesCommand object
 */
public class FindPreferencesCommandParser implements Parser<FindPreferencesCommand> {
    @Override
    public FindPreferencesCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format("Invalid command format! \n%s",
                    FindPreferencesCommand.MESSAGE_USAGE));
        }

        Predicate<Person> predicate = new PreferenceContainsKeywordPredicate(trimmedArgs);
        return new FindPreferencesCommand(predicate);
    }
}
