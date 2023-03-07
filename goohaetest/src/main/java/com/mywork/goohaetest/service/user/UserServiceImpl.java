package com.kdt.goohae.service.user;


import com.kdt.goohae.domain.user.UserVO;
import com.kdt.goohae.mapper.user.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    // 필드
    private final UserMapper userMapper;

    // 생성자
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }


    // 메서드
    /**
     * 로그인 및 회원정보에 관한 메서드
     * @param vo = userVO
     * @return UserVO
     */
    @Override
    public UserVO getUserData(UserVO vo) {
        return userMapper.getUserData(vo);
    } // getUserData


    /**
     * 회원가입에 관한 메서드
     * @param vo = userVO
     * @return 성공 시 1, 실패 시 0
     */
    @Override
    public int join(UserVO vo) {
        return userMapper.join(vo);
    } // join


    /**
     * 패스워드 수정에 관한 메서드
     * @param vo = userVO
     * @return 성공 시 1, 실패 시 0
     */
    @Override
    public int changePassword(UserVO vo) {
        return userMapper.changePassword(vo);
    } // changePassword


    /**
     * 배송지 수정에 관한 메서드
     * @param vo = userVO
     * @return 성공 시 1, 실패 시 0
     */
    @Override
    public int changeAddress(UserVO vo) {
        return userMapper.changeAddress(vo);
    } // changeAddress


    /**
     * 회원 탈퇴에 관한 메서드 ( 휴면계정으로 )
     * @param vo = userVO
     * @return 성공 시 1, 실패 시 0
     */
    @Override
    public int disabledUser(UserVO vo) {
        return userMapper.disabledUser(vo);
    }
}
