package io.github.gchaldu.cli;

import picocli.CommandLine.IVersionProvider;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class GchalduVersionProvider implements IVersionProvider {

    @Override
    public String[] getVersion() throws Exception {
        Properties properties = loadProperties();
        String version = properties.getProperty("version");
        if (version == null || version.isBlank()) {
            throw new IOException("No se encontró la propiedad 'version' en app.properties");
        }
        return new String[]{"gchaldu " + version};
    }

    private Properties loadProperties() throws IOException {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("app.properties")) {
            if (input == null) {
                throw new IOException("No se encontró el recurso app.properties");
            }
            try (InputStreamReader reader = new InputStreamReader(input, StandardCharsets.UTF_8)) {
                properties.load(reader);
            }
        }
        return properties;
    }
}
