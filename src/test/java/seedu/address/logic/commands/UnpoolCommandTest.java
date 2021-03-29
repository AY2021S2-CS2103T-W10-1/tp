package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalPools.getTypicalAddressBookPools;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.pool.Pool;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code UnpoolCommandTest}.
 */
public class UnpoolCommandTest {

    private Model model = new ModelManager(getTypicalAddressBookPools(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Pool poolToRemove = model.getFilteredPoolList().get(INDEX_FIRST.getZeroBased());
        UnpoolCommand unpoolCommand = new UnpoolCommand(INDEX_FIRST);

        String expectedMessage = String.format(UnpoolCommand.MESSAGE_UNPOOL_SUCCESS, poolToRemove);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePool(poolToRemove);

        assertCommandSuccess(unpoolCommand, model, expectedMessage, expectedModel);
    }

    //TODO: Implement once list filtering is added
    /*@Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPoolList().size() + 1);
        UnpoolCommand unpoolCommand = new UnpoolCommand(outOfBoundIndex);

        assertCommandFailure(unpoolCommand, model, Messages.MESSAGE_INVALID_PASSENGER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        Pool poolToRemove = model.getFilteredPoolList().get(INDEX_FIRST.getZeroBased());
        UnpoolCommand unpoolCommand = new UnpoolCommand(INDEX_FIRST);

        String expectedMessage = String.format(UnpoolCommand.MESSAGE_UNPOOL_SUCCESS, poolToRemove);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePool(poolToRemove);
        showNoPassenger(expectedModel);

        assertCommandSuccess(unpoolCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPassengerList().size());

        UnpoolCommand unpoolCommand = new UnpoolCommand(outOfBoundIndex);

        assertCommandFailure(unpoolCommand, model, Messages.MESSAGE_INVALID_PASSENGER_DISPLAYED_INDEX);
    }*/

    @Test
    public void equals() {
        UnpoolCommand unpoolFirstCommand = new UnpoolCommand(INDEX_FIRST);
        UnpoolCommand unpoolSecondCommand = new UnpoolCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(unpoolFirstCommand.equals(unpoolFirstCommand));

        // same values -> returns true
        UnpoolCommand unpoolFirstCommandCopy = new UnpoolCommand(INDEX_FIRST);
        assertTrue(unpoolFirstCommand.equals(unpoolFirstCommandCopy));

        // different types -> returns false
        assertFalse(unpoolFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unpoolFirstCommand.equals(null));

        // different passenger -> returns false
        assertFalse(unpoolFirstCommand.equals(unpoolSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPassenger(Model model) {
        model.updateFilteredPoolList(p -> false);

        assertTrue(model.getFilteredPoolList().isEmpty());
    }
}
