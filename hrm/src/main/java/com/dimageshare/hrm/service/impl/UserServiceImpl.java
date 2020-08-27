package com.dimageshare.hrm.service.impl;

import com.dimageshare.hrm.dto.UserDTO;
import com.dimageshare.hrm.dto.UserDetailsDTO;
import com.dimageshare.hrm.entity.Role;
import com.dimageshare.hrm.entity.User;
import com.dimageshare.hrm.exception.UserLockException;
import com.dimageshare.hrm.mapper.BaseMapper;
import com.dimageshare.hrm.repository.UserRepository;
import com.dimageshare.hrm.service.UserService;
import com.dimageshare.hrm.util.AppUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private BaseMapper<User, UserDTO> mapper = new BaseMapper<>(User.class, UserDTO.class);

    @PostConstruct
    public void init() {
        Optional<User> user = userRepository.findById(1L);
        if (user.isPresent())
            return;
        User userInit = new User();
        userInit.setUsername("admin");
        userInit.setPassword(passwordEncoder.encode("Admin@123"));
        userInit.setEmail("admin@gmail");
        userInit.setFirstName("jon");
        userInit.setLastName("wick");
        userInit.setIsLock((short) 0);
        userInit.setIsActive((short) 1);
        userInit.setProfileImageUrl("");
        userRepository.save(userInit);
    }

    @SneakyThrows
    @Override
    public UserDetailsDTO loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            user = userRepository.findByEmail(username);
        }
        if (user == null || AppUtils.compareShort(user.getIsActive(), 0) || AppUtils.compareShort(user.getIsLock(), 1)) {
            throw new UsernameNotFoundException(username);
        }
        return mappingToUserDetailsDTO(user);
    }

    @Override
    public UserDetailsDTO findUserDetailById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return user != null ? mappingToUserDetailsDTO(user) : null;
    }

    private UserDetailsDTO mappingToUserDetailsDTO(User user) {
        UserDTO userDTO = mapper.toDtoBean(user);
        userDTO.setRoles(user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
        return new UserDetailsDTO(userDTO);
    }

    @Override
    public UserDTO getOne(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return user != null ? mapper.toDtoBean(user) : null;
    }

    public void changePassword(String password, Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null)
            return;
        user.setPassword(passwordEncoder.encode(password));
        userRepository.saveAndFlush(user);
    }

    @Override
    public void updateStatusUserLoginRedis(Long id, int status, String type) {
        User user = userRepository.getOne(id);
        redisTemplate.opsForValue().set(type + user.getUsername(), status);
    }

    @Override
    public void updateTimeLoginFail(String account) throws UserLockException {
        User user = userRepository.findByUsername(account);
        if (user == null) {
            user = userRepository.findByEmail(account);
        }
        if (user == null || AppUtils.compareShort(user.getIsActive(), 0))
            return;
        if (AppUtils.compareShort(user.getIsLock(), 1)) {
            throw new UserLockException();
        }
        Short count = (Short) redisTemplate.opsForValue().get("lock_" + user.getUsername());
        if (count == null)
            count = 0;
        if (count < 5) {
            redisTemplate.opsForValue().set("lock_" + user.getUsername(), ++count);
        } else {
            user.setIsLock((short) 1);
            userRepository.save(user);
            redisTemplate.opsForValue().set("lock_" + user.getUsername(), (short) 0);
            throw new UserLockException();
        }
    }

    public void sendMailRemindPassword(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null || AppUtils.compareShort(user.getIsLock(), 1) || AppUtils.compareShort(user.getIsActive(), 0))
            return;
        mailService.sendMailRemindPassword(email, "abc");
    }

    @Override
    public boolean isLogout(String username) {
        Integer status = (Integer) redisTemplate.opsForValue().get("block_" + username);
        return status != null && status == 1;
    }
}
