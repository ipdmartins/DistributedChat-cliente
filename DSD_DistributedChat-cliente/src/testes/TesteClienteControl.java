/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testes;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import control.ClienteControl;

import static org.junit.Assert.*;

/**
 *
 * @author ipdmartins
 */
public class TesteClienteControl {

	public static ClienteControl cliente;

	// private static Cliente cliente;

	public TesteClienteControl() {
		this.cliente = ClienteControl.getInstance();
	}

	@Test
	public void testeStore() {
		String result = cliente.register("Igor", "email@email", "1980", "123", "56000", "131231651561", "56001", "4941616");
		assertEquals("ok", result);
	}

	@Test
	public void testeLogin() {
		//String json = "{\"email\":" + email + ",\"senha\":" + pass + "}";
		String result = cliente.login("email@email", "1980");
		assertEquals("ok", result);
	}
	
	@BeforeClass
	public static void setUpClass() {
	}

	@AfterClass
	public static void tearDownClass() {
	}

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}

}
