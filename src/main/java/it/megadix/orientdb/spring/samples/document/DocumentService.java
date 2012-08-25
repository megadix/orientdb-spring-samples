package it.megadix.orientdb.spring.samples.document;

import it.megadix.orientdb.spring.samples.Animal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class DocumentService {

    private DocumentDao dao;

    @Autowired
    public void setDao(DocumentDao dao) {
        this.dao = dao;
    }

    public List<Animal> readAnimals() {
        return dao.readAnimals();
    }
}
