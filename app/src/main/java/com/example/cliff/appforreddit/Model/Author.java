package com.example.cliff.appforreddit.Model;

// The root <feed> tag has multiple <entry> tags which have <author> tags (which contain multiple tags)

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Root(name = "entry", strict = false)
public class Author implements Serializable {

    @Element(name = "name")
    private String name;

    @Element(name = "uri")
    private String uri;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
