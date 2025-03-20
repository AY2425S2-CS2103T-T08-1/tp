package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddOrderCommand;
import seedu.address.logic.parser.exceptions.ParseException;

class AddOrderCommandParserTest {

    private final AddOrderCommandParser parser = new AddOrderCommandParser();

    @Test
    void parse_missingDish_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("1"));
    }

    @Test
    void parse_invalidIndex_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("abc d/Chicken Rice"));
    }
}
