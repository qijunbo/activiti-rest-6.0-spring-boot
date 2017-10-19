package com.sunway.activiti.conf;

import javax.sql.DataSource;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ActivitiEngineConfiguration {

    private final Logger log = LoggerFactory.getLogger(ActivitiEngineConfiguration.class);


    @Bean(name = "transactionManager")
    public PlatformTransactionManager annotationDrivenTransactionManager(DataSource dataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }

    @Bean
    public ProcessEngineFactoryBean processEngineFactoryBean(ProcessEngineConfigurationImpl config) {
        ProcessEngineFactoryBean factoryBean = new ProcessEngineFactoryBean();
        factoryBean.setProcessEngineConfiguration(config);
        return factoryBean;
    }

    @Bean
    public ProcessEngine processEngine(ProcessEngineFactoryBean bean) {
        // Safe to call the getObject() on the @Bean annotated
        // processEngineFactoryBean(), will be
        // the fully initialized object instanced from the factory and will NOT
        // be created more than once
        try {
            ProcessEngine engine = bean.getObject();
            if (log.isDebugEnabled()) {
                log.debug(String.format("ProcessEngine [%s] Version: [%s]", engine.getName(), ProcessEngine.VERSION));
            }
            return engine;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    @ConfigurationProperties(prefix = "cfg.activiti")
    public ProcessEngineConfigurationImpl processEngineConfiguration(DataSource dataSource,
            PlatformTransactionManager transactionManager) {
        SpringProcessEngineConfiguration cfg = new SpringProcessEngineConfiguration();
        cfg.setDataSource(dataSource);
        cfg.setTransactionManager(transactionManager);
        return cfg;
    }

    @Bean
    public RuntimeService createRuntimeService(ProcessEngine processEngine) {
        return processEngine.getRuntimeService();
    }

    @Bean
    public RepositoryService createRepositoryService(ProcessEngine processEngine) {
        return processEngine.getRepositoryService();
    }

    @Bean
    public TaskService createTaskService(ProcessEngine processEngine) {
        return processEngine.getTaskService();
    }

    @Bean
    public ManagementService createManagementService(ProcessEngine processEngine) {
        return processEngine.getManagementService();
    }

    @Bean
    public IdentityService createIdentityService(ProcessEngine processEngine) {
        return processEngine.getIdentityService();
    }

    @Bean
    public HistoryService createHistoryService(ProcessEngine processEngine) {
        return processEngine.getHistoryService();
    }

    @Bean
    public FormService createFormService(ProcessEngine processEngine) {
        return processEngine.getFormService();
    }

}
