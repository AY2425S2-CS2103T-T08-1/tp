package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;


/**
 * Contains unit tests for TagCommand.
 */
public class TagCommandTest {
    private static final String VALID_TAG = "VIP";
    private static final String INVALID_TAG = "friend";
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TagCommand(null, "VIP"));
    }

    @Test
    public void constructor_nullTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TagCommand(Index.fromZeroBased(1), null));
    }

    @Test
    public void equals() {
        TagCommand tagFirstCommand = new TagCommand(Index.fromZeroBased(1), "VIP");
        TagCommand tagSecondCommand = new TagCommand(Index.fromZeroBased(2), "Regular");

        // same object -> returns true
        assertTrue(tagFirstCommand.equals(tagFirstCommand));

        // same values -> returns true
        TagCommand tagFirstCommandCopy = new TagCommand(Index.fromZeroBased(1), "VIP");
        assertTrue(tagFirstCommand.equals(tagFirstCommandCopy));

        // different types -> returns false
        assertFalse(tagFirstCommand.equals(1));

        // null -> returns false
        assertFalse(tagFirstCommand.equals(null));

        // different tag -> returns false
        assertFalse(tagFirstCommand.equals(tagSecondCommand));
    }

    @Test
    public void toStringMethod() {
        TagCommand tagCommand = new TagCommand(Index.fromZeroBased(1), "VIP");
        String expected = TagCommand.class.getCanonicalName() + "{index="
                + tagCommand.index + ", tag="
                + tagCommand.tag + "}";
        assertEquals(expected, tagCommand.toString());
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        TagCommand tagCommand = new TagCommand(outOfBoundIndex, VALID_TAG);

        assertCommandFailure(tagCommand, model, TagCommand.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void execute_invalidTag_throwsCommandException() {
        Index index = Index.fromOneBased(1);
        TagCommand tagCommand = new TagCommand(index, INVALID_TAG);

        assertCommandFailure(tagCommand, model, Tag.MESSAGE_CONSTRAINTS);
    }
}
