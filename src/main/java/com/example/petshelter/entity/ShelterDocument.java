package com.example.petshelter.entity;

import jakarta.persistence.*;

import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "shelter_documents")
public class ShelterDocument {

    @Id
    @GeneratedValue
    private Long id;
    private String filePath;
    private Long fileSize;
    private String mediaType;
    private byte[] data;
    private String title;

    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;

    public ShelterDocument(Long id, String filePath, Long fileSize, String mediaType, byte[] data, String title, Shelter shelter) {
        this.id = id;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.mediaType = mediaType;
        this.data = data;
        this.title = title;
        this.shelter = shelter;
    }

    public ShelterDocument() {
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

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
        return Objects.equals(id, that.id) && Objects.equals(filePath, that.filePath) && Objects.equals(fileSize, that.fileSize) && Objects.equals(mediaType, that.mediaType) && Arrays.equals(data, that.data) && Objects.equals(title, that.title) && Objects.equals(shelter, that.shelter);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, filePath, fileSize, mediaType, title, shelter);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }

    @Override
    public String toString() {
        return "ShelterDocument{" +
                "id=" + id +
                ", filePath='" + filePath + '\'' +
                ", fileSize=" + fileSize +
                ", mediaType='" + mediaType + '\'' +
                ", data=" + Arrays.toString(data) +
                ", title='" + title + '\'' +
                ", shelter=" + shelter +
                '}';
    }
}
