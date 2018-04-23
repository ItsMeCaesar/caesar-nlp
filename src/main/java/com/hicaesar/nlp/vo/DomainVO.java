package com.hicaesar.nlp.vo;

import java.util.List;

/**
 *
 * @author samuelwaskow
 */
public class DomainVO {

    protected String id;
    protected String name;
    protected String locale;
    protected List<IntentVO> intents;
    protected List<EntitySynonymVO> entitySynonyms;

    /**
     * Default Constructor
     */
    public DomainVO() {
        super();
    }

    @Override
    public String toString() {
        return "{id=" + id + ", name=" + name + ", locale=" + locale + ", intents=" + intents + ", entitySynonyms=" + entitySynonyms + '}';
    }

    /* Gets And Sets */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<IntentVO> getIntents() {
        return intents;
    }

    public void setIntents(List<IntentVO> intents) {
        this.intents = intents;
    }

    public List<EntitySynonymVO> getEntitySynonyms() {
        return entitySynonyms;
    }

    public void setEntitySynonyms(List<EntitySynonymVO> entitySynonyms) {
        this.entitySynonyms = entitySynonyms;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

}
