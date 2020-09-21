package co.jp.api.jwt;

import co.jp.api.dao.impl.UserDao;
import co.jp.api.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserDao userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        // Kiểm tra xem user có tồn tại trong database không?
//        System.out.println(username);
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }else{
            System.out.println("found :"+user.getName());
        }
        return new CustomUserDetails(user);
    }
    public UserDetails loadUserById(Integer Id) {
        // Kiểm tra xem user có tồn tại trong database không?
        User user = userRepository.findById(Id).orElse(null);
        if (user == null) {
            System.out.println("load User fails");
        }else{
            System.out.println("user id not found user");
        }
        return new CustomUserDetails(user);
    }


}