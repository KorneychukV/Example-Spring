package ru.vkorneychuk.spring_test.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vkorneychuk.spring_test.Models.TestTable;

import java.util.List;

// Интерфейс предоставляющий набор стандартных методов JPA для работы с БД.
public interface TestRepository extends JpaRepository<TestTable, Long> {
    // Метод позволяем искать по полю
    List<TestTable> findByPublished(boolean published);
    List<TestTable> findByTitleContaining(String title);
}
