package ru.puzikov.universityschedule.parser;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Site {
    private String groupsUrl;
    private String scheduleUrl;

    private String groupClass;

    public String getUrlForGroup(Integer groupN) {
        return String.format("%s/%s/", scheduleUrl, groupN.toString());
    }

}
