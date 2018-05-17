package com.hicaesar.nlp.vo;

import java.util.Date;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 *
 * @author samuelwaskow
 */
public final class ReportVO {

    private String id;
    private String text;
    private Date created;
    private Date viewed;

    /**
     * Constructor
     */
    public ReportVO() {
        super();
    }

    /**
     * Constructor
     *
     * @param text
     */
    public ReportVO(final String text) {
        this.text = text;
    }

    /**
     * Compute hash code by using Apache Commons Lang HashCodeBuilder.
     *
     * @return
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.id)
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
        if (obj != null && obj instanceof ReportVO) {
            final ReportVO other = (ReportVO) obj;
            return new EqualsBuilder()
                    .append(this.id, other.id)
                    .isEquals();
        } else {
            return false;
        }
    }

    /* Gets And Sets */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getViewed() {
        return viewed;
    }

    public void setViewed(Date viewed) {
        this.viewed = viewed;
    }

}
