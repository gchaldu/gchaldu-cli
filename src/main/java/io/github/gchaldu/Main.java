package io.github.gchaldu;

import io.github.gchaldu.cli.GchalduCommand;

public class Main {

    public static void main(String[] args) {
        int exitCode = GchalduCommand.newCommandLine().execute(args);
        System.exit(exitCode);
    }
}
