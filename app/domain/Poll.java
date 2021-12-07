package domain;

import domain.exceptions.NullOrEmptyTitleException;

import java.util.HashMap;

public class Poll {

    public static Poll create(String title) throws NullOrEmptyTitleException {
        if (title == null || title.isEmpty()) {
            throw new NullOrEmptyTitleException();
        }
        instance = new Poll();
        return instance;
    }

    private Poll() {
        idOptionMap = new HashMap<Integer, String>();
        optionIdMap = new HashMap<String, Integer>();
        setTitle(title);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
    public void addOption(String option) {
        int index = optionCount();
        idOptionMap.put(index, option);
        optionIdMap.put(option, index);
    }

    public int optionCount() {
        return idOptionMap.size();
    }

    public boolean containsOption(String option) {
        return idOptionMap.values().contains(option);
    }

    public int getOptionId(String option) {
        return optionIdMap.get(option);
    }
    private static Poll instance;
    private String title;
    private String description;
    private HashMap<Integer, String> idOptionMap;
    private HashMap<String, Integer> optionIdMap;

}
