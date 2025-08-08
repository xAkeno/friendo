import React, { useEffect, useRef, useState } from 'react';

import ChatHead from './ChatHead';
import ChatContent from './ChatContent';
import ChatInput from './ChatInput';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import axios from 'axios';

//vibe code this part of the code ts make my brain rot so yeah kindy finish i guess but i get the process just my brain is so cook at this part of the code but the backend is good
const ChatBody = (props) => {
  console.log("Target is:", props.target, "User is:", props.username);

  const [privateChats, setPrivateChats] = useState(new Map());
  const [message, setMessage] = useState('');
  const [lastMessageKey, setLastMessageKey] = useState(null);

  const stompClientRef = useRef(null);

  const updateMessage = (val) => {
    setMessage(val);
    sendMessage(val);
  };

  const connect = () => {
    const client = new Client({
        webSocketFactory: () => {
      const sock = new SockJS('http://localhost:8080/ws', null, {
        transports: ['websocket', 'xhr-streaming', 'xhr-polling'],
        withCredentials: true // ✅ very important!
      });
      return sock;
    },

  reconnectDelay: 5000,
    onConnect: () => {
      console.log("✅ Connected");

      // ✅ Correct Spring destination, `user/queue/messages`
      client.subscribe('/user/queue/messages', onPrivateMessage);

      // ✅ Only publish after subscribing
      client.publish({
        destination: '/app/user.addUser',
        body: '',
      });
    },
    onStompError: (frame) => {
      console.error('STOMP error', frame);
    }
    });

    client.activate();
    stompClientRef.current = client;
  };


  const onPrivateMessage = (payload) => {
  const payloadData = JSON.parse(payload.body);
  const senderId = payloadData.senderId;

  console.log("📩 Private Message Received:", payloadData);

  setPrivateChats(prev => {
    const updated = new Map(prev);
    const chatList = updated.get(senderId) || [];

    chatList.push(payloadData);
    updated.set(senderId, chatList);

    // ✅ Update last message key to trigger scroll
    setLastMessageKey(`${senderId}-${chatList.length}`);

    return updated;
  });

  };


  const sendMessage = (val) => {
    if (!stompClientRef.current || !stompClientRef.current.connected) return;

    const chatMes = {
      senderId: props.username,
      recipientId: props.target,
      content: val,
      timestamp: new Date()
    };

    stompClientRef.current.publish({
      destination: "/app/chat",
      body: JSON.stringify(chatMes)
    });

    setMessage('');
  };

  // Fetch previous messages after connection
  useEffect(() => {
    let intervalId;

    const fetchMessages = () => {
      if (props.username && props.target && stompClientRef.current?.connected) {
        const url = `http://localhost:8080/messages/${props.username}/${props.target}`;
        axios.get(url, { withCredentials: true }).then(res => {
        setPrivateChats(prev => {
          const updated = new Map(prev);
          const oldMessages = updated.get(props.target) || [];
          const newMessages = res.data;

          // Only update if theres a new message
          if (newMessages.length !== oldMessages.length) {
            updated.set(props.target, newMessages);
            setLastMessageKey(`${props.target}-${newMessages.length}`);
          }

          return updated;
        });
      }).catch(err => {
          console.error("Failed to fetch messages:", err);
        });
      }
    };

    // Initial fetch
    fetchMessages();

    // Set up polling every 5 seconds
    intervalId = setInterval(fetchMessages, 3000);

    return () => {
      clearInterval(intervalId);
    };
  }, [props.username, props.target]);


  // Connect on mount
  useEffect(() => {
    if (props.username) {
      connect();
    }

    return () => {
      if (stompClientRef.current?.connected) {
        stompClientRef.current.deactivate();
      }
    };
  }, [props.username]); 

  const scrollRef = useRef(null);

  useEffect(() => {
    if (scrollRef.current) {
      scrollRef.current.scrollIntoView({ behavior: 'smooth' });
    }
  }, [lastMessageKey]);
  //isWideScreen
  return (
    <div className="w-[55%] max-[767px]:w-[98%] dark:bg-gray-800 flex flex-col h-full p-3 rounded-md mt-2 max-[767px]:mt-0">
      <ChatHead target={props.target} />
      <ChatContent privateChats={privateChats} username={props.username} target={props.target} scrollRef={scrollRef}/>
      <ChatInput updateMessage={updateMessage} />
    </div>
  );
};

export default ChatBody;
