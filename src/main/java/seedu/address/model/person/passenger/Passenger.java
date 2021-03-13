package seedu.address.model.person.passenger;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.driver.Driver;
import seedu.address.model.tag.Tag;

/**
 * Represents a Passenger in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Passenger extends Person {
    private static final String MESSAGE_NO_ASSIGNED_DRIVER = "No driver assigned to this passenger.";

    // Data fields
    private final Address address;
    private final TripDay tripDay;
    private final TripTime tripTime;
    private final Set<Tag> tags = new HashSet<>();
    private Optional<Driver> driver;

    /**
     * Every field must be present and not null.
     */
    public Passenger(Name name, Phone phone, Address address, TripDay tripDay, TripTime tripTime, Set<Tag> tags) {
        super(name, phone);
        requireAllNonNull(address, tripDay, tripTime, tags);
        this.address = address;
        this.tripDay = tripDay;
        this.tripTime = tripTime;
        this.driver = Optional.empty();
        this.tags.addAll(tags);
    }

    /**
     * Creates a new {@code Passenger} with a driver.
     * @param name the {@code Name} of the {@code Passenger}
     * @param phone the {@code Phone} of the {@code Passenger}
     * @param address the {@code Address} of the {@code Passenger}
     * @param tripDay the {@code TripDay} of the {@code Passenger}
     * @param tripTime the {@code TripTime} of the {@code Passenger}
     * @param driver the {@code Driver} assigned to {@code Passenger}
     * @param tags the {@code Tag}s of the {@code Passenger}
     */
    public Passenger(Name name, Phone phone, Address address, TripDay tripDay, TripTime tripTime, Driver driver,
                     Set<Tag> tags) {
        super(name, phone);
        requireAllNonNull(address, tripDay, tripTime, tags);
        this.address = address;
        this.tripDay = tripDay;
        this.tripTime = tripTime;
        this.driver = Optional.of(driver);
        this.tags.addAll(tags);
    }

    public Address getAddress() {
        return address;
    }

    public TripDay getTripDay() {
        return tripDay;
    }

    public TripTime getTripTime() {
        return tripTime;
    }

    public String getDriverStr() {
        return driver.map(Driver::toString).orElse(MESSAGE_NO_ASSIGNED_DRIVER);
    }

    public Optional<Driver> getDriver() {
        return driver;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePassenger(Passenger otherPassenger) {
        if (otherPassenger == this) {
            return true;
        }

        return otherPassenger != null
                && otherPassenger.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Passenger)) {
            return false;
        }

        Passenger otherPassenger = (Passenger) other;
        return otherPassenger.getName().equals(getName())
                && otherPassenger.getPhone().equals(getPhone())
                && otherPassenger.getAddress().equals(getAddress())
                && otherPassenger.getTripDay().equals(getTripDay())
                && otherPassenger.getTripTime().equals(getTripTime())
                && otherPassenger.getTags().equals(getTags())
                && otherPassenger.getDriver().equals(getDriver());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, address, tripDay, tripTime, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("; Phone: ")
                .append(getPhone())
                .append("; Address: ")
                .append(getAddress())
                .append("; Trip Day: ")
                .append(getTripDay())
                .append("; Trip Time: ")
                .append(getTripTime())
                .append("; Driver: ")
                .append(getDriverStr());

        Set<Tag> tags = getTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(builder::append);
        }
        return builder.toString();
    }

}
