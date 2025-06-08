import React, { use, useEffect, useState } from 'react'
import LoadFriends from './loadFriends'
import LoadedFriendList from './loadedFriendList'
import FindFriend from './findFriend'
import axios from 'axios'

const friend = () => {
    const[data,setData] = useState([]);
    const [data2,setData2] = useState([]);
    const api = () => {
        const url = "http://localhost:8080/api/v1/friend/viewRequest";

        // const userId = document.cookie.split('; ').find(row => row.startsWith('userId='))?.split('=')[1];

        axios({
            method:'get',
            url:url,
            withCredentials:true
        }).then(res => {
            if(res.status == 200){
                setData(res.data);
            }
        }).catch(err => console.log(err))
    }
    const api2 = () => {
        const url ="http://localhost:8080/api/v1/friend/view";

        axios({
            method:'get',
            url:url,
            withCredentials:true
            }).then(res => {
            if(res.status == 200){
                setData2(res.data)
            }
        }).catch(err => console.log(err))
    }
    useEffect(() =>{api();},[])
    useEffect(() =>{api2();},[])
  return (
    <div className="w-full mt-5">
        <FindFriend />
        <h1 className="text-2xl font-semibold mt-3 dark:text-white">Friend Request</h1>
        <div className="grid grid-cols-[repeat(auto-fill,minmax(250px,1fr))]">
            {
                data.map((item,index) => (
                    <LoadFriends key ={index} name={item.firstname + " "+ item.lastname} id={item.id}/> 
                ))
            }
        </div>
        <h1 className="text-2xl font-semibold mt-3 dark:text-white">Friend List</h1>
        <div className="grid grid-cols-[repeat(auto-fill,minmax(250px,1fr))]">
            {
                data2.map((item,index) => (
                    <LoadedFriendList key ={index} name={item.firstname + " "+ item.lastname} id={item.id}/> 
                ))
            }
        </div>
    </div>
  )
}

export default friend
