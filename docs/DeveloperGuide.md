---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# BiteBook Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

This project is based on the AddressBook-Level3 project created by the SE-EDU team. The original project was developed as part of the CS2103T Software Engineering module at the National University of Singapore (NUS). The AddressBook-Level3 project is an open-source project that serves as a template for students to learn software engineering principles and practices.

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/AY2425S2-CS2103T-T08-1/tp/blob/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2425S2-CS2103T-T08-1/tp/blob/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Storing a customer's dietary preference feature

Given below is how the storing mechanism occurs:

Step 1: The user launches the application.

Step 2: The user executes `savePreference 1 s/Chicken rice`, causing a preference to be added to the customer.

The following sequence diagram shows how an add preference operation goes through the Logic component:

<puml src="diagrams/SavePreferenceDiagram-Logic.puml" alt="SavePreferenceDiagram-Logic" />

### Finding customers by dietary preferences feature

Given below is how the filtering mechanism occurs:

Step 1: The user launches the application.

Step 2: The user executes `findPreferences no seafood`, causing customers who have that preference to be shown.

The following sequence diagram shows how a find preference operation goes through the Logic component:

<puml src="diagrams/FindPreferencesDiagram.puml" alt="FindPreferencesDiagram" />
--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* Restaurant owners who want to keep track of their clientele and regular customers

**Value proposition**:
* Improve the quality of service for customers by enabling regulars to contact easily their favourite restaurant
* Increase interpersonal relationships with customers by keeping track of their preferences



### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​          | I want to …​                                                                         | So that I can…​                            |
|----------|------------------|--------------------------------------------------------------------------------------|--------------------------------------------|
| `* * *`  | Restaurant owner | add new customers to the address book                                                | keep track of their contact details        |
| `* * *`  | Restaurant owner | delete customer records                                                              | remove outdated or incorrect information   |
| `* * *`  | Restaurant owner | tag customers with labels (e.g., VIP, Regular, New)                                  | personalize their experience               |
| `* * *`  | Restaurant owner | store customer preferences and allergies                                             | provide a personalized dining experience.  |
| `* *`    | Restaurant owner | customer feedback and complaints (To add in the future)                              | improve my restaurant’s service            |
| `* *`    | Restaurant owner | see a customer’s past orders (To add in the future)                                  | recommend seasonal dishes they might enjoy |
| `* *`    | Restaurant owner | group customers by dining preferences (e.g., vegetarian, wine lovers, family diners) | offer them relevant recommendations        |
| `* *`    | Restaurant owner | track customer satisfaction scores based on their feedback (To add in the future)    | improve my service.                        |

### Use cases

(For all use cases below, the **System** is `BiteBook` and the **Actor** is the `Restaurant owner`, unless specified otherwise)

**Use case: Add new customers to the address book**

**MSS**

1.  User enters into the command line (add n/Tom p/123456789)
2.  The contact information is added into the address book with a success text

    Use case ends.

**Extensions**

* 1a. The same entry exists (i.e same name and number)

    * 1a1. Bitebook shows an error message.

    Use case ends.

**Use case: Delete customers in the address book**

**MSS**

1.  User list all customers
2.  User enters into the command line (delete 1)
3.  The first contact information is deleted

    Use case ends.

**Extensions**

* 1a. The list is empty.

  Use case ends.

* 2a. The given index is invalid.

    * 2a1. Bitebook shows an error message.

      Use case resumes at step 2.

**Use case: Tag customers with labels (e.g., VIP, Regular, New)**

**MSS**

1.  User list all customers
2.  User enters into the command line (tag 1 t/VIP)
3.  The first contact information is tagged with VIP

    Use case ends.

**Extensions**

* 1a. The list is empty.

  Use case ends.

* 2a. The given index is invalid.

    * 2a1. Bitebook shows an error message.

    Use case ends.

* 2b. The tag is invalid/

    * 2b1. Bitebook shows an error message.

      Use case resumes at step 2.

**Use case: Add orders for customer**

**MSS**

1.  User list all customers
2.  User enters into the command line (addOrder 1 d/Fish soup)
3.  fish soup appears in the top 3 dishes

    Use case ends.

**Extensions**

* 1a. The list is empty.

  Use case ends.

* 2a. The given index is invalid.

    * 2a1. Bitebook shows an error message.

    Use case ends.

* 2b. The dish already exists in the customer's past orders

    * 2b1. If the top 3 dishes have a higher count than fish soup, then fish soup does not appear
    
     Use case ends.

    * 2b2. fish soup 

     Use case resumes at step 2.

* 3a. No prefix (/d) is added

    * 3a1. Bitebook shows an error message.

    Use case ends.

**Use case: Find all customers with a certain preference**

**MSS**

1.  User enters a valid findPreferences command (findPreferences No seafood)
2.  Lists all customers matching the preference 

Use case ends.

**Extensions**

* 1a. The list is empty.

  Use case ends.

* 2a. No customers have the given preference

    * 2a1. Shows "0 persons listed!"

    Use case ends.

* 3a. No preferences provided

    * 3a1. Bitebook shows an error message.

    Use case ends.

**Use case: Find all customers with a certain order**

**MSS**

