package com.soen343.smarthomesimulator;

import com.soen343.smarthomesimulator.controllers.HomeController;
import com.soen343.smarthomesimulator.controllers.PageController;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SmartHomeSimulatorApplicationTests {

	@Autowired
	PageController pageController;

	@Test
	void contextLoads() throws Exception {
		assertThat(pageController).isNotNull();
	}

}
