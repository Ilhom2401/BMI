package com.example.buildings.service;

import com.example.buildings.dto.ProjectDto;
import com.example.buildings.entity.Project;
import com.example.buildings.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public List<Project> getList() {
        return projectRepository.findAll();
    }

    public boolean save(ProjectDto projectDto) {
        Project project = projectRepository.getByNameAndAddress(projectDto.getName(), projectDto.getAddress());
        if (project != null)
            return false;
        Project project1 = new Project();
        project1.setActive(true);
        project1.setAddress(projectDto.getAddress());
        project1.setName(projectDto.getName());
        projectRepository.save(project1);
        return true;
    }

    public Project edit(ProjectDto projectDto){
        Optional<Project> optionalProject = projectRepository.findById(projectDto.getProjectId());
        if (optionalProject.isEmpty())
            return null;
        return optionalProject.get();
    }

    public boolean edited(ProjectDto projectDto){
        Optional<Project> optionalProject = projectRepository.findById(projectDto.getProjectId());
        if (optionalProject.isEmpty())
            return false;
        Project project = optionalProject.get();
        project.setName(projectDto.getName());
        project.setAddress(projectDto.getAddress());
        projectRepository.save(project);
        return true;
    }

    public Project getById(Long id){
        Optional<Project> byId = projectRepository.findById(id);
        if (byId.isEmpty())
            return null;
        return byId.get();
    }

}
