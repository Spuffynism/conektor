package xyz.ndlr.controller.index;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IndexControllerSmokeTest {

    @Autowired
    private IndexController controller;

    @Test
    public void doesNotSmoke() throws Exception {
        assertThat(controller).isNotNull();
    }
}
