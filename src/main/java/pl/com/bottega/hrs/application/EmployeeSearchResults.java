package pl.com.bottega.hrs.application;

import lombok.Getter;
import lombok.Setter;
import pl.com.bottega.hrs.application.dtos.BasicEmployeeDto;

import java.util.List;

@Getter
@Setter
public class EmployeeSearchResults {

    private List<BasicEmployeeDto> results;

    private int totalCount;

    private int pageSize;

    private int pageNumber;
    private int pagesCount;
}
