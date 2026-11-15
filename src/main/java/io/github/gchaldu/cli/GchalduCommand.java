package io.github.gchaldu.cli;

import picocli.CommandLine.Command;

@Command(
        name = "gchaldu",
        mixinStandardHelpOptions = true,
        versionProvider = GchalduVersionProvider.class
)
public class GchalduCommand {
}
