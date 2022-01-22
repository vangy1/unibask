package sk.unibask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

//    @Profile("prod")
//    @Bean
//    public ServletWebServerFactory servletContainer() {
//        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
//            @Override
//            protected void postProcessContext(Context context) {
//                SecurityConstraint securityConstraint = new SecurityConstraint();
//                securityConstraint.setUserConstraint("CONFIDENTIAL");
//                SecurityCollection collection = new SecurityCollection();
//                collection.addPattern("/*");
//                securityConstraint.addCollection(collection);
//                context.addConstraint(securityConstraint);
//            }
//        };
////        tomcat.addAdditionalTomcatConnectors(createHttpConnector());
//        return tomcat;
//    }


//    private Connector createHttpConnector() {
//        Connector connector =
//                new Connector("org.apache.coyote.http11.Http11NioProtocol");
//        connector.setScheme("http");
//        connector.setSecure(false);
//        connector.setPort(80);
//        connector.setRedirectPort(443);
//
//        connector.setProperty("connectionUploadTimeout", "18000000");
//        connector.setProperty("disableUploadTimeout", "false");
//        return connector;
//    }

}
