package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PREFERENCE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREFERENCE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PREFERENCE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SavePreferenceCommand;

public class SavePreferenceCommandParserTest {

    private static final String MESSAGE_INVALID_INDEX =
            "Index is not a non-zero unsigned integer.";

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SavePreferenceCommand.MESSAGE_USAGE);

    private SavePreferenceCommandParser parser = new SavePreferenceCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, PREFERENCE_DESC, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1 ", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + PREFERENCE_DESC, MESSAGE_INVALID_INDEX);

        // zero index
        assertParseFailure(parser, "0" + PREFERENCE_DESC, MESSAGE_INVALID_INDEX);

        // invalid preamble
        assertParseFailure(parser, "a" + PREFERENCE_DESC, MESSAGE_INVALID_INDEX);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1" + "/s" + VALID_PREFERENCE, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreference_failure() {
        // invalid preference
        assertParseFailure(parser, INVALID_PREFERENCE_DESC, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_preference_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + PREFERENCE_DESC;
        SavePreferenceCommand expectedCommand = new SavePreferenceCommand(targetIndex, VALID_PREFERENCE);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
