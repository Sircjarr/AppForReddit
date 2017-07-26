package com.example.cliff.appforreddit;

import java.util.ArrayList;
import java.util.List;

// The String returned from the content tag is complex because of CData and tags within
// This class takes the tags within content, and extracts their information

public class ExtractXML {
    private static final String TAG = "ExtractXML";

    private String xml;
    private String tag;

    public ExtractXML(String xml, String tag) {
        this.xml = xml;
        this.tag = tag;
    }

    public List<String> start() {
        List<String> result = new ArrayList<>();

        String[] splitXML = xml.split(tag + "\"");
        int count = splitXML.length;

        // Loop through splitXML[] and find the next quotation mark
        for (int i = 1; i < count; i++) {
            String temp = splitXML[i];
            int index = temp.indexOf("\"");
            temp = temp.substring(0, index);
            result.add(temp);
        }

        return result;
    }
}
