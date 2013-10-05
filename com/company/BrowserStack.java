package com.company;

import com.company.model.Browser;
import com.company.model.Response;

import java.io.IOException;

public interface BrowserStack {
    public ErrorCode setAuthentication(String username, String accessKey);

    public void setApiVersion(ApiVersion apiVersion);

    //One sample for GET
    public Browser[] getAllBrowsers(boolean all) throws IOException;

    //Enums not used for OS type and version as they keep getting new values
    //One sample for POST
    public Response createBrowserInstance(BrowserParameters browserParameters) throws InvalidParameters, IOException;
}
