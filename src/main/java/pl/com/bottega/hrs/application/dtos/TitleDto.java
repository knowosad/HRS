package pl.com.bottega.hrs.application.dtos;

import lombok.Getter;
import lombok.Setter;
import pl.com.bottega.hrs.model.Title;

import java.time.LocalDate;

@Getter
@Setter
public class TitleDto {

    private LocalDate fromDate, toDate;

    private String title;

    public TitleDto(Title title) {
        fromDate = title.getFromDate();
        toDate = title.getToDate();
        this.title = title.getName();
    }
}
