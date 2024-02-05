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
public class BookmarkGroup {
    private int id;
    private String name;
    private int orderNo;
    private LocalDateTime regDttm;
    private LocalDateTime modifyDttm;
}
