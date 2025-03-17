package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_REGULAR;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_VIP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_REGULAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_VIP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TagCommand;

public class TagCommandParserTest {

    private static final String TAG_EMPTY = PREFIX_TAG + " ";

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE);

    private TagCommandParser parser = new TagCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_TAG_VIP, MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + TAG_DESC_VIP, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + TAG_DESC_VIP, MESSAGE_INVALID_FORMAT);

        // invalid preamble
        assertParseFailure(parser, "a" + TAG_DESC_VIP, MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1" + "/s" + TAG_DESC_VIP, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_emptyTag_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + TAG_EMPTY;
        TagCommand expectedCommand = new TagCommand(targetIndex, "");

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_singleTag_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + TAG_DESC_VIP;
        TagCommand expectedCommand = new TagCommand(targetIndex, VALID_TAG_VIP);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleTags_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + TAG_DESC_VIP + TAG_DESC_REGULAR;
        TagCommand expectedCommand = new TagCommand(targetIndex, VALID_TAG_REGULAR);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
