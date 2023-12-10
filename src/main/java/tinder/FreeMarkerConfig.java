package tinder;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.IOException;

public class FreeMarkerConfig {
    private static final Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);

    static {
        try {
            cfg.setDirectoryForTemplateLoading(new File("src/main/resources/templates"));

            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setLogTemplateExceptions(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Configuration getInstance() {
        return cfg;
    }
}
