package sk.unibask.user;

import sk.unibask.user.studyprogram.StudyProgramDto;

import java.util.Date;
import java.util.List;

public class UserDto {
    private Long id;
    private String mail;
    private String username;
    private String avatar;
    private Date creationDate;
    private List<String> roles;
    private StudyProgramDto studyProgram;
    private Long reputation;

    public UserDto(Long id, String mail, String username, String avatar, Date registrationDate, List<String> roles, StudyProgramDto studyProgramDto) {
        this.id = id;
        this.mail = mail;
        this.username = username;
        this.avatar = avatar;
        this.creationDate = registrationDate;
        this.roles = roles;
        this.studyProgram = studyProgramDto;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String>

                                 roles) {
        this.roles = roles;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public StudyProgramDto getStudyProgram() {
        return studyProgram;
    }

    public void setStudyProgram(StudyProgramDto studyProgram) {
        this.studyProgram = studyProgram;
    }
}
