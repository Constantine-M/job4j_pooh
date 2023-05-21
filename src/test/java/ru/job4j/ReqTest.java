package ru.job4j;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ReqTest {

    @Test
    void whenQueueModePostMethod() {
        String ls = System.lineSeparator();
        String content = "POST /queue/weather HTTP/1.1" + ls
                + "Host: localhost:9000" + ls
                + "User-Agent: curl/7.72.0" + ls
                + "Accept: */*" + ls
                + "Content-Length: 14" + ls
                + "Content-Type: application/x-www-form-urlencoded" + ls
                + "" + ls
                + "temperature=18" + ls;
        Req req = Req.of(content);
        assertThat(req.httpRequestType()).isEqualTo("POST");
        assertThat(req.getPoohMode()).isEqualTo("queue");
        assertThat(req.getSourceName()).isEqualTo("weather");
        assertThat(req.getParam()).isEqualTo("temperature=18");
    }

    @Test
    void whenQueueModeGetMethod() {
        String ls = System.lineSeparator();
        String content = "GET /queue/weather HTTP/1.1" + ls
                + "Host: localhost:9000" + ls
                + "User-Agent: curl/7.72.0" + ls
                + "Accept: */*" + ls + ls + ls;
        Req req = Req.of(content);
        assertThat(req.httpRequestType()).isEqualTo("GET");
        assertThat(req.getPoohMode()).isEqualTo("queue");
        assertThat(req.getSourceName()).isEqualTo("weather");
        assertThat(req.getParam()).isEqualTo("");
    }

    @Test
    void whenTopicModePostMethod() {
        String ls = System.lineSeparator();
        String content = "POST /topic/weather HTTP/1.1" + ls
                + "Host: localhost:9000" + ls
                + "User-Agent: curl/7.72.0" + ls
                + "Accept: */*" + ls
                + "Content-Length: 14" + ls
                + "Content-Type: application/x-www-form-urlencoded" + ls
                + "" + ls
                + "temperature=18" + ls;
        Req req = Req.of(content);
        assertThat(req.httpRequestType()).isEqualTo("POST");
        assertThat(req.getPoohMode()).isEqualTo("topic");
        assertThat(req.getSourceName()).isEqualTo("weather");
        assertThat(req.getParam()).isEqualTo("temperature=18");
    }

    @Test
    void whenTopicModeGetMethod() {
        String ls = System.lineSeparator();
        String content = "GET /topic/weather/client407 HTTP/1.1" + ls
                + "Host: localhost:9000" + ls
                + "User-Agent: curl/7.72.0" + ls
                + "Accept: */*" + ls + ls + ls;
        Req req = Req.of(content);
        assertThat(req.httpRequestType()).isEqualTo("GET");
        assertThat(req.getPoohMode()).isEqualTo("topic");
        assertThat(req.getSourceName()).isEqualTo("weather");
        assertThat(req.getParam()).isEqualTo("client407");
    }
}