package io.github.gchaldu.cli;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.assertj.core.api.Assertions.assertThat;

class GchalduCommandHelpTest {

    @Test
    @DisplayName("C3: --help muestra la ayuda en castellano")
    void helpShowsSpanishUsage() {
        StringWriter capturedOut = new StringWriter();
        CommandLine commandLine = GchalduCommand.newCommandLine();
        commandLine.setOut(new PrintWriter(capturedOut));

        int exitCode = commandLine.execute("--help");
        String output = capturedOut.toString();

        assertThat(exitCode).isZero();
        assertThat(output).contains("Uso:");
        assertThat(output).contains("--version");
        assertThat(output).contains("--help");
    }
}
