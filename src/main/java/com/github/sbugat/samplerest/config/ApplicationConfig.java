


@Configuration
@ComponentScan("com.github.sbugat.samplerest")
@PropertySource("classpath:application-configuration.properties")
public class ApplicationConfig {
	
	@Inject
	private Environment environment;
}
