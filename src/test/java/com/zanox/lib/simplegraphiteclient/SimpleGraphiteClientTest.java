package com.zanox.lib.simplegraphiteclient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;



@RunWith(MockitoJUnitRunner.class)
public class SimpleGraphiteClientTest {

	@Mock
	private Socket socket;	
	private ByteArrayOutputStream out;	
	private SimpleGraphiteClient simpleGraphiteClient;

	private long currentTimestamp;
	
	@Before
	public void setUp() throws IOException {
		out = new ByteArrayOutputStream();
		when(socket.getOutputStream()).thenReturn(out);
		currentTimestamp = System.currentTimeMillis() / 1000;
		
		simpleGraphiteClient = new SimpleGraphiteClient("", 12) {
				@Override
				protected Socket createSocket() {
					return socket;
				}
				
				@Override
				protected long getCurrentTimestamp() {
					return currentTimestamp;
				}
			};
	}
	
	@Test
	public void testSendSingleMetric() throws IOException {
		simpleGraphiteClient.sendMetric("junit.test.metric", 4711, 1l);		
		assertEquals(String.format("junit.test.metric 4711 1%n"), out.toString());
	}

	@Test
	public void testSendSingleMetricCurrentTime() throws IOException, InterruptedException {		
		simpleGraphiteClient.sendMetric("junit.test.metric", 4711);		
		assertEquals(String.format("junit.test.metric 4711 %d%n", currentTimestamp), out.toString());
	}
	
	@Test
	public void testSendMultipleMetrics() {
		Map<String, Long> data = new  HashMap<String, Long>();
		data.put("junit.test.metric1", 4711L);
		data.put("junit.test.metric2", 4712L);
		simpleGraphiteClient.sendMetrics(data);
		assertTrue(out.toString().contains("junit.test.metric1 4711 " + currentTimestamp));
		assertTrue(out.toString().contains("junit.test.metric2 4712 " + currentTimestamp));
	}
	
	@Test
	public void testCurrentTimestamp() {
		long timestamp = new SimpleGraphiteClient("", 0).getCurrentTimestamp();
		assertTrue((System.currentTimeMillis() / 1000) - timestamp < 2);
	}
	
}
