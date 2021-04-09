package seedu.address.model.person.passenger;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PassengerBuilder;

public class PriceContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        Double firstPredicatePrice = VALID_PRICE_AMY;
        Double secondPredicatePrice = VALID_PRICE_BOB;

        PriceContainsKeywordsPredicate firstPredicate = new PriceContainsKeywordsPredicate(firstPredicatePrice);
        PriceContainsKeywordsPredicate secondPredicate = new PriceContainsKeywordsPredicate(secondPredicatePrice);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PriceContainsKeywordsPredicate firstPredicateCopy = new PriceContainsKeywordsPredicate(firstPredicatePrice);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different passenger -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_passengerHasPrice() {
        // Passenger price equals to Predicate
        PriceContainsKeywordsPredicate predicate = new PriceContainsKeywordsPredicate(VALID_PRICE_AMY);
        assertTrue(predicate.test(new PassengerBuilder().withPrice(VALID_PRICE_AMY).build()));

        // Passenger price bigger than Predicate
        predicate = new PriceContainsKeywordsPredicate(VALID_PRICE_AMY);
        assertTrue(predicate.test(new PassengerBuilder().withPrice(VALID_PRICE_BOB).build()));

        // Passenger price smaller than Predicate
        predicate = new PriceContainsKeywordsPredicate(VALID_PRICE_BOB);
        assertFalse(predicate.test(new PassengerBuilder().withPrice(VALID_PRICE_AMY).build()));
    }

    @Test
    public void test_passengerWithoutPrice_returnsFalse() {
        PriceContainsKeywordsPredicate predicate = new PriceContainsKeywordsPredicate(VALID_PRICE_AMY);
        assertFalse(predicate.test(new PassengerBuilder().build()));
    }
}
