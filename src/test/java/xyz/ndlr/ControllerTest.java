package xyz.ndlr;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.annotation.*;

/**
 * Default test configuration
 * usage:
 * <code>
 *     @DefaultTest
 *     @RunWith(SpringRunner.class)
 *     public class UserControllerTest {}
 * </code>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@SpringBootTest(classes = {Application.class})
@AutoConfigureMockMvc
public @interface ControllerTest {
}
