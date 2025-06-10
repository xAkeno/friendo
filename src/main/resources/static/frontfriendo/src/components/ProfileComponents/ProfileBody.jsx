import React, { useEffect, useState } from 'react'
import ProfileHeader from './ProfileHeader'
import ProfileMyDay from './ProfileMyDay'
import ProfileContentChoose from './ProfileContentChoose'
import ProfileContent from './ProfileContent'
import { useParams } from 'react-router-dom'
import axios from 'axios'
const ProfileBody = () => {
    const [choosen,setChoosen] = useState(1);
    const [data,setData] = useState([]);
    const updateChoose = (updated) => {
        setChoosen(updated);
    }
    const {username} = useParams();
    // console.log(username + "user profile")
    const loadProfile = () => {
      const url = "http://localhost:8080/auth/profile";

      axios({
        method:'get',
        url:url,
        withCredentials:true,
        params:{
          username:username
        },
        headers:{
          'Content-Type':'application/json'
        }
      }).then(res => setData(res.data))
      .catch(err => console.log(err))
    }
    // console.log(data)
    useEffect(loadProfile,username)
  return (
    <div className='w-full mt-5 flex justify-center items-center flex flex-col'>
      <div className='w-[60%] pb-10 px-10 border-b-1 flex flex-col gap-5'>
        <ProfileHeader data={data}/>
        <ProfileMyDay/>
      </div>
        <ProfileContentChoose updateChoose={updateChoose} choosen={choosen}/>
        <ProfileContent choosen={choosen} data={data}/>
    </div>
  )
}

export default ProfileBody
