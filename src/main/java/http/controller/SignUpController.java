package http.controller;

import db.DataBase;
import http.model.*;
import model.User;

import static com.google.common.net.HttpHeaders.CONTENT_TYPE;

public class SignUpController implements Controller {
    private final static String ROOT_URI = "http://localhost:8080";

    @Override
    public HttpResponse service(HttpRequest httpRequest) {
        HttpParameters httpParameters = httpRequest.getParameters();

        User user = new User(httpParameters.getParameter("userId"),
                httpParameters.getParameter("password"),
                httpParameters.getParameter("name"),
                httpParameters.getParameter("email"));

        DataBase.addUser(user);

        return new HttpResponse.Builder()
                .sendRedirect(ROOT_URI + "/index.html")
                .protocols(HttpProtocols.HTTP1_1)
                .status(HttpStatus.FOUND)
                .addHeader(CONTENT_TYPE, ContentType.HTML.getType())
                .build();
    }
}