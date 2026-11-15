package io.github.gchaldu.cli;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(
        name = "gchaldu",
        synopsisHeading = "Uso: ",
        optionListHeading = "Opciones:%n",
        versionProvider = GchalduVersionProvider.class
)
public class GchalduCommand {

    @Option(names = {"-h", "--help"}, usageHelp = true, description = "Muestra este mensaje de ayuda y sale.")
    boolean helpRequested;

    @Option(names = {"-V", "--version"}, versionHelp = true, description = "Muestra la información de versión y sale.")
    boolean versionRequested;
}
