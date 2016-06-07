package org.openzal.zal.index;

import com.zimbra.cs.mailbox.ZimbraSimulator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openzal.zal.ParsedMessage;

import javax.activation.DataSource;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;


public class IndexerManagerTest {
    private IndexerManager mIndexerManager;
    private MimeMessage mMimeMessage;
    private ParsedMessage mParsedMessage;
    private Indexer mIndexer;
    private ZimbraSimulator mZimbraSimulator;

    @Before
    public void setup() throws Exception {
        mZimbraSimulator = new ZimbraSimulator();
        mIndexerManager = new IndexerManager();
        mIndexerManager.attachToZimbra();
        mMimeMessage = createMimeMessageWithAttachment();
        mIndexer = mock(Indexer.class);
        mParsedMessage = new ParsedMessage(mMimeMessage, true);
    }

    private MimeMessage createMimeMessageWithAttachment() throws Exception {
        MimeMessage message = new MimeMessage((Session) null);

        MimeBodyPart text = new MimeBodyPart();
        text.setText("text of the email");

        MimeBodyPart attachment = new MimeBodyPart();
        attachment.setContent(
                new byte[]{'C', 'I', 'A', 'O'},
                "application/pdf"
        );

        MimeMultipart mimeMultipart = new MimeMultipart("mixed");
        mimeMultipart.addBodyPart(text);
        mimeMultipart.addBodyPart(attachment);
        message.setContent(mimeMultipart);
        message.saveChanges();

        return message;
    }

    @After
    public void cleanup() throws Exception {
        mIndexerManager.detach();
        mZimbraSimulator.cleanup();
    }

    @Test
    public void start_indexing_without_register_doesnt_explode() {
        mParsedMessage.generateInternalIndexing();
    }

    @Test
    public void start_indexing_with_valid_indexer() {
        when(mIndexer.canHandle(anyString(), anyString())).thenReturn(true);
        mIndexerManager.register(mIndexer);

        mParsedMessage.generateInternalIndexing();

        verify(mIndexer, times(6)).canHandle(anyString(), anyString());
        verify(mIndexer, times(4)).extractPlainText(any(DataSource.class), anyString(), anyString());
    }

    @Test
    public void unregister_indexer_doesnt_get_called() {
        when(mIndexer.canHandle(anyString(), anyString())).thenReturn(true);
        mIndexerManager.register(mIndexer);
        mIndexerManager.unregister(mIndexer);

        mParsedMessage.generateInternalIndexing();

        verify(mIndexer, times(0)).canHandle(anyString(), anyString());
        verify(mIndexer, times(0)).extractPlainText(any(DataSource.class), anyString(), anyString());
    }
}