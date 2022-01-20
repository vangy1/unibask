package sk.unibask.notification;

import java.util.Date;

public class NotificationDto {
    private Long id;
    private Date creationDate;
    private String title;
    private String url;
    private boolean viewed;

    public NotificationDto(Long id, Date creationDate, String title, String url, boolean viewed) {
        this.id = id;
        this.creationDate = creationDate;
        this.title = title;
        this.url = url;
        this.viewed = viewed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isViewed() {
        return viewed;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }
}
