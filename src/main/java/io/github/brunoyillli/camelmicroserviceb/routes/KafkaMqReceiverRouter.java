package io.github.brunoyillli.camelmicroserviceb.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import io.github.brunoyillli.camelmicroserviceb.entity.CurrencyExchange;

@Component
public class KafkaMqReceiverRouter extends RouteBuilder{
	
	@Override
	public void configure() throws Exception {

		from("kafka:myKafkaTopic")
		.unmarshal().json(JsonLibrary.Jackson, CurrencyExchange.class)
		.to("log:received-message-from-kafka");
		
	}

}
