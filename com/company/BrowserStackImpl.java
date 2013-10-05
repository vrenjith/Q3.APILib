package com.company;

import com.company.model.Browser;
import com.company.model.Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.codec.binary.Base64;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.company.ErrorCode.INVALID_ARGUMENTS;
import static com.company.ErrorCode.SUCCESS;

public class BrowserStackImpl implements BrowserStack {
    private static final String BASE_URL = "http://api.browserstack.com";
    private static final String GET = "GET";
    private static final String POST = "POST";

    private String authenticationData;
    private ApiVersion apiVersion;

    @Override
    public ErrorCode setAuthentication(String username, String accessKey) {
        if(null == username || null == accessKey ||
                username.isEmpty() || accessKey.isEmpty()){
            return INVALID_ARGUMENTS;
        }
        else{
            authenticationData = String.format("%s:%s",username,accessKey);
        }
        return SUCCESS;
    }

    @Override
    public void setApiVersion(ApiVersion apiVersion) {
        this.apiVersion = apiVersion;
    }

    @Override
    public Browser[] getAllBrowsers(boolean all) throws IOException {
        String url = buildBaseUrl() + "/browsers";

        //This is a conscious decision to add the flat parameter as the non-flat
        //data format requires hardcoded os types, which might not be be best way to define an API
        url = addParam(url, "flat","true");

        if(all){
            url = addParam(url, "all","true");
        }

        Gson gson = new GsonBuilder().create();
        return gson.fromJson(getHttpData(url, GET), Browser[].class);
    }

    private String getHttpData(String url, String method) throws IOException {
        URL apiurl = new URL(url);
        HttpURLConnection con = (HttpURLConnection) apiurl.openConnection();
        con.setRequestMethod(method);

        con.setRequestProperty("Authorization", "Basic " + new String(Base64.encodeBase64(authenticationData.getBytes())));
        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder data = new StringBuilder("");
        String line = "";
        while((line = reader.readLine()) != null){
            data.append(line);
        }
        return data.toString();
    }

    @Override
    public Response createBrowserInstance(BrowserParameters browserParameters) throws InvalidParameters, IOException {
        if(checkMandatoryParams(browserParameters)){
            throw new InvalidParameters("Mandatory parameters are missing");
        }

        String url = buildBaseUrl() + "/worker?" + browserParameters.getParamsAsQueyString();
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(getHttpData(url, POST), Browser[].class);
    }

    private boolean checkMandatoryParams(BrowserParameters browserParameters) {
        //TODO check the parameters inside the param object
        return true;
    }

    private String buildBaseUrl(){
        return BASE_URL + mapApiVersion();
    }

    private String mapApiVersion() {
        if(null == apiVersion){
            return "3";
        }

        if(apiVersion == ApiVersion.V1){
            return "1";
        }
        else if(apiVersion == ApiVersion.V2){
            return "2";
        }
        else if(apiVersion == ApiVersion.V3){
            return "3";
        }
        else{
            //latest
            return "3";
        }
    }
    private String addParam(String url, String param, String value){
        if(url.contains("?")){
            return String.format("%s&%s=%s",url, param, value);
        }
        else{
            return String.format("%s?%s=%s",url, param, value);
        }
    }
}
