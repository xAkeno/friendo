import React, { useEffect, useState } from 'react'
import ChatLoadedContent from './ChatLoadedContent'
import axios from 'axios';
const ChatContent = (props) => {
  const messages = props.privateChats.get(props.target) || [];

  const [userImage,setUserImage] = useState({});

  useEffect(() => {
    const x = async () => {
      try{
        const url = "http://localhost:8080/auth/profile";

        const [sender,target] = await Promise.all(
          [
            axios.get(url,{withCredentials:true,params:{username:props.username},headers:{'Content-Type':'application/json'}}),
            axios.get(url,{withCredentials:true,params:{username:props.target},headers:{'Content-Type':'application/json'}})
          ]
        )
        setUserImage({
          [props.username]: sender.data.profileImg,
          [props.target]: target.data.profileImg
        })
      }catch(err){
        console.log(err)
      }
    }
    x();
  },[props.username,props.target])
  return (
    <div className="flex-1 overflow-y-auto py-5">
      {messages.map((msg, index) => (
        <ChatLoadedContent
          key={index}
          senderId={msg.senderId}
          content={msg.content}
          timestamp={msg.timestamp}
          profileImg={userImage[msg.senderId]}
        />
      ))}
      <div ref={props.scrollRef}/>
    </div>
  );
}

export default ChatContent
