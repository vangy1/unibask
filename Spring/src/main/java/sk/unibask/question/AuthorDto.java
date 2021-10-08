package sk.unibask.question;

public class AuthorDto {
    private String id;
    private String username;
    private String avatar;
    private String reputation;

    public AuthorDto(String id, String username, String avatar, String reputation) {
        this.id = id;
        this.username = username;
        this.avatar = avatar;
        this.reputation = reputation;
    }

    public AuthorDto(Long id, String username, String avatar, Long reputation) {
        this.id = String.valueOf(id);
        this.username = username;
        this.avatar = avatar;
        this.reputation = String.valueOf(reputation);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getReputation() {
        return reputation;
    }

    public void setReputation(String reputation) {
        this.reputation = reputation;
    }
}
