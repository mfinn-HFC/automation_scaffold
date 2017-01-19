package util;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import java.util.Properties;

public class GmailClient {

    private Folder inbox;
    private Store store;
    private String gmailHost = "imap.gmail.com";

    public GmailClient(String email, String password) throws MessagingException
    {
        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");
        Session session = Session.getDefaultInstance(props, null);

        store = session.getStore("imaps");
        store.connect(gmailHost, email, password);

        inbox = store.getFolder("inbox");
        if (!inbox.isOpen()) inbox.open(Folder.READ_ONLY);
    }



}