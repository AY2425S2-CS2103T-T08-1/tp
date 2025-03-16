package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PREFERENCE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SavePreferenceCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new SavePreferenceCommand object
 */
public class SavePreferenceCommandParser implements Parser<SavePreferenceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SavePreferenceCommand
     * and returns a SavePreferenceCommand object for execution.
     */
    public SavePreferenceCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PREFERENCE);

        if (argMultimap.getPreamble().isBlank() || argMultimap.getValue(PREFIX_PREFERENCE).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SavePreferenceCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
        String preference = argMultimap.getValue(PREFIX_PREFERENCE).orElse("");
        preference = preference.trim().toLowerCase();

        return new SavePreferenceCommand(index, preference);
    }

}
