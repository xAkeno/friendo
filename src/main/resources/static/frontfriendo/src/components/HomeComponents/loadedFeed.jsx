import React, { useEffect, useState } from 'react'
import Comment from './comment'
import CommentInput from './commentInput';
import Sparkle from '../../assets/sparkle.png'
import Sparkles from '../../assets/sparkles.png'
import axios from 'axios';
const loadedFeed = (props) => {
    const[commentData,setCommentData] = useState([])
    const userId = document.cookie.split('; ').find(row => row.startsWith('userId='))?.split('=')[1];

    const notlikeimg = <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-sparkle-icon lucide-sparkle"><path d="M9.937 15.5A2 2 0 0 0 8.5 14.063l-6.135-1.582a.5.5 0 0 1 0-.962L8.5 9.936A2 2 0 0 0 9.937 8.5l1.582-6.135a.5.5 0 0 1 .963 0L14.063 8.5A2 2 0 0 0 15.5 9.937l6.135 1.581a.5.5 0 0 1 0 .964L15.5 14.063a2 2 0 0 0-1.437 1.437l-1.582 6.135a.5.5 0 0 1-.963 0z"/></svg>;
    const likeimg = <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-sparkles-icon lucide-sparkles"><path d="M9.937 15.5A2 2 0 0 0 8.5 14.063l-6.135-1.582a.5.5 0 0 1 0-.962L8.5 9.936A2 2 0 0 0 9.937 8.5l1.582-6.135a.5.5 0 0 1 .963 0L14.063 8.5A2 2 0 0 0 15.5 9.937l6.135 1.581a.5.5 0 0 1 0 .964L15.5 14.063a2 2 0 0 0-1.437 1.437l-1.582 6.135a.5.5 0 0 1-.963 0z"/><path d="M20 3v4"/><path d="M22 5h-4"/><path d="M4 17v2"/><path d="M5 18H3"/></svg>;
    const api = () => {
        const url = "http://localhost:8080/api/v1/comment/viewComment";

        axios({
            method:'get',
            url:url,
            params:{
                target:props.id
            }
        }).then(res => {
            setCommentData(res.data);
        }).catch(err => console.log(err))
    }
    const like = () => {
        const url = "http://localhost:8080/api/v1/like/add";

        axios({
            method:'post',
            url: url,
            params:{
                userid:userId,
                target:props.id
            }
        }).then(res => {
            if(res.status == 200){
                alert("Successfully liked")
            }
        }).catch(err => console.log(err))
        location.reload();
    }
    useEffect(() => {api();},[])
    
  return (
    <div className="w-[100%] flex flex-col items-center pr-5 pl-5">
      <div className="w-[75%] bg-[#ffffff] pt-2 pl-4 pb-2 pr-4 flex flex-col gap-3 rounded-2xl border-1 dark:focus:ring-gray-700 dark:bg-gray-800  dark:border-gray-600  dark:hover:bg-gray-700 dark:text-white">
        <div className="flex justify-between">
            <div className="flex gap-3">
                <span>
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-circle-user-icon lucide-circle-user"><circle cx="12" cy="12" r="10"/><circle cx="12" cy="10" r="3"/><path d="M7 20.662V19a2 2 0 0 1 2-2h6a2 2 0 0 1 2 2v1.662"/></svg>
                </span>
                <h1><b>{props.account.username}</b></h1>
            </div>
            <div>
                <span className="flex gap-2">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-triangle-alert-icon lucide-triangle-alert"><path d="m21.73 18-8-14a2 2 0 0 0-3.48 0l-8 14A2 2 0 0 0 4 21h16a2 2 0 0 0 1.73-3"/><path d="M12 9v4"/><path d="M12 17h.01"/></svg>
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-eye-off-icon lucide-eye-off"><path d="M10.733 5.076a10.744 10.744 0 0 1 11.205 6.575 1 1 0 0 1 0 .696 10.747 10.747 0 0 1-1.444 2.49"/><path d="M14.084 14.158a3 3 0 0 1-4.242-4.242"/><path d="M17.479 17.499a10.75 10.75 0 0 1-15.417-5.151 1 1 0 0 1 0-.696 10.75 10.75 0 0 1 4.446-5.143"/><path d="m2 2 20 20"/></svg>
                </span>
            </div>
        </div>
        <div >
            <h1>
                {props.context}
            </h1>
        </div>
        <div className="flex justify-around">
            <span className="cursor-pointer flex gap-1" onClick={like} >
                {
                    props.liker ? likeimg : notlikeimg
                }
            </span>
            <span className="cursor-pointer" onClick={() => props.updateShowComment(props.index)}>
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-message-circle-icon lucide-message-circle"><path d="M7.9 20A9 9 0 1 0 4 16.1L2 22Z"/></svg>
            </span>
            <span className="cursor-pointer">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-share-icon lucide-share"><path d="M4 12v8a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2v-8"/><polyline points="16 6 12 2 8 6"/><line x1="12" x2="12" y1="2" y2="15"/></svg>
            </span>
        </div>
        {
            props.showComment == props.index ? <div>
                <hr />
                {
                    commentData.map((item,index) => (<Comment name={item.userid} context={item.content}/>)) 
                }
                <CommentInput id={props.id}/>
            </div>  : null
        }
      </div>
    </div>
  )
}

export default loadedFeed
