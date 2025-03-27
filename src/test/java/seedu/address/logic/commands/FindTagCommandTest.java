package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.TagContainsKeywordPredicate;
import seedu.address.model.tag.Tag;

public class FindTagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new seedu.address.model.UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new seedu.address.model.UserPrefs());

    @Test
    public void execute_validTag_customersFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        Tag tag = new Tag("VIP");
        TagContainsKeywordPredicate predicate = new TagContainsKeywordPredicate(tag);
        FindTagCommand command = new seedu.address.logic.commands.FindTagCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(java.util.Arrays.asList(ALICE, DANIEL), model.getFilteredPersonList());
    }

    @Test
    public void execute_validTag_noCustomersFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        Tag tag = new Tag("NEW");
        TagContainsKeywordPredicate predicate = new TagContainsKeywordPredicate(tag);
        FindTagCommand command = new seedu.address.logic.commands.FindTagCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }
}
