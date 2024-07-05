package com.rds.connect.common.util.db;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import jakarta.annotation.PreDestroy;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Profile("dev")
@Component
@ConfigurationProperties(prefix = "ssh")
@Setter
public class SSHTunnelingInitializer {
    @Value("${ssh.ssh_host}") private String sshHost;
    @Value("${ssh.ssh_user}") private String sshUser;
    @Value("${ssh.ssh_port}") private int sshPort;
    @Value("${ssh.ssh_key}") private String sshKey;
    @Value("${ssh.database_host}") private String databaseHost;
    @Value("${ssh.database_port}") private int databasePort;

    private Session session;

    @PreDestroy
    public void closeSSH() {
        if (session != null && session.isConnected())
            session.disconnect();
    }

    public Integer buildSSHConnection() {
        Integer forwardedPort = null;

        try {
            JSch jSch = new JSch();
            jSch.addIdentity(sshKey);
            session = jSch.getSession(sshUser, sshHost, sshPort);

            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();

            forwardedPort = session.setPortForwardingL(0, databaseHost, databasePort);
        } catch (JSchException e){
            this.closeSSH();
            e.printStackTrace();
        }

        return forwardedPort;
    }
}
