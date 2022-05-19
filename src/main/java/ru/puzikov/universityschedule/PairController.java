package ru.puzikov.universityschedule;

import org.springframework.web.bind.annotation.*;
import ru.puzikov.universityschedule.dto.PairDto;
import ru.puzikov.universityschedule.exception.GroupNotFoundException;
import ru.puzikov.universityschedule.persistence.model.Day;
import ru.puzikov.universityschedule.persistence.model.Group;
import ru.puzikov.universityschedule.persistence.service.GroupService;
import ru.puzikov.universityschedule.persistence.service.ParserService;

@SuppressWarnings("SameReturnValue")
@RestController
public class PairController {
    final
    GroupService groupService;
    final
    ParserService parserService;

    public PairController(GroupService groupService, ParserService parserService) {
        this.groupService = groupService;
        this.parserService = parserService;
    }

    @PostMapping("/getPair")
    public PairDto getNextPair(@RequestBody Group group) {
        try {
            return groupService.getNextPair(group);
        } catch (GroupNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/getDay")
    public Day getDay(@RequestParam Integer groupId, Integer dayOfTheWeek) {
        return groupService.getPairsForDay(groupId, dayOfTheWeek);
    }

    @GetMapping("/refresh")
    public String refresh() {
        parserService.refresh();
        return "Done";
    }
}
