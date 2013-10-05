package com.company;

public class BrowserParameters {
    String osType;
    String version;
    String browser;
    String device;
    String browserVersion;

    int timeout;
    String url;
    String name;
    String build;
    String project;

    public BrowserParameters(String osType, String version,
                             String browser, String device, String browserVersion, int timeout, String url, String name, String build, String project) {
        this.osType = osType;
        this.version = version;
        this.browser = browser;
        this.device = device;
        this.browserVersion = browserVersion;
        this.timeout = timeout;
        this.url = url;
        this.name = name;
        this.build = build;
        this.project = project;
    }

    public BrowserParameters(String osType, String version, String browser, String device, String browserVersion) {
        this.osType = osType;
        this.version = version;
        this.browser = browser;
        this.device = device;
        this.browserVersion = browserVersion;
    }
    public String getParamsAsQueyString(){
        //TODO: Convert all the params to query string format
        return "";
    }
}
