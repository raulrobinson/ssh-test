package co.com.telefonica.ws.service;

import com.jcraft.jsch.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class SshService {

    public String executeRemoteCommand(String host, int port, String username, String password, String command) {
        StringBuilder result = new StringBuilder();
        JSch jsch = new JSch();
        Session session = null;
        ChannelExec channel = null;

        try {
            session = jsch.getSession(username, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no"); // Opción para evitar el intercambio de claves

            session.connect();

            channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(command);
            channel.connect();

            // Lee la salida del comando remoto
            try (InputStream in = channel.getInputStream()) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    result.append(new String(buffer, 0, bytesRead));
                }
            }
        } catch (JSchException | IOException e) {
            e.printStackTrace();
        } finally {
            if (channel != null) {
                channel.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        }

        return result.toString();
    }
}

