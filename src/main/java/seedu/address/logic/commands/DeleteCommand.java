package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.passenger.Passenger;

/**
 * Deletes a passenger identified using it's displayed index from the passenger list.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the passenger identified by the index number used in the displayed passenger list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PASSENGER_SUCCESS = "Deleted Passenger(s): %1$s";
    public static final String MESSAGE_DELETE_PASSENGER_FAIL_HAS_POOL = "Failed to delete. One or more Pools "
            + "contain Passenger(s): %1$s.";
    public static final String MESSAGE_DELETE_PASSENGER_FAIL_HAS_POOL_OTHERS_DELETED = "Deleted Passenger(s): %1$s.\n"
            + "However failed to delete some passengers as one or more Pools contain Passenger(s): %2$s.";

    private final List<Index> targetIndexes;

    public DeleteCommand(List<Index> targetIndexes) {
        this.targetIndexes = targetIndexes;
    }

    private static String printPassengersInList(List<Passenger> passengers) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < passengers.size(); i++) {
            sb.append(passengers.get(i).getName());

            if (i < passengers.size() - 1) {
                sb.append(", ");
            }
        }

        return sb.toString();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Passenger> lastShownList = model.getFilteredPassengerList();
        List<Passenger> targetedPassengers = new ArrayList<>();

        for (Index targetIndex : targetIndexes) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PASSENGER_DISPLAYED_INDEX);
            }

            Passenger passengerToDelete = lastShownList.get(targetIndex.getZeroBased());
            targetedPassengers.add(passengerToDelete);
        }

        List<Passenger> passengerWithPools = new ArrayList<>();
        List<Passenger> deletedPassengers = new ArrayList<>();

        for (Passenger p : targetedPassengers) {
            if (!model.deletePassenger(p)) {
                passengerWithPools.add(p);
            } else {
                deletedPassengers.add(p);
            }
        }

        if (passengerWithPools.size() > 0) {
            String passengerNames = printPassengersInList(passengerWithPools);

            if (deletedPassengers.size() == 0) {
                throw new CommandException(String.format(MESSAGE_DELETE_PASSENGER_FAIL_HAS_POOL, passengerNames));
            } else {
                String deletedPassengersNames = printPassengersInList(deletedPassengers);

                throw new CommandException(String.format(MESSAGE_DELETE_PASSENGER_FAIL_HAS_POOL_OTHERS_DELETED,
                        deletedPassengersNames, passengerNames));
            }
        } else {
            String deletedPassengersNames = printPassengersInList(deletedPassengers);

            return new CommandResult(String.format(MESSAGE_DELETE_PASSENGER_SUCCESS, deletedPassengersNames));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndexes.equals(((DeleteCommand) other).targetIndexes)); // state check
    }
}
