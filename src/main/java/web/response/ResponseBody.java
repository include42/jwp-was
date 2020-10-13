package web.response;

import java.io.DataOutputStream;
import java.io.IOException;

public class ResponseBody {
    private final byte[] body;

    public ResponseBody(byte[] body) {
        this.body = body;
    }

    public void write(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.write(body, 0, body.length);
    }
}
