package demos;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.HSQL;

@Configuration
public class SpringConfig {

    @Bean
    public DataSource release_ias_db() {
        return new EmbeddedDatabaseBuilder().setType(HSQL).addScript("/createDb.sql").build();
    }
}
