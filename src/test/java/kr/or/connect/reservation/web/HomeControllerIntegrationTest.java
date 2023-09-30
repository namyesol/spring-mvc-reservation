package kr.or.connect.reservation.web;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import kr.or.connect.reservation.security.WithMockCustomUser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
    "file:src/main/webapp/WEB-INF/dispatcher-servlet.xml", 
    "file:src/main/webapp/WEB-INF/application-context.xml",
    "file:src/main/webapp/WEB-INF/security-context.xml"
})
@WebAppConfiguration
@ActiveProfiles("jdbc")
public class HomeControllerIntegrationTest {
    
    @InjectMocks
    private HomeController homeController;

    @Autowired
    WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).apply(springSecurity()).build();
    }
    
    @Test
    @WithAnonymousUser
    public void testWithAnonymousUser() throws Exception {
        this.mockMvc.perform(get("/protected"))
            .andExpect(status().is3xxRedirection())
            .andExpect(MockMvcResultMatchers.redirectedUrlPattern("**/login"));
    }

    @Test
    @WithMockCustomUser(email="test@connect.or.kr")
    public void testWithMockCustomUser() throws Exception {
        this.mockMvc.perform(get("/protected"))
            .andExpect(status().isOk())
            .andExpect(SecurityMockMvcResultMatchers.authenticated().withUsername("test@connect.or.kr"));
    }

}
