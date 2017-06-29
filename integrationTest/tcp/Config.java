package tcp;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
	@Bean
	@Qualifier("discover.mcastgroup")
	public String macastGroup(){
		return "235.40.49.1";
	}
	
	@Bean
	@Qualifier("discover.mcastport")
	public Integer mcastPort(){
		return 27015;
	}
	
	@Bean
	@Qualifier("tcp.port")
	public Integer tcpPort(){
		return 27015;
	}
}
