package com.hicaesar.nlp.vo;

/**
 *
 * @author samuelwaskow
 */
public final class IntentEntityVO {

    private int start;
    private int end;
    private String value;
    private String type;

    /**
     * Default Constructor
     */
    public IntentEntityVO() {
        super();
    }

    @Override
    public String toString() {
        return "{start=" + start + ", end=" + end + ", value=" + value + ", type=" + type + '}';
    }

    /* Gets And Sets */
    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
