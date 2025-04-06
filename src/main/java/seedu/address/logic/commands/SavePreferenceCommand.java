package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Preference;

/**
 * A command to save preference
 */
public class SavePreferenceCommand extends Command {

    public static final String COMMAND_WORD = "savePreference";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds the preference to the customer identified "
            + "by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "s/PREFERENCE\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "s/no seafood";

    public static final String MESSAGE_SUCCESS = "Added %s to %s";
    public static final String MESSAGE_INVALID_INDEX = "The index is outside the acceptable range!";
    public static final String MESSAGE_REPEAT_PREF = "This Preference has already been added!";

    public final Index index;
    public final String preference;

    /**
     * @param index of the person in the address book to add preference
     * @param preference description of the preference to be added
     */
    public SavePreferenceCommand(Index index, String preference) {
        requireAllNonNull(index, preference);

        this.index = index;
        this.preference = preference;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_INDEX);
        }
        if (preference.isBlank() || !Preference.isValidPreference(preference)) {
            throw new CommandException(Preference.MESSAGE_CONSTRAINTS);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        String normalizedPref = preference.trim().toLowerCase().replaceAll("\\s+", " ");
        String prefString = personToEdit.getPreference().toString(); // e.g., [no beef, no seafood]
        prefString = prefString.substring(1, prefString.length() - 1); // remove [ and ]
        List<String> currentPrefs = List.of(prefString.split(",\\s*"));
        boolean isDuplicate = currentPrefs.stream()
                .map(p -> p.trim().toLowerCase().replaceAll("\\s+", " "))
                .anyMatch(p -> p.equals(normalizedPref));
        if (isDuplicate) {
            throw new CommandException(MESSAGE_REPEAT_PREF);
        }
        personToEdit.addPreference(preference); // Add original formatting
        model.setPerson(personToEdit, personToEdit);
        return new CommandResult(String.format(MESSAGE_SUCCESS, preference, Messages.format(personToEdit)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SavePreferenceCommand)) {
            return false;
        }

        SavePreferenceCommand e = (SavePreferenceCommand) other;
        return index.equals(e.index)
                && preference.equals(e.preference);
    }

    @Override
    public String toString() {
        return SavePreferenceCommand.class.getCanonicalName() + "{index="
                + index + ", preference="
                + preference + "}";
    }
}
