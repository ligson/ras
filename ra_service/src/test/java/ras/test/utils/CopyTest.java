package ras.test.utils;

import org.ca.ras.user.domain.UserEntity;
import org.ca.ras.user.dto.QueryUserRequestDto;
import org.springframework.beans.BeanUtils;

/**
 * Created by ligson on 2016/5/4.
 */
public class CopyTest {
    public static void main(String[] args) {
        QueryUserRequestDto requestDto = new QueryUserRequestDto();
        requestDto.setPageAble(false);
        requestDto.setPageNum(90);
        UserEntity entity = new UserEntity();
        BeanUtils.copyProperties(requestDto,entity);
        System.out.println(entity.getPageAble());
    }
}
