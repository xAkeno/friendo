import axios from 'axios';
import React, { useEffect } from 'react'

const loadFriends = (props) => {
    const accept = () => {
        const url = "http://localhost:8080/api/v1/friend/accept";
        axios({
            method:'post',
            url: url,
            withCredentials:true,
            params:{
                send: props.id
            }
        }).then(res => {
            if(res.status == 200){
                alert("Successfull accepted :>")
            }
        }).catch(err => console.log(err))
        location.reload();
    }
    const reject = () => {
        const url = "http://localhost:8080/api/v1/friend/reject";
        axios({
            method:'post',
            url: url,
            withCredentials:true,
            params:{
                send: props.id
            }
        }).then(res => {
            if(res.status == 200){
                alert("Successfull rejected :<")
            }
        }).catch(err => console.log(err))
        location.reload();
    }
  return (
    <div className="w-[250px] h-[400px] p-2 border-1 rounded-[10px] mt-3 mb-3">
        <img src="https://images.squarespace-cdn.com/content/v1/639baf89d3544a2a17afcd6f/d59585eb-a7c6-46e1-8d50-72ba5aec90d8/0x0.jpg" className="h-[250px]"/>
        <div className="flex flex-col">
            <h1 className="mt-1 mb-1">{props.name}</h1>
            <button type="button" onClick={accept} class="cursor-pointer w-full text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-100 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:bg-gray-800 dark:text-white dark:border-gray-600 dark:hover:bg-gray-700 dark:hover:border-gray-600 dark:focus:ring-gray-700">Accept</button>
            <button type="button" onClick={reject} class="cursor-pointer w-full text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-100 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:bg-gray-800 dark:text-white dark:border-gray-600 dark:hover:bg-gray-700 dark:hover:border-gray-600 dark:focus:ring-gray-700">Reject</button>
        </div>
    </div>
  )
}

export default loadFriends
