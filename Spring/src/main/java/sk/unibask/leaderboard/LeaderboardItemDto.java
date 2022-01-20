package sk.unibask.leaderboard;

public class LeaderboardItemDto {
    private Long userId;
    private Long reputation;
    private String profilePicture;
    private String name;

    public LeaderboardItemDto(Long userId, Long reputation, String profilePicture, String name) {
        this.userId = userId;
        this.reputation = reputation;
        this.profilePicture = profilePicture;
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getReputation() {
        return reputation;
    }

    public void setReputation(Long reputation) {
        this.reputation = reputation;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
