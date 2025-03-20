package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code ViewOrdersCommand}.
 */
public class ViewOrdersCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_noOrders_success() {
        Person personToView = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ViewOrdersCommand viewOrdersCommand = new ViewOrdersCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(ViewOrdersCommand.MESSAGE_VIEWORDERS_SUCCESS,
                Messages.format(personToView)) + "No past orders.";

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(viewOrdersCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ViewOrdersCommand viewOrdersCommand = new ViewOrdersCommand(outOfBoundIndex);

        assertCommandFailure(viewOrdersCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_noOrders_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToView = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ViewOrdersCommand viewOrdersCommand = new ViewOrdersCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(ViewOrdersCommand.MESSAGE_VIEWORDERS_SUCCESS,
                Messages.format(personToView)) + "No past orders.";

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);

        assertCommandSuccess(viewOrdersCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        ViewOrdersCommand viewOrdersCommand = new ViewOrdersCommand(outOfBoundIndex);

        assertCommandFailure(viewOrdersCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ViewOrdersCommand viewOrdersFirstCommand = new ViewOrdersCommand(INDEX_FIRST_PERSON);
        ViewOrdersCommand viewOrdersSecondCommand = new ViewOrdersCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(viewOrdersFirstCommand.equals(viewOrdersFirstCommand));

        // same values -> returns true
        ViewOrdersCommand viewOrdersFirstCommandCopy = new ViewOrdersCommand(INDEX_FIRST_PERSON);
        assertTrue(viewOrdersFirstCommand.equals(viewOrdersFirstCommandCopy));

        // different types -> returns false
        assertFalse(viewOrdersFirstCommand.equals(1));

        // null -> returns false
        assertFalse(viewOrdersFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(viewOrdersFirstCommand.equals(viewOrdersSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        ViewOrdersCommand viewOrdersCommand = new ViewOrdersCommand(targetIndex);
        String expected = ViewOrdersCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, viewOrdersCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
