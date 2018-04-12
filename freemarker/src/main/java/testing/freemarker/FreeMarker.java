package testing.freemarker;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;
import java.io.StringReader;

public class FreeMarker
{
    public static void main(String[] args) throws IOException, TemplateException {

        Configuration cfg = new Configuration(new Version("2.3.23"));
        
        // Set the preferred charset template files are stored in. UTF-8 is
        // a good choice in most applications:
        cfg.setDefaultEncoding("UTF-8");

        // Sets how errors will appear.
        // During web page *development* TemplateExceptionHandler.HTML_DEBUG_HANDLER is better.
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        // Don't log exceptions inside FreeMarker that it will thrown at you anyway:
        cfg.setLogTemplateExceptions(false);

        cfg.setClassForTemplateLoading(FreeMarker.class, "/testing/freemarker");
        cfg.setDefaultEncoding("UTF-8");

        Template template = cfg.getTemplate("test.ftl");

        Map<String, Object> templateData = new HashMap<String, Object>();
        templateData.put("msg", "Today is a beautiful day");

        try (StringWriter out = new StringWriter()) {

            template.process(templateData, out);
            System.out.println(out.getBuffer().toString());

            out.flush();
        }
        
        templateData.put("user", "a user");

        String templateStr="Hello ${use}. ${msg}";
        Template t = new Template("name", new StringReader(templateStr),cfg);
         try (StringWriter out = new StringWriter()) {
            t.process(templateData, out);

            System.out.println(out.getBuffer().toString());

            out.flush();
        }
    }

}
