package com.gunjan.pagepulse.util;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

@Component
public class SeoScoreUtil {

    public int calculateSeoScore(Document document, int statusCode, boolean https) {

        int score = 0;

        // HTTPS
        if (https) {
            score += 20;
        }

        // HTTP Status
        if (statusCode == 200) {
            score += 20;
        }

        // Title
        if (!document.title().isBlank()) {
            score += 20;
        }

        // Meta Description
        String description = document
                .select("meta[name=description]")
                .attr("content");

        if (!description.isBlank()) {
            score += 20;
        }

        // H1
        if (!document.select("h1").isEmpty()) {
            score += 20;
        }

        return score;
    }
}