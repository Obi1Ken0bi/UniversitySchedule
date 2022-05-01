package ru.puzikov.universityschedule;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.puzikov.universityschedule.persistance.model.Group;
import ru.puzikov.universityschedule.persistance.model.Pair;
import ru.puzikov.universityschedule.persistance.service.GroupService;

@RestController
public class PairController {
    final
    GroupService groupService;

    public PairController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping("/getPair")
    public Pair getNextPair(@RequestBody Group group) {
        return groupService.getNextPair(group);
    }
}
