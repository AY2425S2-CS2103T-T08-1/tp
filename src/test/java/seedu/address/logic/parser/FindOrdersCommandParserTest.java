package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindOrdersCommand;
import seedu.address.model.person.OrdersContainsKeywordsPredicate;

public class FindOrdersCommandParserTest {

    private FindOrdersCommandParser parser = new FindOrdersCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindOrdersCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindOrdersCommand() {
        // no leading and trailing whitespaces
        FindOrdersCommand expectedFindOrdersCommand =
                new FindOrdersCommand(new OrdersContainsKeywordsPredicate("chicken rice"));
        assertParseSuccess(parser, "chicken rice", expectedFindOrdersCommand);
        assertParseSuccess(parser, "Chicken Rice", expectedFindOrdersCommand);
        assertParseSuccess(parser, "   chicken rice    ", expectedFindOrdersCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n chicken rice \t", expectedFindOrdersCommand);
    }

}
