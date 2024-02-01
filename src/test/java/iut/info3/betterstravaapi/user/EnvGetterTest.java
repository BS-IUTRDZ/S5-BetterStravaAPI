package iut.info3.betterstravaapi.user;

import iut.info3.betterstravaapi.EnvGetter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
public class EnvGetterTest {

    @Autowired
    EnvGetter envGetter;

    @Test
    public void testGetterSentence(){

        assertEquals("",true,envGetter.getSentence().length() > -1);
    }

    @Test
    public void testGetterExpiration(){

        assertEquals("",true, envGetter.getExpiration() > -1);
    }

}
