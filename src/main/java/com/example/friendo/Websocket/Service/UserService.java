package com.example.friendo.Websocket.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.friendo.Websocket.Model.Status;
import com.example.friendo.Websocket.Model.User;
import com.example.friendo.Websocket.Repository.UserRepository;

@Service
public class UserService {
    private UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public void saveUser(User user){
        user.setStatus(Status.ONLINE);
        userRepository.save(user);
    }
    public void disconnectUser(User user){
        var storedUser = userRepository.findById(user.getId()).orElse(null);
        if(storedUser !=null){
            user.setStatus(Status.OFFLINE);
            userRepository.save(user);
        }
    }
    public List<User> findConnectedUsers() {
        return userRepository.findAllByStatus(Status.ONLINE);
    }
}
