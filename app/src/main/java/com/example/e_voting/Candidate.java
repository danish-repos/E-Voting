package com.example.e_voting;

public class Candidate {

    String name, location, type, partyName;
    int totalVotes;

    public Candidate() {
    }

    public Candidate(String name, String location, String type, String partyName, int totalVotes) {
        this.name = name;
        this.location = location;
        this.type = type;
        this.partyName = partyName;
        this.totalVotes = totalVotes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public int getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(int totalVotes) {
        this.totalVotes = totalVotes;
    }
}
