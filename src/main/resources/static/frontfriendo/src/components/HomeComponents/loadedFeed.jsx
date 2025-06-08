import React, { useEffect, useRef, useState } from 'react'
import Comment from './comment'
import CommentInput from './commentInput';
import Sparkle from '../../assets/sparkle.png'
import Sparkles from '../../assets/sparkles.png'
import ImageStucture from './ImageStucture';
import axios from 'axios';
const loadedFeed = (props) => {
    const[commentData,setCommentData] = useState([])
    const userId = document.cookie.split('; ').find(row => row.startsWith('userId='))?.split('=')[1];
    const [option,setOption] = useState(false);
    const [liker,setLiker] = useState(false);
    let timeoutId = null;
    const timeoutRef = useRef(null);
    // console.log(commentData.map((item,index) => (console.log(item))) + "<<")

    const notlikeimg = <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-sparkle-icon lucide-sparkle"><path d="M9.937 15.5A2 2 0 0 0 8.5 14.063l-6.135-1.582a.5.5 0 0 1 0-.962L8.5 9.936A2 2 0 0 0 9.937 8.5l1.582-6.135a.5.5 0 0 1 .963 0L14.063 8.5A2 2 0 0 0 15.5 9.937l6.135 1.581a.5.5 0 0 1 0 .964L15.5 14.063a2 2 0 0 0-1.437 1.437l-1.582 6.135a.5.5 0 0 1-.963 0z"/></svg>;
    const likeimg = <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-sparkles-icon lucide-sparkles"><path d="M9.937 15.5A2 2 0 0 0 8.5 14.063l-6.135-1.582a.5.5 0 0 1 0-.962L8.5 9.936A2 2 0 0 0 9.937 8.5l1.582-6.135a.5.5 0 0 1 .963 0L14.063 8.5A2 2 0 0 0 15.5 9.937l6.135 1.581a.5.5 0 0 1 0 .964L15.5 14.063a2 2 0 0 0-1.437 1.437l-1.582 6.135a.5.5 0 0 1-.963 0z"/><path d="M20 3v4"/><path d="M22 5h-4"/><path d="M4 17v2"/><path d="M5 18H3"/></svg>;
    const api = () => {
        setCommentData(props.comments)
        // const url = "http://localhost:8080/api/v1/comment/viewComment";

        // axios({
        //     method:'get',
        //     url:url,
        //     params:{
        //         target:props.id
        //     }
        // }).then(res => {
        //     // console.log(JSON.stringify(res.data) + "<<<<<<")
        //     setCommentData(res.data);
        // }).catch(err => console.log(err))
    }
    const like = () => {
        const url = "http://localhost:8080/api/v1/like/add";
        
        axios({
            method:'post',
            url: url,
            withCredentials:true,
            params:{
                target:props.id
            }
        }).then(res => {
            if(res.status == 200){

                alert("Successfully liked")
            }
        }).catch(err => console.log(err))
        // location.reload();
    }

    useEffect(() => {api();},[])
  return (
    <div className="w-[100%] flex flex-col items-center pr-5 pl-5">
      <div className="w-[75%] bg-[#ffffff] pt-2 pl-4 pb-2 pr-4 flex flex-col gap-3 rounded-md border-gray-500 border-1 dark:focus:ring-gray-700 dark:bg-gray-800  dark:border-gray-600   dark:text-white">
        <div className="flex justify-between">
            <div className="flex gap-3">
                <span>
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-circle-user-icon lucide-circle-user"><circle cx="12" cy="12" r="10"/><circle cx="12" cy="10" r="3"/><path d="M7 20.662V19a2 2 0 0 1 2-2h6a2 2 0 0 1 2 2v1.662"/></svg>
                </span>
                <h1><b>{props.account.username}</b></h1>
            </div>
            <div>
                <span className="flex gap-2 rounded-full hover:bg-gray-500 p-0.5 cursor-pointer" onClick={() => (setOption(!option))}>
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-ellipsis-icon lucide-ellipsis"><circle cx="12" cy="12" r="1"/><circle cx="19" cy="12" r="1"/><circle cx="5" cy="12" r="1"/></svg>
                </span>
                {
                    option && <div class="absolute z-10 inline-block w-54 text-sm text-gray-900 duration-300 bg-white border border-gray-200 rounded-lg shadow-xs dark:text-gray-400 dark:border-gray-600 dark:bg-gray-800 [&>button]:cursor-pointer">
                        <button type="button" class="relative inline-flex items-center w-full px-2 py-2 text-sm font-medium border-b border-gray-200 rounded-t-lg hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-2 focus:ring-blue-700 focus:text-blue-700 dark:border-gray-600 dark:hover:bg-gray-600 dark:hover:text-white dark:focus:ring-gray-500 dark:focus:text-white">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-pencil-icon lucide-pencil"><path d="M21.174 6.812a1 1 0 0 0-3.986-3.987L3.842 16.174a2 2 0 0 0-.5.83l-1.321 4.352a.5.5 0 0 0 .623.622l4.353-1.32a2 2 0 0 0 .83-.497z"/><path d="m15 5 4 4"/></svg>
                            <span className='ml-1'>Edit</span>
                        </button>
                        <button type="button" class="relative inline-flex items-center w-full px-2 py-2 text-sm font-medium border-b border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-2 focus:ring-blue-700 focus:text-blue-700 dark:border-gray-600 dark:hover:bg-gray-600 dark:hover:text-white dark:focus:ring-gray-500 dark:focus:text-white">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-trash2-icon lucide-trash-2"><path d="M3 6h18"/><path d="M19 6v14c0 1-1 2-2 2H7c-1 0-2-1-2-2V6"/><path d="M8 6V4c0-1 1-2 2-2h4c1 0 2 1 2 2v2"/><line x1="10" x2="10" y1="11" y2="17"/><line x1="14" x2="14" y1="11" y2="17"/></svg>
                            <span className='ml-1'>Delete</span>
                        </button>
                        <button type="button" class="relative inline-flex items-center w-full px-2 py-2 text-sm font-medium border-b border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-2 focus:ring-blue-700 focus:text-blue-700 dark:border-gray-600 dark:hover:bg-gray-600 dark:hover:text-white dark:focus:ring-gray-500 dark:focus:text-white">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-bookmark-icon lucide-bookmark"><path d="m19 21-7-4-7 4V5a2 2 0 0 1 2-2h10a2 2 0 0 1 2 2v16z"/></svg>
                            <span className='ml-1'>Saved</span>
                        </button>
                        <button type="button" class="relative inline-flex items-center w-full px-2 py-2 text-sm font-medium border-b border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-2 focus:ring-blue-700 focus:text-blue-700 dark:border-gray-600 dark:hover:bg-gray-600 dark:hover:text-white dark:focus:ring-gray-500 dark:focus:text-white">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-message-square-warning-icon lucide-message-square-warning"><path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"/><path d="M12 7v2"/><path d="M12 13h.01"/></svg>
                            <span className='ml-1'>Report</span>
                        </button>
                    </div>
                }
            </div>
        </div>
        <div >
            <h1>
                {props.context}
            </h1>
            <ImageStucture data={props.imageMetaModels}/>
        </div>
        <div className="flex justify-around">
            <span className="relative cursor-pointer flex gap-1" onClick={like} onMouseEnter={() => {clearTimeout(timeoutRef.current);setLiker(true);}} onMouseLeave={() => {timeoutRef.current = setTimeout(() => setLiker(false), 300);}} >
                {
                    liker && <div class="absolute max-h-45 overflow-y-auto bottom-full left-1/2 -translate-x-1/2 mb-2 z-50 inline-block w-54 text-sm text-gray-900 duration-300 bg-white border border-gray-200 rounded-lg shadow-xs dark:text-gray-400 dark:border-gray-600 dark:bg-gray-800 [&>button]:cursor-pointer">
                        <a type="button" class="relative inline-flex items-center w-full px-2 py-2 text-sm font-medium border-b border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-2 focus:ring-blue-700 focus:text-blue-700 dark:border-gray-600 dark:hover:bg-gray-600 dark:hover:text-white dark:focus:ring-gray-500 dark:focus:text-white">
                            <img className="w-7 h-7 rounded-full" src="https://upload.wikimedia.org/wikipedia/commons/thumb/5/5c/Kanye_West_at_the_2009_Tribeca_Film_Festival_%28crop_2%29.jpg/250px-Kanye_West_at_the_2009_Tribeca_Film_Festival_%28crop_2%29.jpg"/>
                            <span className='ml-1'>Clark Kent Raguhos</span>
                        </a>
                        <a type="button" class="relative inline-flex items-center w-full px-2 py-2 text-sm font-medium border-b border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-2 focus:ring-blue-700 focus:text-blue-700 dark:border-gray-600 dark:hover:bg-gray-600 dark:hover:text-white dark:focus:ring-gray-500 dark:focus:text-white">
                            <img className="w-7 h-7 rounded-full" src="https://upload.wikimedia.org/wikipedia/commons/thumb/5/5c/Kanye_West_at_the_2009_Tribeca_Film_Festival_%28crop_2%29.jpg/250px-Kanye_West_at_the_2009_Tribeca_Film_Festival_%28crop_2%29.jpg"/>
                            <span className='ml-1'>Clark Kent Raguhos</span>
                        </a>
                        <a type="button" class="relative inline-flex items-center w-full px-2 py-2 text-sm font-medium border-b border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-2 focus:ring-blue-700 focus:text-blue-700 dark:border-gray-600 dark:hover:bg-gray-600 dark:hover:text-white dark:focus:ring-gray-500 dark:focus:text-white">
                            <img className="w-7 h-7 rounded-full" src="https://upload.wikimedia.org/wikipedia/commons/thumb/5/5c/Kanye_West_at_the_2009_Tribeca_Film_Festival_%28crop_2%29.jpg/250px-Kanye_West_at_the_2009_Tribeca_Film_Festival_%28crop_2%29.jpg"/>
                            <span className='ml-1'>Clark Kent Raguhos</span>
                        </a>
                    </div>
                }
                {
                    props.liker ? likeimg : notlikeimg
                }
                <span>0</span>
            </span>
            <span className="cursor-pointer flex gap-1" onClick={() => props.updateShowComment(props.index)}>
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-message-circle-icon lucide-message-circle"><path d="M7.9 20A9 9 0 1 0 4 16.1L2 22Z"/></svg>
                0
            </span>
        </div>
        {
            props.showComment == props.index ? <div>
                <hr />
                {
                    commentData.map((item,index) => (<Comment name={item.account.username} context={item.content}/>)) 
                }
                <CommentInput id={props.id}/>
            </div>  : null
        }
      </div>
    </div>
  )
}

export default loadedFeed
