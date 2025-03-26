---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# BiteBook User Guide

BiteBook is a **desktop app for managing customer details, optimized for use via a  Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, BiteBook can get your customer management tasks done faster than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `.jar` file from [here](https://github.com/se-edu/addressbook-level3/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar addressbook.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432` : Adds a contact named `John Doe` to the Address Book.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `find Chicken rice` : Finds all customer who ordered chicken rice.
   
   * `findPreferences no salt` : Finds all customer who prefer to not have salt.

   * `savePreferences 1 no salt` : Add the preference "no salt" for customer in the 1st position

   * `tag 2 t/VIP` : Tag the 2nd customer in contact book as VIP

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/VIP` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[s/PREFERENCE]…​` can be used as ` ` (i.e. 0 times), `s/no beef`, `t/no beef t/no pork` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

### Viewing help : `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a customer: `add`

Adds a customer to the address book.

Format: `add n/NAME p/PHONE_NUMBER [t/TAG] [s/PREFERENCE]…​`

<box type="tip" seamless>

**Tip:** A person can have any number of tags (including 0)
</box>

Examples:
* `add n/John Doe p/98765432 t/VIP s/No salt`

### Listing all customers : `list`

Shows a list of all customers in the address book.

Format: `list`

### Editing a customer : `edit`

Edits an existing customer in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

### Locating customers by name: `find`

Finds customers whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting a customer : `delete`

Deletes the specified customer from the address book.

Format: `delete INDEX`

* Deletes the customer at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### Tag a customer : `tag`

Tags a customer in the address book as VIP, Regular or New.

Format: `tag INDEX t/TAG`

* Tags the customer at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​
* Existing tags of the person will be removed and replaced by the input tag.
* If multiple tags are provided, only the last tag will be saved.

Examples:
* `list` followed by `tag 2 t/VIP` tags the 2nd person in the address book as VIP.
* `tag 1 t/Regular t/VIP` tags the 1st person in the address book as VIP.
* `tag 1 t/` will untag a customer.

### Finding customers by tag : `findTag`

Finds all customers that has the tag that matches

Format: `findTag TAG`

* Lists all customers that are tagged as `PREFERENCE`.
* The `TAG` must be one of the following: VIP, Regular, New.
* The search is case-insensitive. e.g. `ReGuLaR` will match with `Regular`.
* Only tags are searched.

Examples:
* `findTag VIP` will list all customers that have been tagged `VIP`.

### Store a customer's dietary preferences : `savePreference`

Stores a customer's dietary preferences in the address book.

Format: `savePreference INDEX s/PREFERENCE`

* Stores the dietary preference of the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `savePreference 2 s/no beef` stores the dietary preference of the 2nd person in the address book as `no beef`.
* `savePreference 1 s/no beef s/no fish` will save multiple dietary preferences for a customer.

### Finding customers by dietary preferences : `findPreferences`

Finds all customers that has a preference that matches

Format: `findPreferences PREFERENCE`

* Lists all customers that have `PREFERENCE` saved as their dietary preference.
* The search is case-insensitive. e.g. `no seafood` will match with `No SeAfoOD`
* The search is space-sensitive. e.g. `no seafood` will not match with `noseafood` or `no  seafood `
* Only dietary preferences are searched.

Examples:
* `findPreferences no fish` will list all customers that have `no fish` under their dietary preferences.

### View a customer's past orders : `viewOrders`

Views a customer's past orders in the address book.

Format: `viewOrders INDEX`

* Views the past orders of the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `viewOrders 2` views the past orders of the 2nd person in the address book.

### Locating customers by past orders: `findOrders`

Finds persons whose past orders contain any of the given keywords.

Format: `findOrders KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `milo` will match `Milo`
* The order of the keywords does not matter. e.g. `Milo Soup` will match `Soup Milo`
* Only the orders is searched.
* Only full words will be matched e.g. `ChickenRice` will not match `Chicken`

Examples:
* `find milo` returns `John` and `Alice` who both ordered `milo`

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action              | Format, Examples                                                                          |
|---------------------|-------------------------------------------------------------------------------------------|
| **Add**             | `add n/NAME p/PHONE_NUMBER [t/TAG] [s/PREFERENCE]` <br> e.g., `add n/James Ho p/22224444` |
| **Delete**          | `delete INDEX`<br> e.g., `delete 3`                                                       |
| **Tag**             | `tag INDEX t/TAG`<br> e.g.,`tag 1 t/VIP`                                                  |
| **Find tag**        | `findTag TAG`<br> e.g., `findTag VIP`                                                     |
| **Save preference** | `savePreference INDEX s/PREFERENCE`<br> e.g., `savePreference 1 s/No seafood`             |
| **Find**            | `find KEYWORD [MORE_KEYWORDS]` <br> e.g., `find john`                                     |
| **Find preference** | `findPreferences PREFERENCE`<br> e.g., `findPreferences No seafood`                       |
| **View orders**     | `viewOrders INDEX` <br> e.g., `viewOrders 1`                                              |
| **Find orders**     | `findOrders KEYWORD [MORE_KEYWORDS]` <br> e.g., `findOrders milo`                         |
| **Help**            | `help`                                                                                    |
