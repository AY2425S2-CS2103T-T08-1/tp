package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DISH;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddOrderCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code AddOrderCommand} object.
 */
public class AddOrderCommandParser implements Parser<AddOrderCommand> {

    /**
     * Parses the given {@code String} of arguments and returns an {@code AddOrderCommand} for execution.
     *
     * @param args The user input string containing the command arguments.
     * @return An {@code AddOrderCommand} containing the parsed customer index and dish name.
     * @throws ParseException If the user input is invalid or missing required parameters.
     */
    @Override
    public AddOrderCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DISH);

        if (!argMultimap.getValue(PREFIX_DISH).isPresent() || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOrderCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
        String dishName = argMultimap.getValue(PREFIX_DISH).orElseThrow(() ->
                new ParseException("Dish name must be provided."));

        return new AddOrderCommand(index, dishName);
    }

}
