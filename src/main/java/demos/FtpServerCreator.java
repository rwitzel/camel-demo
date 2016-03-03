package demos;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.filesystem.nativefs.NativeFileSystemFactory;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.ClearTextPasswordEncryptor;
import org.apache.ftpserver.usermanager.impl.PropertiesUserManager;

import java.net.URL;

public class FtpServerCreator {

    public static FtpServer embeddedFtpServer(int port, URL userProperties) throws Exception {
        FtpServerFactory serverFactory = new FtpServerFactory();

        UserManager uman = new PropertiesUserManager(new ClearTextPasswordEncryptor(), userProperties, "admin");
        serverFactory.setUserManager(uman);

        NativeFileSystemFactory fsf = new NativeFileSystemFactory();
        fsf.setCreateHome(true);
        serverFactory.setFileSystem(fsf);

        ListenerFactory factory = new ListenerFactory();
        factory.setPort(port);
        serverFactory.addListener("default", factory.createListener());

        return serverFactory.createServer();
    }
}