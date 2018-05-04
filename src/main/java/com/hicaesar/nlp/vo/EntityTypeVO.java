package com.hicaesar.nlp.vo;

/**
 *
 * @author samuelwaskow
 */
public final class EntityTypeVO {

    private String id;
    private String name;
    private String color;

    /**
     * Constructor
     */
    public EntityTypeVO() {
        super();
    }

    @Override
    public String toString() {
        return "{id=" + id + ", name=" + name + ", color=" + color + '}';
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
