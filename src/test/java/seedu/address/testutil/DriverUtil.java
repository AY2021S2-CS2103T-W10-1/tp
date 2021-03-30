package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMUTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.model.person.driver.Driver;

/**
 * A utility class for Driver.
 */
public class DriverUtil {

    // TODO should this method append the prefixes, compare this to passengerUtil methods
    public static String getDriverDetails(Driver driver) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + driver.getName().fullName + " ");
        sb.append(PREFIX_PHONE + driver.getPhone().value + " ");
        return sb.toString();
    }

    public static String getCommuterDetails(Set<Index> indices) {
        StringBuilder sb = new StringBuilder();
        for (Index idx : indices) {
            sb.append(PREFIX_COMMUTER + String.valueOf(idx.getOneBased()) + " ");
        }
        return sb.toString();
    }
}
