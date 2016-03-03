package demos;


import org.apache.camel.Body;
import org.apache.camel.Header;

public class Logger {

    public void log(@Header("CamelFileName") String fileName, @Body Object body) {
        System.out.println("[my service] fileName: " + fileName + ", body: " + body);
    }

}
