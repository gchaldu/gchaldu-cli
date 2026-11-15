package io.github.gchaldu.cli;

import picocli.CommandLine;
import picocli.CommandLine.UnmatchedArgumentException;

import java.util.List;

public class GchalduParameterExceptionHandler implements CommandLine.IParameterExceptionHandler {

    @Override
    public int handleParseException(CommandLine.ParameterException ex, String[] args) throws Exception {
        CommandLine commandLine = ex.getCommandLine();
        commandLine.getErr().println(buildMessage(ex));
        return commandLine.getCommandSpec().exitCodeOnInvalidInput();
    }

    private String buildMessage(CommandLine.ParameterException ex) {
        if (ex instanceof UnmatchedArgumentException unmatched) {
            List<String> unmatchedArgs = unmatched.getUnmatched();
            if (unmatchedArgs.size() == 1) {
                return "Opción desconocida '" + unmatchedArgs.getFirst() + "'";
            }
            return "Opciones desconocidas " + unmatchedArgs;
        }
        return ex.getMessage();
    }
}
