package com.rd.lab.pizza_service.service.order_service;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/annos_based_repo_test_config.xml" })
@ActiveProfiles(profiles = { "dev" })
public class RepositoryTestConfig {

}