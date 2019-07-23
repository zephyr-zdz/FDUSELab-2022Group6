package com.huawei.devcloud.demoapp.test;

import com.fasterxml.jackson.databind.ObjectReader;
import com.huawei.devcloud.demoapp.api.Saying;
import com.huawei.devcloud.demoapp.resources.HelloWorldResource;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Rule;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

import java.io.IOException;

public class HelloWorldEndpointTest {

    @Rule
    public final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new HelloWorldResource("Hello, %s!", "Stranger"))
            .build();

    @Test
    public void helloWorldDropwizard() throws IOException {
        // Hit the endpoint and get the raw json string
        String resp = resources.client().target("/hello-world")
                .queryParam("name", "dropwizard")
                .request().get(String.class);

        // The expected json that will be returned
        String json = "{ \"id\": 1, \"content\": \"Hello, dropwizard!\" }";

        // The object responsible for translating json to our class
        ObjectReader reader = resources.getObjectMapper().reader(Saying.class);

        // Deserialize our actual and expected responses
        Saying actual = reader.readValue(resp);
        Saying expected = reader.readValue(json);

        assertThat(actual.getId())
                .isEqualTo(expected.getId())
                .isEqualTo(1);

        assertThat(actual.getContent())
                .isEqualTo(expected.getContent())
                .isEqualTo("Hello, dropwizard!");
    }

    @Test
    public void helloWorldAbsentName() {
        // A more terse way to test just an endpoint
        Saying actual = resources.client().target("/hello-world")
                .request().get(Saying.class);
        Saying expected = new Saying(1, "Hello, Stranger!");
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getContent()).isEqualTo(expected.getContent());
    }
}
