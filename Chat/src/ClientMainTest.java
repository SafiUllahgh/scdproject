import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.event.ActionEvent;

import org.junit.Test;

public class ClientMainTest {

	ClientMain obj = new ClientMain();
	@Test
	public void test() {
		String expected = "Client Signing on";
		assertEquals(expected,obj.main());
	}
}