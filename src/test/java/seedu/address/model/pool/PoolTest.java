package seedu.address.model.pool;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_GOLF;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TRIPDAY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TRIPTIME_BOB;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPools.HOMEPOOL;
import static seedu.address.testutil.TypicalPools.OFFICEPOOL;

import java.time.DayOfWeek;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PassengerListBuilder;
import seedu.address.testutil.PoolBuilder;
import seedu.address.testutil.TypicalDrivers;
import seedu.address.testutil.TypicalPassengers;


public class PoolTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Pool pool = new PoolBuilder()
                .withPassengers(
                        new PassengerListBuilder()
                        .withDefaultPassengers()
                        .build())
                .build();
        assertThrows(UnsupportedOperationException.class, () -> pool.getTags().remove(0));
    }

    @Test
    public void isSamePool() {
        // same object -> returns true
        assertTrue(HOMEPOOL.isSamePool(HOMEPOOL));

        // null -> returns false
        assertFalse(HOMEPOOL.isSamePool(null));

        // same details, tags different -> returns true
        Pool editedHomePool = new PoolBuilder(HOMEPOOL)
                .withTags(VALID_TAG_GOLF).build();
        assertTrue(HOMEPOOL.isSamePool(editedHomePool));

        // different details, same tags -> returns false
        editedHomePool = new PoolBuilder(HOMEPOOL)
                .withDriver(TypicalDrivers.BOB)
                .withTripDay(DayOfWeek.WEDNESDAY)
                .withTripTime(LocalTime.of(14, 0))
                .withPassengers(new PassengerListBuilder()
                        .withPassenger(TypicalPassengers.ELLE)
                        .withPassenger(TypicalPassengers.DANIEL)
                        .withPassenger(TypicalPassengers.GEORGE)
                        .build())
                .build();
        assertFalse(OFFICEPOOL.isSamePool(editedHomePool));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Pool homePoolCopy = new PoolBuilder(HOMEPOOL).build();
        assertTrue(HOMEPOOL.equals(homePoolCopy));

        // same object -> returns true
        assertTrue(HOMEPOOL.equals(HOMEPOOL));

        // null -> returns false
        assertFalse(HOMEPOOL.equals(null));

        // different type -> returns false
        assertFalse(HOMEPOOL.equals(5));

        // different pool -> returns false
        assertFalse(HOMEPOOL.equals(OFFICEPOOL));

        // different driver -> returns false
        Pool editedAlice = new PoolBuilder(HOMEPOOL).withDriver(TypicalDrivers.FIONA).build();
        assertFalse(HOMEPOOL.equals(editedAlice));

        // different tripDay-> returns false
        editedAlice = new PoolBuilder(HOMEPOOL).withTripDay(VALID_TRIPDAY_BOB).build();
        assertFalse(HOMEPOOL.equals(editedAlice));

        // different tripTime -> returns false
        editedAlice = new PoolBuilder(HOMEPOOL).withTripTime(VALID_TRIPTIME_BOB).build();
        assertFalse(HOMEPOOL.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PoolBuilder(HOMEPOOL).withTags(VALID_TAG_GOLF).build();
        assertFalse(HOMEPOOL.equals(editedAlice));
    }
}
