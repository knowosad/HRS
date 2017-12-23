package pl.com.bottega.hrs.application.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by freszczypior on 2017-11-28.
 */
@Getter
@Setter
public class BasicUserDto {

    private Integer id;

    private String login;

    public BasicUserDto(Integer id, String login) {
        this.id = id;
        this.login = login;
    }
}
