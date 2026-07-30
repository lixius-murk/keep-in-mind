package com.example.keep_in_mind.models;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


//idk, i feel like thing is too much
public class DTO {

    static public class ProjectRequestDTO implements Serializable {

        private final String start_date;
        private final String end_date;
        private final String description;
        private final String state;
        private final Long folder_id;

        public ProjectRequestDTO() {
            this(null, null, null, null, null);
        }

        public ProjectRequestDTO(String start_date, String end_date, String description,
                                 String state, Long folder_id) {
            this.start_date = start_date;
            this.end_date = end_date;
            this.description = description;
            this.state = state;
            this.folder_id = folder_id;
        }

        public String getStartDate() {
            return start_date;
        }

        public String getEndDate() {
            return end_date;
        }

        public String getDescription() {
            return description;
        }

        public String getState() {
            return state;
        }

        public Long getFolderId() {
            return folder_id;
        }

        @Override
        public String toString() {
            return "ProjectRequestDTO{" +
                    "start_date=" + start_date  +
                    ", end_date=" + end_date +
                    ", description=" + description +
                    ", state=" + state +
                    ", folder_id=" + folder_id +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ProjectRequestDTO)) return false;
            ProjectRequestDTO that = (ProjectRequestDTO) o;
            return Objects.equals(start_date, that.start_date) &&
                    Objects.equals(end_date, that.end_date) &&
                    Objects.equals(description, that.description) &&
                    Objects.equals(state, that.state) &&
                    Objects.equals(folder_id, that.folder_id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(start_date, end_date, description, state, folder_id);
        }
    }

    static public class ProjectResponseDTO implements Serializable {

        private final Long id;
        private final String start_date;
        private final String end_date;
        private final String description;
        private final String state;
        private final Long folder_id;

        public ProjectResponseDTO() {
            this(null, null, null, null, null, null);
        }

        public ProjectResponseDTO(Long id, String start_date, String end_date,
                                  String description, String state, Long folder_id) {
            this.id = id;
            this.start_date = start_date;
            this.end_date = end_date;
            this.description = description;
            this.state = state;
            this.folder_id = folder_id;
        }

        public Long getId() {
            return id;
        }

        public String getStartDate() {
            return start_date;
        }

        public String getEndDate() {
            return end_date;
        }

        public String getDescription() {
            return description;
        }

        public String getState() {
            return state;
        }

        public Long getFolderId() {
            return folder_id;
        }

        @Override
        public String toString() {
            return "ProjectResponseDTO{" +
                    "id=" + id +
                    ", start_date=" + start_date +
                    ", end_date=" + end_date  +
                    ", description=" + description  +
                    ", state=" + state +
                    ", folder_id=" + folder_id +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ProjectResponseDTO)) return false;
            ProjectResponseDTO that = (ProjectResponseDTO) o;
            return Objects.equals(id, that.id) &&
                    Objects.equals(start_date, that.start_date) &&
                    Objects.equals(end_date, that.end_date) &&
                    Objects.equals(description, that.description) &&
                    Objects.equals(state, that.state) &&
                    Objects.equals(folder_id, that.folder_id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, start_date, end_date, description, state, folder_id);
        }
    }

    static public class ProjectExtraRequestDTO implements Serializable {

        private final Long project_id;
        private final String type;
        private final String content;

        public ProjectExtraRequestDTO() {
            this(null, null, null);
        }


        public ProjectExtraRequestDTO(Long project_id, String type, String content) {
            if (project_id == null) {
                throw new IllegalArgumentException("project_id must not be null");
            }
            this.project_id = project_id;
            this.type = type;
            this.content = content;
        }

        public Long getProjectId() {
            return project_id;
        }

        public String getType() {
            return type;
        }

        public String getContent() {
            return content;
        }

        @Override
        public String toString() {
            return "ProjectExtraRequestDTO{" +
                    "project_id=" + project_id +
                    ", type=" + type  +
                    ", content=" + content +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ProjectExtraRequestDTO)) return false;
            ProjectExtraRequestDTO that = (ProjectExtraRequestDTO) o;
            return Objects.equals(project_id, that.project_id) &&
                    Objects.equals(type, that.type) &&
                    Objects.equals(content, that.content);
        }

        @Override
        public int hashCode() {
            return Objects.hash(project_id, type, content);
        }
    }

    static public class ProjectExtraResponseDTO implements Serializable {

        private final Long id;
        private final Long project_id;
        private final String type;
        private final String content;

        public ProjectExtraResponseDTO() {
            this(null, null, null, null);
        }

        public ProjectExtraResponseDTO(Long id, Long project_id, String type, String content) {
            this.id = id;
            this.project_id = project_id;
            this.type = type;
            this.content = content;
        }

        public Long getId() {
            return id;
        }

        public Long getProjectId() {
            return project_id;
        }

        public String getType() {
            return type;
        }

        public String getContent() {
            return content;
        }

        @Override
        public String toString() {
            return "ProjectExtraResponseDTO{" +
                    "id=" + id +
                    ", project_id=" + project_id +
                    ", type=" + type  +
                    ", content=" + content  +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ProjectExtraResponseDTO)) return false;
            ProjectExtraResponseDTO that = (ProjectExtraResponseDTO) o;
            return Objects.equals(id, that.id) &&
                    Objects.equals(project_id, that.project_id) &&
                    Objects.equals(type, that.type) &&
                    Objects.equals(content, that.content);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, project_id, type, content);
        }
    }

    static public class ProjectWithExtrasResponseDTO implements Serializable {

        private final ProjectResponseDTO project;
        private final List<ProjectExtraResponseDTO> extras;

        public ProjectWithExtrasResponseDTO() {
            this(null, Collections.emptyList());
        }

        public ProjectWithExtrasResponseDTO(ProjectResponseDTO project, List<ProjectExtraResponseDTO> extras) {
            this.project = project;
            this.extras = extras == null ? Collections.emptyList() : Collections.unmodifiableList(extras);
        }

        public ProjectResponseDTO getProject() {
            return project;
        }

        public List<ProjectExtraResponseDTO> getExtras() {
            return extras;
        }

        @Override
        public String toString() {
            return "ProjectWithExtrasResponseDTO{project=" + project + ", extras=" + extras + '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ProjectWithExtrasResponseDTO)) return false;
            ProjectWithExtrasResponseDTO that = (ProjectWithExtrasResponseDTO) o;
            return Objects.equals(project, that.project) && Objects.equals(extras, that.extras);
        }

        @Override
        public int hashCode() {
            return Objects.hash(project, extras);
        }
    }
    static public class FolderRequestDTO implements Serializable {

        private final String name;

        public FolderRequestDTO() {
            this(null);
        }

        public FolderRequestDTO(String name) {
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("name must not be blank");
            }
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "FolderRequestDTO{name=" + name + "}";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof FolderRequestDTO)) return false;
            FolderRequestDTO that = (FolderRequestDTO) o;
            return Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    static public class FolderResponseDTO implements Serializable {

        private final Long id;
        private final String name;

        public FolderResponseDTO() {
            this(null, null);
        }

        public FolderResponseDTO(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "FolderResponseDTO{id=" + id + ", name=" + name + "}";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof FolderResponseDTO)) return false;
            FolderResponseDTO that = (FolderResponseDTO) o;
            return Objects.equals(id, that.id) && Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name);
        }
    }
}
