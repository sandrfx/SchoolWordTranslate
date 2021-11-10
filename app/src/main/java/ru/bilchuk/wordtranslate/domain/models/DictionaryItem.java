package ru.bilchuk.wordtranslate.domain.models;

public class DictionaryItem {

    private String keyword;
    private String translation;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    @Override
    public String toString() {
        return "DictionaryItem{" +
                "keyword='" + keyword + '\'' +
                ", translation='" + translation + '\'' +
                '}';
    }
}
