package com.example.PROmpt.repository;

import com.example.PROmpt.models.Template;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemplateRepo extends JpaRepository<Template,Long> {
    List<Template> findAllByTypeAndTypeExtras(String type, String typeExtras);
}
