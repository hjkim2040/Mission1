package com.sc.mission1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bookmark {
    private int id;
    private String mgrNo;
    private int bookmarkGroupId;
    private LocalDateTime regDttm;
}
