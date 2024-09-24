package com.practicejpa02.JPA.entity;

import java.io.Serializable;
import java.util.Objects;

public class UserBankId implements Serializable {

    private Long user;
    private Long bank;
    // @IdClass 활용을 위홰 오버라이드 해야함

    @Override
    public boolean equals(Object o)
    {
        boolean ret = false;
        if (this == o)
        {
            ret = true;
        }
        return ret;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(user,bank);
    }
}
