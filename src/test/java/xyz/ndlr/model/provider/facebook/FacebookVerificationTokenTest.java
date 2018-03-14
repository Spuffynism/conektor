package xyz.ndlr.model.provider.facebook;

import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import xyz.ndlr.exception.InvalidFacebookVerificationToken;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class FacebookVerificationTokenTest {
    private enum VALID_PARAMETER {
        MODE("hub.mode", "subscribe"),
        CHALLENGE("hub.challenge", "test_challenge"),
        VERIFY_TOKEN("hub.verify_token", "test_verify_token");

        private String key;
        private String value;

        VALID_PARAMETER(String key, String value) {
            this.key = key;
            this.value = value;
        }

        String key() {
            return this.key;
        }

        String value() {
            return this.value;
        }

        static Map<String, String> asMap(VALID_PARAMETER... params) {
            return Arrays.stream(params)
                    .collect(Collectors.toMap(e -> e.key, e -> e.value));
        }
    }

    @Test
    public void validationSucceedsWhenParametersAreValid() throws Exception {
        Map<String, String> params = VALID_PARAMETER.asMap(VALID_PARAMETER.MODE,
                VALID_PARAMETER.CHALLENGE, VALID_PARAMETER.VERIFY_TOKEN);

        FacebookVerificationToken token = new FacebookVerificationToken(params);
        ReflectionTestUtils.setField(token, "actualValidToken", "test_verify_token",
                String.class);
        token.validate();
    }

    @Test
    public void validationFailsWhenModeIsNull() {
        Map<String, String> params = new HashMap<>();
        params.put(VALID_PARAMETER.MODE.key(), null);

        assertValidationFails(params);
    }

    @Test
    public void validationFailsWhenModeIsNotSubscribe() {
        Map<String, String> params = new HashMap<>();
        params.put(VALID_PARAMETER.MODE.key(), "invalid_mode");
        assertValidationFails(params);
    }

    @Test
    public void validationFailsWhenTokenFromFacebookIsNull() {
        Map<String, String> params = VALID_PARAMETER.asMap(VALID_PARAMETER.MODE);
        params.put("hub.verify_token", null);

        assertValidationFails(params);
    }

    @Test
    public void validationFailsWhenTokenFromFacebookIsEmpty() {
        Map<String, String> params = VALID_PARAMETER.asMap(VALID_PARAMETER.MODE);
        params.put("hub.verify_token", "");

        assertValidationFails(params);
    }

    @Test
    public void validationFailsWhenTokenFromFacebookIsUnspecified() {
        Map<String, String> params = VALID_PARAMETER.asMap(VALID_PARAMETER.MODE);

        assertValidationFails(params);
    }

    private void assertValidationFails(Map<String, String> params) {
        FacebookVerificationToken token = new FacebookVerificationToken(params);

        ReflectionTestUtils.setField(token, "actualValidToken", "test_verify_token",
                String.class);
        assertThrows(InvalidFacebookVerificationToken.class, token::validate);
    }
}