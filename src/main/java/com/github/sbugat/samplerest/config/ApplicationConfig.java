


@Configuration
@ComponentScan("com.github.sbugat.samplerest")
@PropertySource("classpath:application-configuration.properties")
public class ApplicationConfig {
	
	@Inject
	private Environment environment;
	
	@Bean
	public static PropertyPlaceholderConfigurer ppc() {
		// instantiate, configure and return ppc ...
		return new PropertySourcesPlaceholderConfigurer();
	}
}
