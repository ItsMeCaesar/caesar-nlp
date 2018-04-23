package com.hicaesar.nlp.vo;

import java.util.List;

/**
 *
 * @author samuelwaskow
 */
public final class EntitySynonymVO {

    private String value;
    private List<String> synonyms;

    /**
     * Constructor
     */
    public EntitySynonymVO() {
        super();
    }

    @Override
    public String toString() {
        return "{value=" + value + ", synonyms=" + synonyms + '}';
    }

    /* Gets And Sets */
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<String> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(List<String> synonyms) {
        this.synonyms = synonyms;
    }

}
