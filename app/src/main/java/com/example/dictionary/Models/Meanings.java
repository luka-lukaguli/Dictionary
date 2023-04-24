package com.example.dictionary.Models;

import java.util.List;

public class Meanings {
    String partOfSpeech = "";
    List<Defenitions> definitions = null;

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public List<Defenitions> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<Defenitions> definitions) {
        this.definitions = definitions;
    }
}
