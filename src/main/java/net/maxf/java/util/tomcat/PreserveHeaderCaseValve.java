package net.maxf.java.util.tomcat;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.RequestFacade;
import org.apache.catalina.valves.ValveBase;
import org.apache.coyote.Request as CoyoteRequest;
import org.apache.tomcat.util.http.MimeHeaders;

import jakarta.servlet.ServletException;
import java.io.IOException;
import java.util.*;

public class PreserveHeaderCaseValve extends ValveBase {

    @Override
    public void invoke(Request request, org.apache.catalina.connector.Response response)
            throws IOException, ServletException {

        CoyoteRequest coyoteRequest = request.getCoyoteRequest();
        MimeHeaders mimeHeaders = coyoteRequest.getMimeHeaders();

        // Preserve original case
        Map<String, List<String>> originalHeaders = new LinkedHashMap<>();

        for (int i = 0; i < mimeHeaders.size(); i++) {
            String name = mimeHeaders.getName(i).toString();
            String value = mimeHeaders.getValue(i).toString();

            originalHeaders.computeIfAbsent(name, k -> new ArrayList<>()).add(value);
        }

        // Expose to downstream code via request attribute
        request.setAttribute("originalHeaders", originalHeaders);

        // Continue pipeline
        getNext().invoke(request, response);
    }
}

