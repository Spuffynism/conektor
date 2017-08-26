package xyz.ndlr.model.provider.imgur;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import xyz.ndlr.model.provider.facebook.sendAPI.message.attachment.MultimediaPayload;
import xyz.ndlr.model.provider.facebook.shared.Attachment;
import xyz.ndlr.model.provider.facebook.shared.AttachmentType;
import xyz.ndlr.model.provider.facebook.webhook.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class ImgurServiceTest {
    private ImgurService imgurService;

    @Before
    public void setUp() throws Exception {
        imgurService = new ImgurService(new ImgurRepository());
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testUpload() {
    }
}
