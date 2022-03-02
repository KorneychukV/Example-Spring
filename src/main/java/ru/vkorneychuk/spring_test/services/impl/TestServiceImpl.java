package ru.vkorneychuk.spring_test.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vkorneychuk.spring_test.Models.TestTable;
import ru.vkorneychuk.spring_test.repositories.TestRepository;
import ru.vkorneychuk.spring_test.services.TestService;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Service
public class TestServiceImpl implements TestService {

    // Dependency injection
    @Autowired
    TestRepository testRepository;

    @Autowired
    EntityManager em;

    @Override
    public List<TestTable> getAllData(String title) {
        List<TestTable> testTables = new ArrayList<>();
        if (title == null) {
            testRepository.findAll().forEach(testTables::add);
        }
        else
            testRepository.findByTitleContaining(title).forEach(testTables::add);
        return testTables;
    }

    @Override
    public List<TestTable> getAllDataEm(String title) {
        List<TestTable> testTables;
        if (title == null)
            testTables = em.createQuery("SELECT tt FROM TestTable tt", TestTable.class).getResultList();
        else
            testTables = em.createQuery("SELECT tt FROM TestTable tt WHERE tt.title like :t", TestTable.class)
                    .setParameter('t', title)
                    .getResultList();

        return testTables;
    }
}
