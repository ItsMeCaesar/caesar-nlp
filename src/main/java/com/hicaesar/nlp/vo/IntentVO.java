package com.hicaesar.nlp.vo;

import java.util.List;

/**
 *
 * @author samuelwaskow
 */
public final class IntentVO {

    private String text;
    private String intent;
    private List<IntentEntityVO> entities;

    /**
     * Constructor
     */
    public IntentVO() {
        super();
    }

    @Override
    public String toString() {
        return "{text=" + text + ", intent=" + intent + ", entities=" + entities + '}';
    }

    /* Gets And Sets */
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public List<IntentEntityVO> getEntities() {
        return entities;
    }

    public void setEntities(List<IntentEntityVO> entities) {
        this.entities = entities;
    }

}
