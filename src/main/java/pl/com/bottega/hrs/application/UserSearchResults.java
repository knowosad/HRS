package pl.com.bottega.hrs.application;

import lombok.Getter;
import lombok.Setter;
import pl.com.bottega.hrs.application.dtos.BasicUserDto;

import java.util.List;

/**
 * Created by freszczypior on 2017-11-29.
 */
@Getter
@Setter
public class UserSearchResults {

    private List<BasicUserDto> results;

    private int totalCount;

    private int pageSize;

    private int pageNumber;

    private int pagesCount;

}
