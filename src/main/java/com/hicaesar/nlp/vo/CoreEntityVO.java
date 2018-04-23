package com.hicaesar.nlp.vo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 *
 * @author samuelwaskow
 */
public final class CoreEntityVO {

    private String id;
    private String locale;
    private String value;
    private String type;

    /**
     * Default Constructor
     */
    public CoreEntityVO() {
        super();
    }

    /**
     * Compute hash code by using Apache Commons Lang HashCodeBuilder.
     *
     * @return
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.locale)
                .append(this.value)
                .append(this.type)
                .hashCode();
    }

    /**
     * Compute equals by using Apache Commons Lang EqualsBuilder.
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof CoreEntityVO) {
            final CoreEntityVO other = (CoreEntityVO) obj;
            return new EqualsBuilder()
                    .append(this.locale, other.locale)
                    .append(this.value, other.value)
                    .append(this.type, other.type)
                    .isEquals();
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "{id=" + id + ", locale=" + locale + ", value=" + value + ", type=" + type + '}';
    }

    /* Gets And Sets */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
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
