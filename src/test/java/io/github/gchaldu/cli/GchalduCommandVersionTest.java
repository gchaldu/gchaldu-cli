package io.github.gchaldu.cli;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.PrintWriter;
import java.io.StringWriter;

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

        StringWriter capturedOut = new StringWriter();
        CommandLine commandLine = GchalduCommand.newCommandLine();
        commandLine.setOut(new PrintWriter(capturedOut));

        int exitCode = commandLine.execute("--version");
        String output = capturedOut.toString();

        assertThat(exitCode).isZero();
        assertThat(output).contains("gchaldu " + version);
    }
}
