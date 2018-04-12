package xyz.ndlr.model.provider.imgur;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
