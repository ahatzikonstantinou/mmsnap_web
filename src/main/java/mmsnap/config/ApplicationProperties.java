package mmsnap.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Mmsnap Web.
 * <p>
 * Properties are configured in the application.yml file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
//    private String dataExcelTemplate = "";
//
//    public void setDataExcelTemplate( String dataExcelTemplate )
//    {
//        this.dataExcelTemplate = dataExcelTemplate;
//    }
//
//    public String getDataExcelTemplate()
//    {
//        return dataExcelTemplate;
//    }
}
