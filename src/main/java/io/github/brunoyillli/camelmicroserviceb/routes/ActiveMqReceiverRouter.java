package io.github.brunoyillli.camelmicroserviceb.routes;

import java.math.BigDecimal;
import java.util.Currency;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.brunoyillli.camelmicroserviceb.CurrencyExchange;

@Component
public class ActiveMqReceiverRouter extends RouteBuilder{

	@Autowired
	private MyCurrencyExchangeTransformer myCurrencyExchangeTransformer;
	
	@Autowired
	private MyCurrencyExchangeProcessor myCurrencyExchangeProcessor;
	
	@Override
	public void configure() throws Exception {

//		pegando os json no activemq e convertendo para objeto e depois tratando eles
//		from("activemq:my-activemq-queue")
//		.unmarshal().json(JsonLibrary.Jackson, CurrencyExchange.class)
//		.bean(myCurrencyExchangeProcessor)
//		.bean(myCurrencyExchangeTransformer)
//		.to("log:received-message-from-active-mq");
		
		from("activemq:my-activemq-xml-queue")
		.unmarshal().jacksonxml(CurrencyExchange.class)
		//.bean(myCurrencyExchangeProcessor)
		//.bean(myCurrencyExchangeTransformer)
		.to("log:received-message-from-active-mq");
		
	}

}

@Component
class MyCurrencyExchangeTransformer {
		
	public CurrencyExchange processMessage(CurrencyExchange currencyExchange) {
		currencyExchange.setConversionMultiple(
				currencyExchange.getConversionMultiple().multiply(BigDecimal.TEN));
		return currencyExchange;
	}
}

@Component
class MyCurrencyExchangeProcessor{
	
	Logger logger = LoggerFactory.getLogger(ActiveMqReceiverRouter.class);
	
	public void processMessage(CurrencyExchange currencyExchange) {
		logger.info("DO some processing with currencyEnchage.getConversionMultiple()"
				+ " value which is {}", currencyExchange.getConversionMultiple());
	}
}
