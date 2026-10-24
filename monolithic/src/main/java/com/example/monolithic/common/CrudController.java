package com.example.monolithic.common;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

public abstract class CrudController<T extends BaseEntity> {
    private final JpaRepository<T, Long> repo;
    protected CrudController(JpaRepository<T, Long> repo) { this.repo = repo; }

    @GetMapping public List<T> all() { return repo.findAll(); }

    @GetMapping("/{id}") public T one(@PathVariable Long id) {
        return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
    }
    @PostMapping public T create(@RequestBody T e) { return repo.save(e); }

    @PutMapping("/{id}") public T update(@PathVariable Long id, @RequestBody T e) {
        e.setId(id);
        return repo.save(e);
    }
    @DeleteMapping("/{id}") public void delete(@PathVariable Long id) { repo.deleteById(id); }
}
