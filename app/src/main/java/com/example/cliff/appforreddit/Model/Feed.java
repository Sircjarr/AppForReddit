package com.example.cliff.appforreddit.Model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

// The Model package models the XML data pulled from the Reddit feed
// This class will model the <feed> tag, which is the root tag

// The RSS feed can be seen in a more readable format using https://codebeautify.org/xmlviewer/
// The XML can be converted to HTML and beautified using https://codebeautify.org/htmlviewer/

// By default, strict is set to true; which means that the data will not be retrieved
    // if every tag is not accounted for
@Root(name = "feed", strict = false)
public class Feed implements Serializable {

    // Define the element in Retrofit
    @Element(name = "icon")
    private String icon;

    @Element(name = "id")
    private String id;

    @Element(name = "logo")
    private String logo;

    @Element(name = "title")
    private String title;

    @Element(name = "updated")
    private String updated;

    @Element(name = "subtitle")
    private String subtitle;

    // Declare the list of entries that need to be retrieved
    @ElementList(inline = true, name = "entry")
    private List<Entry> entryList;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public List<Entry> getEntryList() {
        return entryList;
    }

    public void setEntryList(List<Entry> entryList) {
        this.entryList = entryList;
    }
}
