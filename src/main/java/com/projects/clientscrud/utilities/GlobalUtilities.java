package com.projects.clientscrud.utilities;

import org.json.JSONObject;

public final class GlobalUtilities {

    public static JSONObject createGeneralResponse (int status, String message, JSONObject response) {
        JSONObject body = new JSONObject();
        body.put("status", status);
        body.put("message", message);
        body.put("response", response);
        return body;
    }
}
