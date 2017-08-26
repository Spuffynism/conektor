package xyz.ndlr;

import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
public class Application {
    private static final String TLS_CIPHERS = "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256," +
            "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA,TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384, " +
            "TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA,TLS_ECDHE_RSA_WITH_RC4_128_SHA," +
            "TLS_RSA_WITH_AES_128_CBC_SHA256,TLS_RSA_WITH_AES_128_CBC_SHA," +
            "TLS_RSA_WITH_AES_256_CBC_SHA256,TLS_RSA_WITH_AES_256_CBC_SHA,SSL_RSA_WITH_RC4_128_SHA";

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    @Profile("http")
    public void noop() {
    }

    /**
     * Starts app in a servlet with https using the specified keystore file.
     * ex.:
     * # mvn clean install
     * # java -Dspring.profiles.active=https -Dkeystore.file=file:///C:/g/api/keys/tp4keystore
     *
     * @param keystoreFile the keystore's file path
     * @return ConfigurableEmbeddedServletContainer the app's servlet
     * @throws Exception e
     */
    @Bean
    @Profile("https")
    EmbeddedServletContainerCustomizer containerCustomizer(
            @Value("${keystore.file}") Resource keystoreFile,
            @Value("${server.port}") int port,
            @Value("${keystore.pass}") String keyStorePass,
            @Value("${keystore.alias}") String keyAlias) throws Exception {
        String absoluteKeystoreFile = keystoreFile.getFile().getAbsolutePath();

        return (ConfigurableEmbeddedServletContainer container) -> {
            TomcatEmbeddedServletContainerFactory tomcat =
                    (TomcatEmbeddedServletContainerFactory) container;
            tomcat.addConnectorCustomizers(
                    (connector) -> {
                        connector.setPort(port);
                        connector.setSecure(true);
                        connector.setScheme("keys");

                        Http11NioProtocol protocol =
                                (Http11NioProtocol) connector.getProtocolHandler();
                        protocol.setSSLEnabled(true);
                        protocol.setSSLProtocol("TLSv1.2");
                        protocol.setCiphers(TLS_CIPHERS);
                        protocol.setKeystoreFile(absoluteKeystoreFile);
                        protocol.setKeystorePass(keyStorePass);
                        protocol.setKeystoreType("JKS");
                        protocol.setKeyAlias(keyAlias);
                    }
            );
        };
    }
}
