package com.example.petshelter.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "user_reports_photos")
public class UserReportPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String filePath;
    private Long fileSize;
    private String mediaType;
    private String title;
    @ManyToOne
    @JoinColumn(name = "user_report_id")
    private UserReport userReport;

    public UserReportPhoto(Long id, String filePath, Long fileSize, String mediaType, String title, UserReport userReport) {
        this.id = id;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.mediaType = mediaType;
        this.title = title;
        this.userReport = userReport;
    }

    public UserReportPhoto() {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UserReport getUserReport() {
        return userReport;
    }

    public void setUserReport(UserReport userReport) {
        this.userReport = userReport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserReportPhoto that = (UserReportPhoto) o;
        return Objects.equals(filePath, that.filePath) && Objects.equals(fileSize, that.fileSize) && Objects.equals(mediaType, that.mediaType) && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filePath, fileSize, mediaType, title);
    }

    @Override
    public String toString() {
        return "UserReportPhoto{" +
                "id=" + id +
                ", filePath='" + filePath + '\'' +
                ", fileSize=" + fileSize +
                ", mediaType='" + mediaType + '\'' +
                ", title='" + title + '\'' +
                ", userReport=" + userReport +
                '}';
    }
}