1.  User enters a valid findOrders command (findOrders d/Fish soup)
2.  Lists all customers with the order

Use case ends.

**Extensions**

* 1a. The list is empty.

  Use case ends.

* 2a. No customers have the given dish

    * 2a1. Shows "0 persons listed!"

  Use case ends.

* 3a. NNo prefix (/d) is added

    * 3a1. Bitebook shows an error message.

  Use case ends.

**Use case: Save preference for a customer**

**MSS**

1.  User enters a valid savePreference command (savePreference 1 s/no salt)
2.  The first contact information preference is saved as no salt

Use case ends.

**Extensions**

* 1a. The list is empty.

  Use case ends.

* 2a. The given index is invalid.

    * 2a1. Bitebook shows an error message.

    Use case ends.

* 3a. No prefix (/s) is added

    * 3a1. Bitebook shows an error message.

    Use case ends.

* 4a. There is an existing preference saved for the customer

    * 4a1. The existing preference becomes the new preference 

    Use case ends.

**Use case: Clear all contact**

**MSS**

1.  User enters a valid clear command (clear)

Use case ends.

**Use case: exit**

**MSS**

1.  User enters a valid exit command (exit)

Use case ends.

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  The response to any use action should become visible within 5 seconds.
5.  The source code should be open source.
6.  The user interface should be intuitive enough for users who are not IT-savvy.
7.  The product is offered as a free online service.

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Private contact detail**: A contact detail that is not meant to be shared with others
* **Tags**: A marker flavour text for each customer (E.g VIP)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.


---

###  1. Add a New Customer

Add a customer named Alice with preferences and a tag:  
`add n/Alice Tan p/91234567 t/Regular s/no seafood`

---

###  2. Tag a Customer

Tag the customer with:  
`tag 1 t/NEW`

Test:
- Overwriting existing tag
- Clearing tag with:  
  `tag 1 t/`

---

### 3. Save Preferences

Add dietary preferences:  
`savePreference 1 s/no dairy`  
`savePreference 1 s/no seafood`

Test:
- Duplicate prevention:  
  `savePreference 1 s/no dairy`

---

###  4. Add Past Orders

Add multiple dishes (with variations in spacing and case):  
`addOrder 1 d/Chicken Rice`  
`addOrder 1 d/iced milo`  
`addOrder 1 d/Iced Milo`  
`addOrder 1 d/Nasi Lemak`  
`addOrder 1 d/Nasi Lemak`

Test:
- Frequency sorting in UI top 3
- Normalization (case & spacing)

---

###  5. View Past Orders

View the customer’s order history:  
`viewOrders 1`

Check that:
- Orders are displayed
- Most frequent ones appear in top 3 dishes

---

###  6. Find by Preferences

Search by dietary preferences:  
`findPreferences no seafood`  
`findPreferences no dairy`

---

###  7. Find by Order History

Search by past orders:  
`findOrders nasi lemak`  
`findOrders iced milo`

---

###  8. Find by Name / Tag

Search by name:  
`find alice`  
Search by tag:  
`findTag VIP`

---

###  9. Delete Customer

Delete the customer from the list:  
`delete 1`

Ensure:
- Person is removed
- Indexes shift appropriately

---

###  10. Clear All Entries

Reset the app to empty state:  
`clear`

Ensure:
- No customers remain
- App behaves normally post-clear


### Saving data

1. Dealing with missing/corrupted data files

   1. Prerequisites: Executed the BiteBook jar file.
   2. Delete the `data` folder in the same directory as the jar file.
   3. Re-launch the app by double-clicking the jar file.
   4. Expected: The app should create a new `data` folder and a new `addressbook.json` file in it. The app should show the GUI with a set of sample contacts.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Planned Enhancements**

Team size: 5

1. **Make add command handle multiple preferences**: The current `add` command only supports a single preference, but a customer can have multiple preferences. We plan to modify the `add` command to accept multiple preference inputs, allowing users to specify more than one preference when adding a contact.
2. **Allow the use of `/` and `'` in names**: The current `add` command only allows alphanumeric characters in the name field. We plan to improve this by allowing characters such as `/` and `'`, so names like `s/o John` can be properly added to BiteBook.
3. **Normalize spacing in names**: When adding a customer details, any internal spaces in the names are not handled, so it allows `John Doe` and `John   Doe` with the same phone number can be added into BiteBook. To solve this, we plan to collapse the multiple spaces between words into a single space. For example, both `John Doe` and `John   Doe` will be stored as `John Doe` to ensure consistent formatting.
4. **Menu Integration for Order Consistency**:To reduce variability in order entries (e.g., `milo` vs `iced milo` or `ice milo`), a fixed menu system will be implemented. This menu will contain standardized food and drink items that customers can select from when placing an order. The saveOrder function will be updated to only allow selections from this predefined menu, improving data consistency and enabling better analytics.
5. **Editable Tag System for Customer Categorization**: Currently, customer tags may be too rigid or inconsistent. To offer more flexibility, restaurant owners will be able to edit and manage the fixed list of available tags (e.g., `VIP`, `Vegetarian`, `Allergy-prone`). This ensures the tags remain relevant to the restaurant’s evolving customer base and operational needs, while still enforcing consistency in their application.