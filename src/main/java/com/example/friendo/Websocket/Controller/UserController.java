// package com.example.friendo.Websocket.Controller;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.messaging.handler.annotation.MessageMapping;
// import org.springframework.messaging.handler.annotation.Payload;
// import org.springframework.messaging.handler.annotation.SendTo;
// import org.springframework.stereotype.Controller;

// import com.example.friendo.Websocket.Model.User;
// import com.example.friendo.Websocket.Service.UserService;

// import lombok.RequiredArgsConstructor;

// @Controller
// @RequiredArgsConstructor
// public class UserController {
//     private UserService userService;
//     @Autowired
//     public UserController(UserService userService){
//         this.userService = userService;
//     }
//     @MessageMapping("/user.addUser")
//     @SendTo("/user/public")
//     public User addUser(@Payload User user){
//         userService.saveUser(user);
//         return user;
//     }
//     @MessageMapping("/user.disconnectUser")
//     @SendTo("/user/public")
//     public User disconnectUsers(@Payload User user){
//         userService.disconnectUser(user);
//         return user;
//     }
//     public ResponseEntity<List<User>> findConnectUsers(){
//         return ResponseEntity.ok().body(userService.findConnectedUsers());
//     }
// }
