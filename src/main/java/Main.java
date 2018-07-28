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
            git = Git.open(new File("D:/project/github-to-yobi-migration-service/sshtest/gitPractice"));
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
                .setName("TemporalBranchesss")
                .call();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf1 = new SimpleDateFormat("hh:mm:ss");
        System.out.println(sdf1.format(cal.getTime()));
        git.push()
                .setPushAll()
                .setTransportConfigCallback(transportConfigCallback)
                .call();
        Calendar cal1 = Calendar.getInstance();
        System.out.println(sdf1.format(cal1.getTime()));
        git.branchDelete()
                .setBranchNames("TemporalBranchesss")
                .call();

        git.push()
                .setTransportConfigCallback(transportConfigCallback)
                .setRefSpecs(new RefSpec(":TemporalBranchesss"))
                .call();
        Calendar cal2 = Calendar.getInstance();
        System.out.println(sdf1.format(cal2.getTime()));

    }
}
