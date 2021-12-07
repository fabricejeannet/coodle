package domain;

import domain.exceptions.DateConflictException;
import domain.exceptions.EmailCantBeNullOrEmpty;
import domain.exceptions.NonExistentOptionException;
import domain.exceptions.NullOrEmptyTitleException;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.*;

public class CampaignTest {

    @Before
    public void before() throws NullOrEmptyTitleException, EmailCantBeNullOrEmpty {
        campaign = Campaign.create(title);

        HashSet<User> recipients = new HashSet<User>();
        user0 = User.createUser("email0@titi.com", "toto", "titi");
        user1 = User.createUser("email1@titi.com", "toto", "titi");
        recipients.add(user0);
        recipients.add(user1);
        campaign.setRecipients(recipients);

        Poll poll = Poll.create("My poll");
        option0 = "Option #0";
        option1 = "Option #1";
        poll.addOption(option0);
        poll.addOption(option1);
        campaign.setPoll(poll);
    }

    @Test
    public void canCreateCampaign() {
        assertNotNull(campaign);
    }


    @Test
    public void campaignGetsAnIDWhenCreated(){
        assertNotNull(campaign.getId());
    }

    @Test(expected = NullOrEmptyTitleException.class)
    public void TitleCannotBeNull() throws NullOrEmptyTitleException {
        Campaign c = Campaign.create(null);
    }

    @Test(expected = NullOrEmptyTitleException.class)
    public void TitleCannotBeEmpty() throws NullOrEmptyTitleException {
        Campaign c = Campaign.create("");
    }

    @Test
    public void canSetCampaignDescription() {
        campaign.setDescription(description);
        assertEquals(description, campaign.getDescription());
    }

    @Test
    public void campaignGetsAStartDateWhenCreated() {
        assertNotNull(campaign.getStartDate());
    }

    @Test
    public void canSetAStartDate(){
        DateTime startDate = new DateTime();
        campaign.setStartDate(startDate);
        assertEquals(startDate, campaign.getStartDate());
    }

    @Test
    public void canSetAnEndDate() throws DateConflictException {
        DateTime endDate = campaign.getStartDate().plusMillis(1);
        campaign.setEndDate(endDate);
        assertEquals(endDate, campaign.getEndDate());
    }

    @Test(expected = DateConflictException.class)
    public void endDateCannotBeEarlierThanStartDate() throws DateConflictException {
        DateTime startDate = new DateTime();
        DateTime endDate = startDate.minusMillis(1);
        campaign.setStartDate(startDate);
        campaign.setEndDate(endDate);
    }

    @Test
    public void getStatusReturnsUpcomingIfCampaignIsNotStartedYet() throws DateConflictException {
        DateTime startDate = new DateTime().plusDays(1);
        DateTime endDate = startDate.plusDays(8);
        campaign.setStartDate(startDate);
        campaign.setEndDate(endDate);
        assertEquals(CampainStatus.UPCOMING, campaign.getStatus());
    }

    @Test
    public void getStatusReturnsRunningIfCampaignIsBetweenStartAndEndDate() throws DateConflictException {
        DateTime startDate = new DateTime().minusDays(1);
        DateTime endDate = startDate.plusDays(2);
        campaign.setStartDate(startDate);
        campaign.setEndDate(endDate);
        assertEquals(CampainStatus.RUNNING, campaign.getStatus());
    }

    @Test
    public void getStatusReturnsENDEDIfCampaignEndDateIsBeforeNow() throws DateConflictException {
        DateTime startDate = new DateTime().minusDays(2);
        DateTime endDate = startDate.plusDays(1);
        campaign.setStartDate(startDate);
        campaign.setEndDate(endDate);
        assertEquals(CampainStatus.ENDED, campaign.getStatus());
    }

    @Test
    public void settingAFurtherStartDateUpdateStatusToUPCOMING() {
        DateTime startDate = new DateTime().plusDays(1);
        campaign.setStartDate(startDate);
        assertEquals(CampainStatus.UPCOMING, campaign.getStatus());
    }

    @Test
    public void settingStartDateTheDayBeforeNowUpdateStatusToRUNNING() {
        DateTime startDate = new DateTime().minusDays(1);
        campaign.setStartDate(startDate);
        assertEquals(CampainStatus.RUNNING, campaign.getStatus());
    }

    @Test
    public void settingEndDateTheDayBeforeNowUpdateStatusToENDED() throws DateConflictException {
        DateTime startDate = new DateTime().minusDays(2);
        DateTime endDate = new DateTime().minusDays(1);
        campaign.setStartDate(startDate);
        campaign.setEndDate(endDate);
        assertEquals(CampainStatus.ENDED, campaign.getStatus());
    }

    @Test
    public void canSetPoll() throws NullOrEmptyTitleException {
        Poll poll = Poll.create("My Test Poll");
        campaign.setPoll(poll);
        assertNotNull(campaign.getPoll());
        assertEquals(poll, campaign.getPoll());
    }

    @Test
    public void canAbortCampaign() {
        campaign.abort();
        assertEquals(CampainStatus.ABORTED, campaign.getStatus());
    }

    @Test
    public void canSetRecipientList() throws EmailCantBeNullOrEmpty {
        HashSet<User> recipients = new HashSet<User>();
        for (int n = 0; n < 5; n++) {
            User user = User.createUser("email"+n+"@titi.com", "toto", "titi");
            recipients.add(user);
        }
        campaign.setRecipients(recipients);
    }

    @Test
    public void userCanSelectAnOption() throws NonExistentOptionException {
        campaign.setRecipientSelection(user0.getEmail(), option1);
        assertEquals(option1, campaign.getRecipientSelection(user0.getEmail()));
    }

    @Test(expected = NonExistentOptionException.class)
    public void userCannotSelectAnOptionThatDoesNotExist() throws NonExistentOptionException {
        campaign.setRecipientSelection(user0.getEmail(), "Option #2");
    }

    @Test
    public void canGenerateOptionsRelativeURLForAGivenUser() {
        String relativeURL = campaign.getOptionRelativeUrl(user0, option0);
        assertEquals("/" + campaign.getId() + "?u=" + user0.getId() + "&o=" + campaign.getPoll().getOptionId(option0), relativeURL);
    }

    private Campaign campaign = null;
    private String title = "Campaign Title";
    private String description = "My campaign Description";
    private User user1, user0;
    private String option0, option1;
}
