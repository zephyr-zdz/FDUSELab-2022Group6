package com.huawei.devcloud.demoapp.test;

import com.huawei.devcloud.demoapp.HelloWorldApplication;
import com.huawei.devcloud.demoapp.HelloWorldConfiguration;
import com.huawei.devcloud.demoapp.api.Saying;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import javax.ws.rs.client.Client;

import static org.assertj.core.api.Assertions.assertThat;

@Category(IntegrationTest.class)
public class HelloWorldIntegrationTest {
    @Rule
    public final DropwizardAppRule<HelloWorldConfiguration> RULE =
            new DropwizardAppRule<HelloWorldConfiguration>(HelloWorldApplication.class,
                    ResourceHelpers.resourceFilePath("helloworld-integrationtest.yaml"));

    @Test
    public void runServerTest() throws Exception {
        Client client = new JerseyClientBuilder().build();

        // It is an integration test: this is really longer than just build + unit test time
        Thread.sleep(30000);

        Saying result = client.target(
                String.format("http://localhost:%d/api/hello-world", RULE.getLocalPort())
        ).queryParam("name", "dropwizard").request().get(Saying.class);

        assertThat(result.getContent()).isEqualTo("Hello, dropwizard!");


    }
}
