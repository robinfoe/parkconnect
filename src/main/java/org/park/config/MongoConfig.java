package org.park.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import org.park.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

// import com.mongodb.client.MongoClient;
// import com.mongodb.client.MongoClients;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories("org.park.repository")
public class MongoConfig  {

    @Value("${db.host}")
    private String host;

    @Value("${db.port}")
    private int port;    //t =  28888; //27017 ;

    @Value("${db.name}")
    private String database;

    @Value("${db.credentials.loc}")
    private String credentialStore;

    @Value("${db.credentials.salt}")
    private String credentialPass;

   
    public MongoClient mongoClient() throws IllegalArgumentException{

       try{
            SecurityUtil.getInstance().loadKeyStore(this.credentialStore, this.credentialPass);
            ConnectionString connectionString = new ConnectionString("mongodb://"+this.host+":"+this.port);

            // MongoCredential cred = MongoCredential.createCredential(
            //                         SecurityUtil.getInstance().getPasswordFromKeystore("username", this.credentialPass), 
            //                         "admin", SecurityUtil.getInstance().getPasswordFromKeystore("password", this.credentialPass).toCharArray());

            MongoCredential cred = MongoCredential.createCredential(
                SecurityUtil.getInstance().getPasswordFromKeystore("username", this.credentialPass), 
                this.database, SecurityUtil.getInstance().getPasswordFromKeystore("password", this.credentialPass).toCharArray());

            MongoClientSettings settings = MongoClientSettings.builder()
                                                    .applyConnectionString(connectionString)
                                                    .credential(cred)
                                                    .build();

            return MongoClients.create(settings);
       }catch(Exception e){
           e.printStackTrace();
       }
       return null;
    }

    @Bean
    public MongoDbFactory mongoDbFactory() {
        // MongoClient mongoClient = new MongoClient("127.0.0.1:27017");
        return new SimpleMongoClientDbFactory(this.mongoClient(), this.database);
    }

    


}