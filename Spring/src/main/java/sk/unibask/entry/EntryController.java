package sk.unibask.entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class EntryController {
    private final EntryService entryService;

    @Autowired
    public EntryController(EntryService entryService) {
        this.entryService = entryService;
    }

    @GetMapping("/entries")
    public Map<String, List<? extends EntryDto>> getEntries(@RequestParam("userId") String userId) {
        return entryService.getAllEntriesOfAccount(Long.parseLong(userId));
    }

    @GetMapping("/entry")
    public EntryDto getEntry(@RequestParam("id") String entryId) {
        return entryService.getEntryOfLoggedUser(Long.parseLong(entryId));
    }

    @PostMapping("/entry/edit")
    public void editEntry(@RequestBody Map<String, String> body) {
        entryService.editEntry(Long.parseLong(body.get("id")), body.get("text"), body.get("unformattedText"));
    }

}
