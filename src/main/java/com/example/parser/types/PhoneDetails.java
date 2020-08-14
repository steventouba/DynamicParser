package com.example.parser.types;


import com.example.parser.annotations.ParserType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

@ParserType(key = "phone-item")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PhoneDetails {
    @JsonProperty
    private String name;

    @JsonProperty
    private String displaySize;

    @JsonProperty
    private String memory;

    public PhoneDetails() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneDetails that = (PhoneDetails) o;
        return Objects.equal(name, that.name) &&
                Objects.equal(displaySize, that.displaySize) &&
                Objects.equal(memory, that.memory);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, displaySize, memory);
    }

    @Override
    public String toString() {
        return "PhoneDetails{" +
                "name='" + name + '\'' +
                ", displaySize='" + displaySize + '\'' +
                ", memory='" + memory + '\'' +
                '}';
    }
}
