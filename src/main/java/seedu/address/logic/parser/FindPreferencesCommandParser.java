package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.FindPreferencesCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PreferenceContainsKeywordPredicate;

/**
 * Parses input arguments and creates a new FindPreferencesCommand object
 */
public class FindPreferencesCommandParser implements Parser<FindPreferencesCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindPreferencesCommand
     * and returns a FindPreferencesCommand object for execution.
     */
    public FindPreferencesCommand parse(String args) throws ParseException {
        requireNonNull(args);

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format("Invalid command format! \n%s",
                    FindPreferencesCommand.MESSAGE_USAGE));
        }

        PreferenceContainsKeywordPredicate predicate = new PreferenceContainsKeywordPredicate(trimmedArgs);
        return new FindPreferencesCommand(predicate);

    }
}
