package com.namoo.social.dao.mongo.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;

@Configuration
@PropertySource("classpath:mongo.properties")
@EnableMongoRepositories(basePackages = "com.namoo.social.dao.mongo.repository")
public class MongoConfiguration extends AbstractMongoConfiguration {
	//
	@Autowired Environment env;

	@Override
	protected String getDatabaseName() {
		// 
		return env.getProperty("database.name");
	}
	
	@Override
	public @Bean Mongo mongo() throws Exception {
		// 
		// MongoDB SAFE MODE 활성화
		MongoClientOptions options = MongoClientOptions.builder()
				.writeConcern(WriteConcern.SAFE)
				.build();
		
		String host = env.getProperty("database.host"); 
		int port = Integer.parseInt(env.getProperty("database.port")); 
		String dbname = env.getProperty("database.name"); 
		String username = env.getProperty("database.username"); 
		String password = env.getProperty("database.password"); 
		if (username != null && password != null) { 
			List<ServerAddress> seeds = Arrays.asList(new ServerAddress(host, port)); 
			List<MongoCredential> credentialsList = Arrays.asList( MongoCredential.createMongoCRCredential(username, dbname, password.toCharArray())); 
			
			return new MongoClient(seeds, credentialsList, options); 
		} else { 
			return new MongoClient(host); 
		}
	}

	// ---------------------------------------------------------------------------------------------
	
	public @Bean MongoRepositoryFactory getMongoRepositoryFactory() throws Exception {
		//
		return new MongoRepositoryFactory(mongoTemplate());
	}
	
	public @Bean GridFsTemplate gridFsTemplate() throws Exception {
		//
		return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter());
	}
}
