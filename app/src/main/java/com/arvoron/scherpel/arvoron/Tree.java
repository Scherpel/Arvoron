package com.arvoron.scherpel.arvoron;

public class Tree {
    String name;
    String scientificName;
    String briefDescription;

    public Tree(String name, String scientificName, String briefDescription) {
        this.name = name;
        this.scientificName = scientificName;
        this.briefDescription = briefDescription;
    }

    public Tree() {
    }

    public String getName() {
        return name;
    }

    public String getScientificName() {
        return scientificName;
    }

    public String getBriefDescription() {
        return briefDescription;
    }
}
