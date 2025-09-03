package com.gestion.location.web.dto;

public class DummyDTO {
    private Long id;
    private String name;
    private String value;

    public DummyDTO() {
    }

    public DummyDTO(Long id, String name, String value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "DummyDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
