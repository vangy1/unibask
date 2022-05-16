package sk.unibask.profile;

public class UserInfoDto {
    private Long studyProgramId;
    private Boolean mailNotificationsEnabled;

    public UserInfoDto(Long studyProgramId, Boolean mailNotificationsEnabled) {
        this.studyProgramId = studyProgramId;
        this.mailNotificationsEnabled = mailNotificationsEnabled;
    }

    public Long getStudyProgramId() {
        return studyProgramId;
    }

    public void setStudyProgramId(Long studyProgramId) {
        this.studyProgramId = studyProgramId;
    }

    public Boolean getMailNotificationsEnabled() {
        return mailNotificationsEnabled;
    }

    public void setMailNotificationsEnabled(Boolean mailNotificationsEnabled) {
        this.mailNotificationsEnabled = mailNotificationsEnabled;
    }
}
