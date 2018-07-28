import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.TransportConfigCallback;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.*;
import org.eclipse.jgit.util.FS;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Main {
    public static void main(String[] args) throws GitAPIException {
        Git git;
        try {
            git = Git.open(new File("D:/project/github-to-yobi-migration-service/sshtest/2018_ELE3021_2013008264"));
        } catch (IOException e) {
            System.out.println("IOException occured");
            git = null;
        }
        final SshSessionFactory sshSessionFactory = new JschConfigSessionFactory() {
            @Override
            protected void configure(OpenSshConfig.Host host, Session session) {
                session.setConfig("StrictHostKeyChecking", "no");
            }
        };

        TransportConfigCallback transportConfigCallback = new TransportConfigCallback() {
            public void configure(Transport transport) {
               SshTransport sshTransport = (SshTransport) transport;
               sshTransport.setSshSessionFactory(sshSessionFactory);
            }
        };
        git.branchCreate()
                .setStartPoint("453e8715b40641a1ae0e4892a645afb617f5f2a2")
                .setName("TemporalBranch")
                .call();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf1 = new SimpleDateFormat("hh:mm:ss");
        System.out.println(sdf1.format(cal.getTime()));
        git.push()
                .setPushAll()
                .setTransportConfigCallback(transportConfigCallback)
                .call();
        cal = Calendar.getInstance();
        System.out.println(sdf1.format(cal.getTime()));
        git.branchDelete()
                .setBranchNames("TemporalBranch")
                .call();

        git.push()
                .setTransportConfigCallback(transportConfigCallback)
                .setRefSpecs(new RefSpec(":refs/heads/TemporalBranch"))
                .call();
        cal = Calendar.getInstance();
        System.out.println(sdf1.format(cal.getTime()));

        git.branchCreate()
                .setStartPoint("3d7015597f875ce0e220bfb2685912823c95bb3f")
                .setName("TemporalBranch")
                .call();

        cal = Calendar.getInstance();
        System.out.println(sdf1.format(cal.getTime()));
        git.push()
                .setPushAll()
                .setTransportConfigCallback(transportConfigCallback)
                .call();
        cal = Calendar.getInstance();
        System.out.println(sdf1.format(cal.getTime()));
        git.branchDelete()
                .setBranchNames("TemporalBranch")
                .call();

        git.push()
                .setTransportConfigCallback(transportConfigCallback)
                .setRefSpecs(new RefSpec(":refs/heads/TemporalBranch"))
                .call();
        cal = Calendar.getInstance();
        System.out.println(sdf1.format(cal.getTime()));

    }
}
