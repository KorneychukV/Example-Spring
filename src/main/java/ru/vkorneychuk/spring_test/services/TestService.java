package ru.vkorneychuk.spring_test.services;

import ru.vkorneychuk.spring_test.Models.TestTable;

import java.util.List;

public interface TestService {

    List<TestTable> getAllData(String title);
    List<TestTable> getAllDataEm(String title);

}
