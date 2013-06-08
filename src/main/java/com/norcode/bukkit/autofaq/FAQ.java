package com.norcode.bukkit.autofaq;

import javax.persistence.*;
import java.util.regex.Pattern;

@Entity
@Table(name="autofaq_faq")
public class FAQ {
    @Transient
    Pattern compiledPattern = null;

    @Id
    String name;

    @Column
    String pattern;

    @Column
    String response;

    public FAQ() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Transient
    public void compile() {
        this.compiledPattern = Pattern.compile(".*" + this.pattern + ".*", Pattern.CASE_INSENSITIVE);
    }
}
