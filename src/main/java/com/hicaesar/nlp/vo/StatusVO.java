package com.hicaesar.nlp.vo;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author joao polo
 */
@XmlRootElement
public final class StatusVO {

    /**
     * status flag
     */
    private boolean ok = true;

    /**
     * optional message
     */
    private String msg;

    /**
     * optional action
     */
    private String action;

    /**
     * Default Constructor
     */
    public StatusVO() {
        super();
    }

    /**
     * Constructor
     *
     * @param ok
     */
    public StatusVO(boolean ok) {
        super();
        this.ok = ok;
    }

    /**
     * Constructor
     *
     * @param ok
     * @param msg
     * @param action
     */
    public StatusVO(boolean ok, String msg, String action) {
        super();
        this.ok = ok;
        this.msg = msg;
        this.action = action;
    }

    /**
     * OK
     *
     * @return
     */
    public static StatusVO ok() {
        return new StatusVO(true, null, null);
    }

    /**
     * OK
     *
     * @param msg
     * @return
     */
    public static StatusVO ok(final String msg) {
        return new StatusVO(true, msg, null);
    }

    /**
     * OK
     *
     * @param msg
     * @param action
     * @return
     */
    public static StatusVO ok(final String msg, final String action) {
        return new StatusVO(true, msg, action);
    }

    /**
     * OK
     *
     * @param id
     * @return
     */
    public static StatusVO ok(final long id) {
        return new StatusVO(true, null, String.valueOf(id));
    }

    /**
     * ERROR
     *
     * @param msg
     * @return
     */
    public static StatusVO error(final String msg) {
        return new StatusVO(false, msg, null);
    }

    /**
     * ERROR
     *
     * @param msg
     * @param action
     * @return
     */
    public static StatusVO error(final String msg, final String action) {
        return new StatusVO(false, msg, action);
    }

    @Override
    public String toString() {
        return "{ok=" + ok + ", msg=" + msg + ", action=" + action + '}';
    }

    /* Gets And Sets */
    public boolean isOk() {
        return ok;
    }

    public String getMsg() {
        return msg;
    }

    public String getAction() {
        return action;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setAction(String action) {
        this.action = action;
    }

}
