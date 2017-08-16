package xyz.ndlr.model.provider.imgur;

import xyz.ndlr.model.provider.facebook.sendAPI.message.attachment.MultimediaPayload;
import xyz.ndlr.model.provider.facebook.shared.Attachment;
import xyz.ndlr.model.provider.facebook.shared.AttachmentType;
import xyz.ndlr.model.provider.facebook.webhook.Message;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class ImgurServiceTest {
    private ImgurService imgurService;

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    @Before
    public void setUp() throws Exception {
        imgurService = new ImgurService(new ImgurRepository());
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testUpload() {
        Attachment attachment = new Attachment();
        attachment.setType(AttachmentType.IMAGE);
        attachment.setPayload(new MultimediaPayload("https://www.google" +
                ".com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png"));

        Message message = new Message();
        message.setAttachments(new ArrayList<>(Arrays.asList(attachment, attachment)));


        try {
            imgurService.upload(message);
            System.out.println("We'll sleep now");
            Thread.sleep(5000);
            System.out.println("and now we're awake!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void help() {
        System.out.println("---\n" + atomicInteger.incrementAndGet() + "---\n");
    }
}
