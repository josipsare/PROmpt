package com.example.PROmpt.DTO;

import com.example.PROmpt.models.Template;

import java.util.Set;

public class TemplatePrompt {
    private Set<Template> templates;

    private String prompt;

    public TemplatePrompt(Set<Template> templates, String prompt) {
        this.templates = templates;
        this.prompt = prompt;
    }

    public Set<Template> getTemplates() {
        return templates;
    }

    public void setTemplates(Set<Template> templates) {
        this.templates = templates;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
}
