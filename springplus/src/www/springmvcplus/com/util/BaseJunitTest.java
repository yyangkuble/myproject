package www.springmvcplus.com.util;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import www.springmvcplus.com.util.system.JUnit4ClassRunner;

@RunWith(JUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/configs/SpringContext.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class BaseJunitTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	
}
