package ru.puzikov.universityschedule.persistence.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.puzikov.universityschedule.persistence.model.Group;
import ru.puzikov.universityschedule.persistence.model.Schedule;
import ru.puzikov.universityschedule.persistence.repo.GroupRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {
    @Mock
    private GroupRepository groupRepository;

    @Mock
    Schedule schedule;

    @InjectMocks
    private GroupServiceImpl groupService;

    private Group group;

    @BeforeEach
    public void setup(){
        group=Group.builder()
                .id(1L)
                .number(20391)
                .schedule(schedule)
                .build();
    }

    @Test
    @DisplayName("Test for save method")
    void groupSaveTest() {
        given(groupRepository.findByNumber(group.getNumber()))
                .willReturn(Optional.empty());
        given(groupRepository.save(group)).willReturn(group);

        Group savedGroup = groupService.saveOrGet(group);

        assertNotNull(savedGroup);
    }
}