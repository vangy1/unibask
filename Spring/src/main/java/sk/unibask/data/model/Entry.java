package sk.unibask.data.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

import static javax.persistence.FetchType.LAZY;

@Entity(name = "entries")
@Inheritance(strategy = InheritanceType.JOINED)
public class Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date creationDate;

    @Basic(fetch = LAZY)
    @Column(columnDefinition = "TEXT")
    private String entryText;
    @Column(columnDefinition = "TEXT")
    private String entryTextUnformatted;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Account account;

    @OneToMany(mappedBy = "entry")
    private Set<Comment> comments;

    @OneToMany(mappedBy = "entry")
    private Set<Vote> votes;

    @OneToMany(mappedBy = "entry")
    private Set<Report> reports;

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

    public String getEntryText() {
        return entryText;
    }

    public void setEntryText(String entryText) {
        this.entryText = entryText;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Vote> getVotes() {
        return votes;
    }

    public void setVotes(Set<Vote> votes) {
        this.votes = votes;
    }

    public String getEntryTextUnformatted() {
        return entryTextUnformatted;
    }

    public void setEntryTextUnformatted(String entryTextUnformatted) {
        this.entryTextUnformatted = entryTextUnformatted;
    }

    public Set<Report> getReports() {
        return reports;
    }

    public void setReports(Set<Report> reports) {
        this.reports = reports;
    }
}
