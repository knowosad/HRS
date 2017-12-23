package pl.com.bottega.hrs.application;

import pl.com.bottega.hrs.application.dtos.DetailedUserDto;

/**
 * Created by freszczypior on 2017-11-28.
 */
public interface UserFinder {

    UserSearchResults search(UserSearchCriteria criteria);

    DetailedUserDto getUserDetails(Integer id);
}
