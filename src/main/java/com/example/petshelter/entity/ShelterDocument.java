package com.example.petshelter.entity;

import com.example.petshelter.type.ShelterDocumentType;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "shelter_documents")
public class ShelterDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String filePath;
    private Long fileSize;
    @Enumerated(value = EnumType.STRING)
    private ShelterDocumentType type;
    private String mediaType;

    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;

    public ShelterDocument(Long id, String filePath, Long fileSize, ShelterDocumentType type, String mediaType, Shelter shelter) {
        this.id = id;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.type = type;
        this.mediaType = mediaType;
        this.shelter = shelter;
    }

    public ShelterDocument() {
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public ShelterDocumentType getType() {
        return type;
    }

    public void setType(ShelterDocumentType title) {
        this.type = title;
    }

    public Shelter getShelter() {
        return shelter;
    }

    public void setShelter(Shelter shelter) {
        this.shelter = shelter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShelterDocument that = (ShelterDocument) o;
        return filePath.equals(that.filePath) && Objects.equals(fileSize, that.fileSize) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filePath, fileSize, type);
    }

    @Override
    public String toString() {
        return "ShelterDocument{" +
                "id=" + id +
                ", filePath='" + filePath + '\'' +
                ", fileSize=" + fileSize +
                ", title='" + type + '\'' +
                ", shelter=" + shelter +
                '}';
    }
}
