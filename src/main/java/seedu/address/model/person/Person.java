package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.util.MaximumSortedList;
import seedu.address.model.calendar.CalendarEvent;
import seedu.address.model.tag.RiskTag;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {
    public static final int MAXIMUM_NUM_OF_APPOINTMENTS = 3;
    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final IncomeLevel incomeLevel;
    private final Monthly monthly;

    // Data fields
    private final Address address;
    private final RiskTag riskTag;
    private final Set<Tag> specialTags = new HashSet<>();

    private final Set<Tag> tags = new HashSet<>();
    private MaximumSortedList<Appointment> appointments = new MaximumSortedList<>(MAXIMUM_NUM_OF_APPOINTMENTS);

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, IncomeLevel incomeLevel,
                  Monthly monthly, RiskTag riskTag, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, incomeLevel, monthly, riskTag, tags);

        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.riskTag = riskTag;
        this.specialTags.add(riskTag);
        this.incomeLevel = incomeLevel;
        this.monthly = monthly;
        this.tags.addAll(tags);
    }

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, IncomeLevel incomeLevel, Monthly monthly,
                  RiskTag riskTag, Set<Tag> tags, MaximumSortedList<Appointment> appointments) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.riskTag = riskTag;
        this.specialTags.add(riskTag);
        this.monthly = monthly;
        this.tags.addAll(tags);
        this.appointments = appointments;
        this.incomeLevel = incomeLevel;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }
    public Monthly getMonthly() {
        return monthly;
    }

    public MaximumSortedList<Appointment> getAppointments() {
        return appointments;
    }

    public List<CalendarEvent> getCalendarEvents() {
        return appointments.stream().map(x -> new CalendarEvent(this.name, x)).collect(Collectors.toList());

    }


    public IncomeLevel getIncome() {
        return incomeLevel;
    }
    public RiskTag getRiskTag() {
        return riskTag;
    }


    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }
    public Set<Tag> getSpecialTags() {
        return Collections.unmodifiableSet(specialTags);
    }

    public void setAppointments(MaximumSortedList<Appointment> newAppointments) {
        appointments = newAppointments;
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
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

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone())
                && otherPerson.getEmail().equals(getEmail())
                && otherPerson.getAddress().equals(getAddress())
                && otherPerson.getTags().equals(getTags())
                && otherPerson.getAppointments().equals(getAppointments());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, appointments);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("; Phone: ")
                .append(getPhone())
                .append("; Email: ")
                .append(getEmail())
                .append("; Address: ")
                .append(getAddress());

        MaximumSortedList<Appointment> appointments = getAppointments();
        if (!Objects.isNull(appointments) && !appointments.isEmpty()) {
            builder.append("; Appointments: ");
            appointments.forEach(
                    appointment -> builder.append(appointment + " ")
            );
        }

        Set<Tag> tags = getTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(builder::append);
        }
        return builder.toString();
    }

}
