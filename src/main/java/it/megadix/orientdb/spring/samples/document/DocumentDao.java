package it.megadix.orientdb.spring.samples.document;

import it.megadix.orientdb.spring.OrientDbDaoSupport;
import it.megadix.orientdb.spring.samples.Animal;
import it.megadix.orientdb.spring.samples.City;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;

@Component
public class DocumentDao extends OrientDbDaoSupport {
    
    public List<Animal> readAnimals() {
        List<ODocument> queryResult = database.query(new OSQLSynchQuery<ODocument>("select * from Animal"));
        List<Animal> result = new ArrayList<Animal>(queryResult.size());

        for (ODocument doc : queryResult) {
            Animal animal = new Animal();
            animal.setId(doc.field("id"));
            animal.setName(doc.field("name").toString());
            ODocument cityDoc = doc.field("city");
            if (cityDoc != null) {
                City city = new City();
                city.setId(cityDoc.field("id"));
                city.setName(cityDoc.field("name").toString());
                animal.setCity(city);
            }
            result.add(animal);
        }

        return result;
    }
}
