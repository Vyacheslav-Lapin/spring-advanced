package com.luxoft.ptc.employees.tasks;

import com.luxoft.ptc.employees.model.Employee;
import com.luxoft.ptc.employees.model.Task;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin
@RepositoryRestResource
@Tag(name = "Tasks", description = "Task Entity")
public interface TasksRepository extends PagingAndSortingRepository<Task, Long>, QueryByExampleExecutor<Task> {


    List<Employee> findByTitle(@Param("title") String title);

    @Query("select t from Task t where (t.title=?1 or ?1=null) " +
            "and (t.author.id=?2 or ?2=null) " +
            "and (t.responsible.id=?3 or ?3=null)")
    List<Employee> findByTitleAuthorResponsible(
            @Param("name") String name,
            @Param("authorId") Long authorId,
            @Param("responsibleId") Long responsibleId);

  Task save(Task task);
}

