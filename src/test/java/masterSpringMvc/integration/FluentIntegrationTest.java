package masterSpringMvc.integration;

import masterSpringMvc.MasterSpringMvcApplication;
import org.fluentlenium.adapter.FluentTest;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by private on 24.02.18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { MasterSpringMvcApplication.class })
//@WebIntegrationTest
public class FluentIntegrationTest extends FluentTest {

}
