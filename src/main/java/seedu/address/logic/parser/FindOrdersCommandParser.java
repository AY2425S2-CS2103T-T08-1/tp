package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.FindOrdersCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.OrdersContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindOrdersCommand object
 */
public class FindOrdersCommandParser implements Parser<FindOrdersCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindOrdersCommand
     * and returns a FindOrdersCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindOrdersCommand parse(String args) throws ParseException {
        String keywords = args.trim().toLowerCase();
        if (keywords.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindOrdersCommand.MESSAGE_USAGE));
        }

        return new FindOrdersCommand(new OrdersContainsKeywordsPredicate(keywords));
    }

}
