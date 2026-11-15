package io.github.gchaldu.cli;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

class GchalduCommandVersionTest {

    @Test
    @DisplayName("C2: --version muestra la versión del pom")
    void versionShowsProjectVersion() {
        String version = System.getProperty("project.version");
        if (version == null) {
            fail("La propiedad 'project.version' no está definida; ejecutá el test con Maven.");
        }

        ByteArrayOutputStream capturedOut = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(capturedOut));

        try {
            int exitCode = new CommandLine(new GchalduCommand()).execute("--version");
            String output = capturedOut.toString();

            assertThat(exitCode).isZero();
            assertThat(output).contains("gchaldu");
            assertThat(output).contains(version);
        } finally {
            System.setOut(originalOut);
        }
    }
}
