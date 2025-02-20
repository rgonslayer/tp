package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's appointment location in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidLocation(String)}
 */
public class Location {

    public static final String MESSAGE_CONSTRAINTS = "Appointment locations can take any values, "
                                                        + "and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Location}.
     *
     * @param location A valid Location.
     */
    public Location(String location) {
        requireNonNull(location);
        String trimmedStr = location.trim().replaceAll(" +", " ");
        checkArgument(isValidLocation(trimmedStr), MESSAGE_CONSTRAINTS);
        value = trimmedStr;
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidLocation(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Location // instanceof handles nulls
                && value.equals(((Location) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
