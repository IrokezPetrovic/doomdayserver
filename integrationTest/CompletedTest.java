import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={CompletedTest.Config.class})
public class CompletedTest {

	
	@Test
	public void test1(){
		
	}
	
	@Import(org.doomday.server.WebConfig.class)
	public static class Config{
		
	}
}
