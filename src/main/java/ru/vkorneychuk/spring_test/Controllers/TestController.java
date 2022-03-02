package ru.vkorneychuk.spring_test.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vkorneychuk.spring_test.Models.TestTable;
import ru.vkorneychuk.spring_test.repositories.TestRepository;
import ru.vkorneychuk.spring_test.services.impl.TestServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class TestController {

    // Dependency injection
    @Autowired
    TestRepository testRepository;

    @Autowired
    TestServiceImpl service;


        @GetMapping("/test")
    public ResponseEntity<List<TestTable>> getAllTests(@RequestParam(required = false) String title) {
        try {
            List<TestTable> testTables = service.getAllDataEm(title);
            return new ResponseEntity<>(testTables, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/test/{id}")
    public ResponseEntity<TestTable> getTutorialById(@PathVariable("id") long id) {
        Optional<TestTable> testData = testRepository.findById(id);
        if (testData.isPresent()) {
            return new ResponseEntity<>(testData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/test")
    public ResponseEntity<TestTable> createTutorial(@RequestBody TestTable testTable) {
        try {
            TestTable _testTable = testRepository
                    .save(new TestTable(testTable.getTitle(), testTable.getDescription(), false));
            return new ResponseEntity<>(_testTable, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/test/{id}")
    public ResponseEntity<TestTable> updateTutorial(@PathVariable("id") long id, @RequestBody TestTable testTable) {
        Optional<TestTable> tutorialData = testRepository.findById(id);
        if (tutorialData.isPresent()) {
            TestTable _testTable = tutorialData.get();
            _testTable.setTitle(testTable.getTitle());
            _testTable.setDescription(testTable.getDescription());
            _testTable.setPublished(testTable.isPublished());
            return new ResponseEntity<>(testRepository.save(_testTable), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/test/{id}")
    public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
        try {
            testRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/test")
    public ResponseEntity<HttpStatus> deleteAllTutorials() {
        try {
            testRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/test/published")
    public ResponseEntity<List<TestTable>> findByPublished() {
        try {
            List<TestTable> testTables = testRepository.findByPublished(true);
            if (testTables.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(testTables, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
