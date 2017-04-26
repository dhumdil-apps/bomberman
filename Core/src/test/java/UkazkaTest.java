
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UkazkaTest {

    @Test
    public void testFunkcionalityABC() {
    	Ukazka u = new Ukazka();
    	// test ze 1+1=2
    	assertEquals(2, u.scitaj(1, 1));
    	// test ze 15+5=20
    	assertEquals(20, u.scitaj(15, 5));
    	// test ze 2+-5=-3
    	assertEquals(-3, u.scitaj(2, -5));
    }

}