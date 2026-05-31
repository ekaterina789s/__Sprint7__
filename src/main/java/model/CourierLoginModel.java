package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourierLoginModel {
    private String login;
    private String password;

    //конструктор курьера с логином и паролем
    public CourierLoginModel(String login, String password) {
        this.login = login;
        this.password = password;

    }
}
