package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMUTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRIPDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRIPTIME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_POOLS;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.TripDay;
import seedu.address.model.TripTime;
import seedu.address.model.person.driver.Driver;
import seedu.address.model.person.passenger.Passenger;
import seedu.address.model.pool.Pool;
import seedu.address.model.tag.Tag;

/**
 * Associates a Driver with the selected Passengers.
 */
public class PoolCommand extends Command {
    public static final String COMMAND_WORD = "pool";
    public static final long MAX_TIME_DIFFERENCE = 15;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Pools commuters together with a driver. "
            + "Parameters: "
            + PREFIX_NAME + "DRIVER NAME "
            + PREFIX_PHONE + "DRIVER PHONE "
            + PREFIX_TRIPDAY + "TRIP DAY "
            + PREFIX_TRIPTIME + "TRIP TIME "
            + PREFIX_COMMUTER + "COMMUTER "
            + "[" + PREFIX_COMMUTER + "COMMUTER]... "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Florence Lee "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_TRIPDAY + "monday "
            + PREFIX_TRIPTIME + "1930 "
            + PREFIX_COMMUTER + "1 "
            + PREFIX_COMMUTER + "4 "
            + PREFIX_TAG + "female";

    public static final String MESSAGE_NO_COMMUTERS = "No commuters were selected.";
    public static final String MESSAGE_POOL_SUCCESS = "Successfully created pool: %s";
    public static final String MESSAGE_POOL_SUCCESS_WITH_WARNING = "Successfully created pool: %s. \nHowever, note that"
            + " you have passengers with time differences with the pool time of more than 15 minutes.";
    public static final String MESSAGE_DUPLICATE_POOL = "This pool already exists in the GME Terminal";
    public static final String MESSAGE_TRIPDAY_MISMATCH = "One of the passengers specified "
            + "have a trip day that does not match this pool driver's trip day";

    private final Driver driver;
    private final TripDay tripDay;
    private final TripTime tripTime;
    private final Set<Index> indexes;
    private final Set<Tag> tags;

    /**
     * Creates a PoolCommand that adds a pool specified by {@code Driver}, {@code TripDay}, {@code TripTime}
     * with passengers specified.
     */
    public PoolCommand(Driver driver, Set<Index> indexes, TripDay tripDay, TripTime tripTime, Set<Tag> tags) {
        requireNonNull(driver);
        requireNonNull(indexes);
        requireNonNull(tripDay);
        requireNonNull(tripTime);
        this.driver = driver;
        this.indexes = indexes;
        this.tripDay = tripDay;
        this.tripTime = tripTime;
        this.tags = tags;
    }

    private boolean checkTimeDifference(List<Passenger> passengers) {
        return passengers.stream()
                .anyMatch(x -> x.getTripTime().compareMinutes(this.tripTime) > MAX_TIME_DIFFERENCE);
    }
    private List<Passenger> getPassengersFromIndexes(Set<Index> indexes, Model model) throws CommandException {

        List<Passenger> lastShownList = List.copyOf(model.getFilteredPassengerList());

        List<Passenger> passengers = new ArrayList<>();

        for (Index idx : indexes) {
            if (idx.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PASSENGER_DISPLAYED_INDEX);
            }
            Passenger passenger = lastShownList.get(idx.getZeroBased());
            assert passenger != null : "passenger should not be null";

            boolean isTripDayMismatch = !passenger.getTripDay().equals(tripDay);
            if (isTripDayMismatch) {
                throw new CommandException(MESSAGE_TRIPDAY_MISMATCH);
            }
            passengers.add(passenger);
        }

        return passengers;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (indexes.size() == 0) {
            throw new CommandException(MESSAGE_NO_COMMUTERS);
        }

        List<Passenger> passengers = getPassengersFromIndexes(indexes, model);
        boolean shouldWarn = checkTimeDifference(passengersToPool);

        Pool toAdd = new Pool(driver, tripDay, tripTime, passengers, tags);

        if (model.hasPool(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_POOL);
        }

        model.addPool(toAdd);
        model.updateFilteredPoolList(PREDICATE_SHOW_ALL_POOLS);

        String outputMessage = shouldWarn ? MESSAGE_POOL_SUCCESS_WITH_WARNING : MESSAGE_POOL_SUCCESS;

        return new CommandResult(String.format(outputMessage, driver, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PoolCommand // instanceof handles nulls
                && (driver.equals(((PoolCommand) other).driver)
                && indexes.equals(((PoolCommand) other).indexes)));
    }
}
