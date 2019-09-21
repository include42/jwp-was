package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;

public class Response {
    private static final Logger logger = LoggerFactory.getLogger(Response.class);

    private ResponseHeader header;
    private ResponseBody body;

    public Response(ResponseHeader header) {
        this.header = header;
    }

    public void setStatusCode(int statusCode) {
        header.setStatusCode(statusCode);
    }

    public void setLocation(String location) {
        header.setLocation(location);
    }

    public void setContentType(String type) {
        header.setType(type);
    }

    public void setUrl(String url) {
        header.setUrl(url);
    }

    public void setBody(byte[] body) {
        this.body = new ResponseBody(body);
    }

    public String getHeaderUrl() {
        return header.getUrl();
    }

    public void writeMessage(DataOutputStream dos) {
        if (header.isOk()) {
            writeHeader(dos, response200Header());
            writeBody(dos);
            return;
        }
        writeHeader(dos, response302Header());
    }

    private String response200Header() {
        return "HTTP/1.1 200 OK \r\n" +
                "Content-Type: " + header.getType() + ";charset=utf-8\r\n" +
                "Content-Length: " + body.getBody().length + "\r\n" +
                "\r\n";
    }

    private String response302Header() {
        return "HTTP/1.1 302 FOUND \r\n" +
                "Content-Type: " + header.getType() + ";charset=utf-8\r\n" +
                "Location: " + header.getLocation() + "\r\n" +
                "\r\n";
    }

    private void writeHeader(DataOutputStream dos, String message) {
        try {
            dos.writeBytes(message);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void writeBody(DataOutputStream dos) {
        try {
            dos.write(body.getBody(), 0, body.getBody().length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
