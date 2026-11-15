package io.github.gchaldu.cli;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.assertj.core.api.Assertions.assertThat;

class GchalduCommandUnknownOptionTest {

    @Test
    @DisplayName("C4: opción desconocida muestra error en castellano y sale con código 2")
    void unknownOptionShowsSpanishError() {
        StringWriter capturedErr = new StringWriter();
        CommandLine commandLine = GchalduCommand.newCommandLine();
        commandLine.setErr(new PrintWriter(capturedErr));

        int exitCode = commandLine.execute("--opcion-desconocida");
        String output = capturedErr.toString();

        assertThat(exitCode).isEqualTo(2);
        assertThat(output).contains("Opción desconocida '--opcion-desconocida'");
        assertThat(output).doesNotContain("Unknown");
        assertThat(output).doesNotContain("Did you mean");
    }
}
