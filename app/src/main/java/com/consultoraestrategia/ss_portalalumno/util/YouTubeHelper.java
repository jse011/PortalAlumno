package com.consultoraestrategia.ss_portalalumno.util;

import android.text.TextUtils;
import android.util.Log;

import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YouTubeHelper {

    final static String youTubeUrlRegEx = "^(https?)?(://)?(www.)?(m.)?((youtube.com)|(youtu.be))/";
    //final static String[] videoIdRegex = { "\\?vi?=([^&]*)","watch\\?.*v=([^&]*)", "(?:embed|vi?)/([^/?]*)", "^([A-Za-z0-9\\-]*)"};
    final static String[] videoIdRegex = {"\\?vi?=([^&]_)", "watch\\?._v=([^&]_)", "(?:embed|vi?)/([^/?]_)", "^([A-Za-z0-9\\-\\_]*)"};

    public static String extractVideoIdFromUrl(String url) {
        String youTubeLinkWithoutProtocolAndDomain = youTubeLinkWithoutProtocolAndDomain(url);
        if(!TextUtils.isEmpty(youTubeLinkWithoutProtocolAndDomain)){
            for(String regex : videoIdRegex) {
                Pattern compiledPattern = Pattern.compile(regex);
                Matcher matcher = compiledPattern.matcher(youTubeLinkWithoutProtocolAndDomain);
                Log.d(YouTubeHelper.class.getSimpleName(), "extractVideoIdFromUrl " + matcher.toString());
                if(matcher.find()){
                    return matcher.group(1);
                }
            }
        }
        return null;
    }

    private static String youTubeLinkWithoutProtocolAndDomain(String url) {
        if(TextUtils.isEmpty(url))return null;
        Pattern compiledPattern = Pattern.compile(youTubeUrlRegEx);
        Matcher matcher = compiledPattern.matcher(url);

        if(matcher.find()){
            return url.replace(matcher.group(), "");
        }
        return null;
    }

    public static class YouTubeHelperTest {

        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"youtube.com/v/vidid"},
                    {"youtube.com/vi/vidid"},
                    {"youtube.com/?v=vidid"},
                    {"youtube.com/?vi=vidid"},
                    {"youtube.com/watch?v=vidid"},
                    {"youtube.com/watch?vi=vidid"},
                    {"youtu.be/vidid"},
                    {"youtube.com/embed/vidid"},
                    {"youtube.com/embed/vidid"},
                    {"www.youtube.com/v/vidid"},
                    {"http://www.youtube.com/v/vidid"},
                    {"https://www.youtube.com/v/vidid"},
                    {"youtube.com/watch?v=vidid&wtv=wtv"},
                    {"http://www.youtube.com/watch?dev=inprogress&v=vidid&feature=related"},
                    {"https://m.youtube.com/watch?v=vidid"}
            });
        }

        private String url;

        public YouTubeHelperTest(String url) {
            this.url = url;
        }

        private YouTubeHelper youTubeHelper = new YouTubeHelper();


        public void extractingVideoIdFromUrlShouldReturnVideoId() {
            Log.d(getClass().getSimpleName(), "Unable to extract correct video id from url " + url + " vidid "+ youTubeHelper.extractVideoIdFromUrl(url));
        }

    }
}
