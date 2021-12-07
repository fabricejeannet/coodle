package domain;

import domain.exceptions.NullOrEmptyTitleException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PollTest {

    @Before
    public void Before() throws NullOrEmptyTitleException {
        poll = Poll.create(title);
    }

    @Test
    public void canCreatePoll(){
        assertNotNull(poll);
    }

    @Test(expected = NullOrEmptyTitleException.class)
    public void TitleCannotBeNull() throws NullOrEmptyTitleException {
        Poll p = Poll.create(null);
    }

    @Test(expected = NullOrEmptyTitleException.class)
    public void TitleCannotBeEmpty() throws NullOrEmptyTitleException {
        Poll p = Poll.create("");
    }

    @Test
    public void canAddDescriptionToMyPoll() {
        poll.setDescription(description);
        assertEquals(description, poll.getDescription());
    }

    @Test
    public void canAddAnOptionToMyPoll(){
        String option = "Option";
        poll.addOption(option);
        assertEquals(1, poll.optionCount());
    }

    @Test
    public void containsOptionReturnFalseWhenTheOptionDoesNotExist(){
        assertFalse(poll.containsOption("Test"));
    }

    @Test
    public void canGetOptionId() {
        String option = "Option";
        poll.addOption(option);
        assertEquals(0, poll.getOptionId(option));

    }

    private Poll poll;
    private String title = "My Poll Title";
    private String description = "My Poll Description";
}
