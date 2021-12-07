package domain;

import domain.exceptions.DateConflictException;
import domain.exceptions.NonExistentOptionException;
import domain.exceptions.NullOrEmptyTitleException;
import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class Campaign {

    public static Campaign create(String title) throws NullOrEmptyTitleException {
        if (title == null  || title.isEmpty()) {
            throw new NullOrEmptyTitleException();
        }
        instance = new Campaign(title);
        instance.setStartDate(new DateTime());
        return instance;
    }

    private Campaign(String title) {
        setTitle(title);
        setId(UUID.randomUUID());
        recipients = new HashSet<User>();
        recipientOptionMap = new HashMap<>();
    }

    public UUID getId() {
        return id;
    }

    private void setId(UUID id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
        updateStatus();
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) throws DateConflictException {
        if (endDate.isAfter(startDate)) {
            this.endDate = endDate;
            updateStatus();
        } else {
            throw new DateConflictException(getStartDate(), endDate);
        }
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    public Poll getPoll() {
        return poll;
    }

    public void updateStatus() {
        if (getStatus() == CampainStatus.ABORTED) {
            setStatus(CampainStatus.ABORTED);
        } else {
            DateTime now = new DateTime();
            if (getStartDate().isAfterNow()) {
                setStatus(CampainStatus.UPCOMING);
            } else if ((getStartDate().isBeforeNow()) && (getEndDate() == null || getEndDate().isAfter(now))) {
                setStatus(CampainStatus.RUNNING);
            } else {
                setStatus(CampainStatus.ENDED);
            }
        }
    }

    public CampainStatus getStatus() {
        return status;
    }

    public void abort() {
        setStatus(CampainStatus.ABORTED);
    }

    public void setStatus(CampainStatus status) {
        this.status = status;
    }

    public HashSet<User> getRecipients() {
        return recipients;
    }

    public void setRecipients(HashSet<User> recipients) {
        this.recipients = recipients;
    }

    public void setRecipientSelection(String email, String option) throws NonExistentOptionException {
        if (poll.containsOption(option)) {
            recipientOptionMap.put(email, option);
        } else {
            throw new NonExistentOptionException();
        }
    }

    public String getRecipientSelection(String email) {
        return recipientOptionMap.get(email) ;
    }

    public String getOptionRelativeUrl(User recipient, String option0) {
        return "/" + getId() + "?u=" + recipient.getId() + "&o=" + getPoll().getOptionId(option0);
    }

    private static Campaign instance;
    private UUID id;
    private String title;
    private String description;
    private DateTime startDate;
    private DateTime endDate;
    private CampainStatus status;
    private Poll poll;
    private HashSet<User>recipients;
    private HashMap<String, String> recipientOptionMap;



}
