package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.ArgumentMultimap.arePrefixesPresent;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLIENTTAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INCOME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTHLY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLANTAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RISKTAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.time.format.DateTimeParseException;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddAppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.DateTime;
import seedu.address.model.person.Location;

/**
 * Parses input arguments and creates a new AddAppointmentCommand object
 */
public class AddAppointmentCommandParser implements Parser<AddAppointmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * AddAppointmentCommand and returns an AddAppointmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddAppointmentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_APPOINTMENT_DATE, PREFIX_APPOINTMENT_LOCATION, PREFIX_NAME,
                        PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_INCOME, PREFIX_MONTHLY,
                        PREFIX_RISKTAG, PREFIX_PLANTAG, PREFIX_CLIENTTAG, PREFIX_TAG);

        if (argMultimap.getPreamble().isEmpty()
            || !arePrefixesPresent(argMultimap, PREFIX_APPOINTMENT_DATE, PREFIX_APPOINTMENT_LOCATION)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddAppointmentCommand.MESSAGE_USAGE));
        }

        Index personIndex;

        Appointment appointment;
        DateTime appointmentDateTime;
        Location appointmentLocation;

        try {
            if (argMultimap.getValue(PREFIX_NAME).isPresent() || argMultimap.getValue(PREFIX_PHONE).isPresent()
                    || argMultimap.getValue(PREFIX_EMAIL).isPresent()
                    || argMultimap.getValue(PREFIX_ADDRESS).isPresent()
                    || argMultimap.getValue(PREFIX_RISKTAG).isPresent()
                    || argMultimap.getValue(PREFIX_PLANTAG).isPresent()
                    || argMultimap.getValue(PREFIX_CLIENTTAG).isPresent()
                    || argMultimap.getValue(PREFIX_INCOME).isPresent()
                    || argMultimap.getValue(PREFIX_MONTHLY).isPresent()
                    || argMultimap.getValue(PREFIX_TAG).isPresent()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        AddAppointmentCommand.MESSAGE_USAGE));
            }
            appointmentDateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_APPOINTMENT_DATE).get());
            appointmentLocation = ParserUtil.parseLocation(argMultimap.getValue(PREFIX_APPOINTMENT_LOCATION).get());
            appointment = ParserUtil.parseAppointment(appointmentDateTime.toString(), appointmentLocation.toString());
        } catch (DateTimeParseException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddAppointmentCommand.MESSAGE_USAGE));
        }

        personIndex = ParserUtil.parseIndex(argMultimap.getPreamble());

        return new AddAppointmentCommand(personIndex, appointment);
    }
}
