package it.megadix.orientdb.spring.samples.document;

import it.megadix.orientdb.spring.OrientDbTransactionManager;
import it.megadix.orientdb.spring.samples.Animal;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;

import com.orientechnologies.orient.core.db.ODatabase;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.record.impl.ODocument;

public class DocumentDbExample {

    static class MyConfiguration {
        @Bean
        ODatabase orientDbDatabase() {
            ODatabase database = new ODatabaseDocumentTx("local:databases/demo");
            if (!database.exists()) {
                database.create();
            }
            return database;
        }

        @Bean
        PlatformTransactionManager transactionManager() {
            OrientDbTransactionManager txManager = new OrientDbTransactionManager();
            txManager.setDatabase(orientDbDatabase());
            txManager.setUsername("admin");
            txManager.setPassword("admin");
            txManager.getEntityClassesPackages().add("it.megadix.orientdb.spring.samples.object");
            return txManager;
        }
    }

    private String dbPath = "databases/demo";
    private ApplicationContext ctx;
    private DocumentService service;

    public void run() throws Exception {
        init();

        List<Animal> animals = service.readAnimals();
        for (Animal animal : animals) {
            System.out.println(animal.toString());
        }
    }

    private void init() throws IOException {
        // delete database from previous runs (if any)
        File dbDir = new File(dbPath);
        if (dbDir.exists()) {
            FileUtils.cleanDirectory(dbDir);
        }

        // Spring initialization
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MyConfiguration.class);
        ctx.scan(DocumentDbExample.class.getPackage().getName());
        this.ctx = ctx;
        this.service = ctx.getBean(DocumentService.class);

        // add sample data
        ODocument milanoDoc = new ODocument("City");
        milanoDoc.field("id", "1");
        milanoDoc.field("name", "Milano");
        milanoDoc.save();

        ODocument romaDoc = new ODocument("City");
        romaDoc.field("id", "2");
        romaDoc.field("name", "Roma");
        romaDoc.save();

        ODocument ninaDoc = new ODocument("Animal");
        ninaDoc.field("id", "1");
        ninaDoc.field("name", "Nina");
        ninaDoc.field("city", milanoDoc);
        ninaDoc.save();

        ODocument kingDoc = new ODocument("Animal");
        kingDoc.field("id", "2");
        kingDoc.field("name", "King");
        kingDoc.field("city", romaDoc);
        kingDoc.save();

    }

    public static void main(String[] args) {
        try {
            new DocumentDbExample().run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
