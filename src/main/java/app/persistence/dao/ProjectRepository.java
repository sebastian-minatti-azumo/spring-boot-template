package app.persistence.dao;

import app.persistence.entities.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends PagingAndSortingRepository<Project, Long>, CrudRepository<Project, Long> {
    Page<Project> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
