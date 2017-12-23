package pl.com.bottega.hrs.application;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

/**
 * Created by freszczypior on 2017-11-29.
 */
@Getter
@Setter
public class UserSearchCriteria {

    private String login;

    private Collection<String> role;

    private int pageSize = 20;

    private int pageNumber = 1;

}
