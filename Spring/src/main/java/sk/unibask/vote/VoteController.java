package sk.unibask.vote;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/votes")
public class VoteController {

    private final VoteService votingService;

    @Autowired
    public VoteController(VoteService votingService) {
        this.votingService = votingService;
    }


    @PostMapping("/entry/upvote")
    // returns vote which you will add to votes of entry
    // two exceptions - failure and cant vote on your post
    public void upvoteEntry(@RequestBody Map<String, String> body) {
        votingService.upvoteEntry(Long.valueOf(body.get("entryId")));
    }

    @PostMapping("/entry/downvote")
    public void downvoteEntry(@RequestBody Map<String, String> body) {
        votingService.downvoteEntry(Long.valueOf(body.get("entryId")));
    }

    @DeleteMapping("/entry/unvote")
    public void unvoteEntry(@RequestParam(value = "entryId") String entryId) {
        votingService.unvoteEntry(Long.valueOf(entryId));
    }

    @GetMapping("/entry")
    public void getVotesForEntry(@RequestParam(value = "entryId") String entryId) {
        votingService.getVotesForEntry(Long.valueOf(entryId));

    }

    @GetMapping("/user")
    public void getVotesForUser(@RequestParam(value = "userId") String userId) {
        votingService.getVotesForUser(Long.valueOf(userId));
    }

}
