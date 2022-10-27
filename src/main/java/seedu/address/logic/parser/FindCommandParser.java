package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.ArgumentMultimap.arePrefixesPresent;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INCOME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTHLY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLANTAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RISKTAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.NormalTagContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;
import seedu.address.model.person.PlanTagContainsKeywordsPredicate;
import seedu.address.model.person.RiskTagContainsKeywordsPredicate;
import seedu.address.model.tag.NormalTag;
import seedu.address.model.tag.PlanTag;
import seedu.address.model.tag.RiskTag;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        List<Predicate<Person>> predicates = new ArrayList<>();
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_RISKTAG,
                        PREFIX_PLANTAG, PREFIX_TAG);

                String tokenizedArgs = trimmedArgs.substring(2);
        String[] keywords = tokenizedArgs.split("\\s+");

        if (!argMultimap.getPreamble().isEmpty()
                || arePrefixesPresent(argMultimap, PREFIX_ADDRESS, PREFIX_INCOME, PREFIX_MONTHLY, PREFIX_EMAIL,
                PREFIX_APPOINTMENT_DATE, PREFIX_APPOINTMENT_LOCATION)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            List<Name> names = ParserUtil.parseAllSpaceSeparatedNames(argMultimap.getAllValuesSeparatedBySpace(PREFIX_NAME));
            predicates.add(new NameContainsKeywordsPredicate(names.stream().map(x -> x.toString()).collect(Collectors.toList())));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            List<Phone> phones = ParserUtil.parseAllSpaceSeparatedPhone(argMultimap.getAllValuesSeparatedBySpace(PREFIX_PHONE));
            predicates.add(new PhoneContainsKeywordsPredicate(phones.stream().map(x -> x.toString()).collect(Collectors.toList())));
        }
        if (argMultimap.getValue(PREFIX_RISKTAG).isPresent()) {
            List<RiskTag> riskTags = ParserUtil.parseAllSpaceSeparatedRiskTag(argMultimap.getAllValuesSeparatedBySpace(PREFIX_RISKTAG));
            predicates.add(new RiskTagContainsKeywordsPredicate(riskTags.stream().map(x -> x.tagName).collect(Collectors.toList())));
        }
        if (argMultimap.getValue(PREFIX_PLANTAG).isPresent()) {
            List<PlanTag> planTags = ParserUtil.parseAllSpaceSeparatedPlanTags(argMultimap.getAllValuesSeparatedBySpace(PREFIX_PLANTAG));
            predicates.add(new PlanTagContainsKeywordsPredicate(planTags.stream().map(x -> x.toString()).collect(Collectors.toList())));
        }
        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            List<NormalTag> normalTags = ParserUtil.parseAllSpaceSeparatedNormalTags(argMultimap.getAllValuesSeparatedBySpace(PREFIX_TAG));
            predicates.add(new NormalTagContainsKeywordsPredicate(normalTags.stream().map(x -> x.toString()).collect(Collectors.toList())));
        }

        return new FindCommand(predicates);

//        // 2 because all except PREFIX_PLANTAG has a prefix of length 2
//        String tokenizedArgs = trimmedArgs.substring(2);
//        String[] keywords = tokenizedArgs.split("\\s+");
//
//        if (trimmedArgs.startsWith(PREFIX_RISKTAG.getPrefix())) {
//            checkIfRiskTag(keywords);
//            return new FindCommand(new RiskTagContainsKeywordsPredicate(Arrays.asList(keywords)));
//        } else if (trimmedArgs.startsWith(PREFIX_TAG.getPrefix())) {
//            return new FindCommand(new NormalTagContainsKeywordsPredicate(Arrays.asList(keywords)));
//        } else if (trimmedArgs.startsWith(PREFIX_NAME.getPrefix())) {
//            return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
//        } else if (trimmedArgs.startsWith(PREFIX_PHONE.getPrefix())) {
//            return new FindCommand(new PhoneContainsKeywordsPredicate(Arrays.asList(keywords)));
//        } else if (trimmedArgs.startsWith(PREFIX_PLANTAG.getPrefix())) {
//            String planTag = trimmedArgs.substring(3);
//            // since all planTag has a space and ends with Plan, we split the input every second space.
//            // planTag - Savings Plan.
//            String[] tags = planTag.split("(?<=\\s\\S{1,100})\\s");
//            return new FindCommand(new PlanTagContainsKeywordsPredicate(Arrays.asList(tags)));
//        } else {
//            throw new ParseException(
//                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, "Please enter a prefix before the KEYWORDs."));
//        }
    }

    private void checkIfRiskTag(String[] tags) throws ParseException {
        for (String tag : tags) {
            // if tag is not a valid Risk tag
            if (!RiskTag.isRiskTag(tag)) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, RiskTag.MESSAGE_CONSTRAINTS));
            }
        }
    }

}
