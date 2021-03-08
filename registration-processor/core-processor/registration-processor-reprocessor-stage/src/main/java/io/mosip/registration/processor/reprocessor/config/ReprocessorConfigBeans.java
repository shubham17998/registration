package io.mosip.registration.processor.reprocessor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import io.mosip.registration.processor.core.spi.restclient.RegistrationProcessorRestClientService;
import io.mosip.registration.processor.reprocessor.stage.ReprocessorStage;
import io.mosip.registration.processor.reprocessor.stage.WorkflowEventUpdateVerticle;
import io.mosip.registration.processor.rest.client.service.impl.RegistrationProcessorRestClientServiceImpl;

/**
 * Config class to get configurations and beans for Reprocessor stage
 * 
 * @author Pranav Kumar
 * @since 0.10.0
 *
 */
@PropertySource("classpath:bootstrap.properties")
@Configuration
public class ReprocessorConfigBeans {

	@Bean
	public ReprocessorStage getReprocessorStage() {
		return new ReprocessorStage();
	}
	@Bean
	public RegistrationProcessorRestClientService<Object> getRegistrationProcessorRestClientService() {
		return new RegistrationProcessorRestClientServiceImpl();
	}

	@Bean
	public WorkflowEventUpdateVerticle getWorkflowEventUpdateVerticle() {
		return new WorkflowEventUpdateVerticle();
	}
}
