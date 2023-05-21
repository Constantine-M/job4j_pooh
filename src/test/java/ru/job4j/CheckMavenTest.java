package ru.job4j;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CheckMavenTest {

    @Test
    void whenReady() {
        assertThat(CheckMaven.ready("yes"))
                .isEqualTo("Am I ready? - Of course yes");
    }

    @Test
    void whenNotReady() {
        assertThatThrownBy(() -> CheckMaven.ready("No"))
                .hasMessageContaining("The correct answer");
    }
}