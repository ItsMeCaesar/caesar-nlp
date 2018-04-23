package com.hicaesar.nlp.vo;

/**
 *
 * @author samuelwaskow
 */
public final class EntityTypeVO {

    private String id;
    private String name;

    /**
     * Constructor
     */
    public EntityTypeVO() {
        super();
    }

    @Override
    public String toString() {
        return "{" + "id=" + id + ", name=" + name + '}';
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

}
