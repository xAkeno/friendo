import React, { useEffect, useRef, useState } from 'react'
import Comment from './comment'
import CommentInput from './commentInput';
import Sparkle from '../../assets/sparkle.png'
import Sparkles from '../../assets/sparkles.png'
import ImageStucture from './ImageStucture';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
const loadedFeed = (props) => {
    const[commentData,setCommentData] = useState([])
    const userId = document.cookie.split('; ').find(row => row.startsWith('userId='))?.split('=')[1];
    const [option,setOption] = useState(false);
    const [liker,setLiker] = useState(false);
    const [onDel,setOnDel] = useState(false);
    const [saveStat,setSaveStat] = useState("")
    let timeoutId = null;
    const timeoutRef = useRef(null);
    // console.log(commentData.map((item,index) => (console.log(item))) + "<<")
    // console.log(props.loadedAllLiker)
    const notlikeimg = <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" className="lucide lucide-sparkle-icon lucide-sparkle"><path d="M9.937 15.5A2 2 0 0 0 8.5 14.063l-6.135-1.582a.5.5 0 0 1 0-.962L8.5 9.936A2 2 0 0 0 9.937 8.5l1.582-6.135a.5.5 0 0 1 .963 0L14.063 8.5A2 2 0 0 0 15.5 9.937l6.135 1.581a.5.5 0 0 1 0 .964L15.5 14.063a2 2 0 0 0-1.437 1.437l-1.582 6.135a.5.5 0 0 1-.963 0z"/></svg>;
    const likeimg = <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" className="lucide lucide-sparkles-icon lucide-sparkles"><path d="M9.937 15.5A2 2 0 0 0 8.5 14.063l-6.135-1.582a.5.5 0 0 1 0-.962L8.5 9.936A2 2 0 0 0 9.937 8.5l1.582-6.135a.5.5 0 0 1 .963 0L14.063 8.5A2 2 0 0 0 15.5 9.937l6.135 1.581a.5.5 0 0 1 0 .964L15.5 14.063a2 2 0 0 0-1.437 1.437l-1.582 6.135a.5.5 0 0 1-.963 0z"/><path d="M20 3v4"/><path d="M22 5h-4"/><path d="M4 17v2"/><path d="M5 18H3"/></svg>;
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
        var url = "";
        var method = "post";
        if(!props.liker){
            url = "http://localhost:8080/api/v1/like/add";
        }else{
            url = "http://localhost:8080/api/v1/like/unlike";
            method = "delete"
        }
        axios({
            method:method,
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
        location.reload();
    }
    const deletePost = () => {
        const url = "http://localhost:8080/auth/api/v1/feed/delete";
        axios({
            url:url,
            method:"delete",
            withCredentials:true,
            params:{
                feedid:props.id
            }
        }).then(res => {
            if(res.status == 200){
                alert("Successfully delete post")
            }
            if(res.status == 202){
                alert("Failed to delete post")
            }
        }).catch(err => console.log(err))
        console.log(props.id)
    }
    const save = () => {
        const url = "http://localhost:8080/api/v1/save/add";
        axios({
            url:url,
            method:"post",
            withCredentials:true,
            params:{
                feedId:props.id
            }
        }).then(res => {
            if(res.status == 200){
                alert("Successfully saved post")
                location.reload();
            }
            if(res.status == 403){
                alert("Failed to save post")
            }
        }).catch(err => console.log(err))
    }
    const unsave = () => {
        const url = "http://localhost:8080/api/v1/save/unsave";
        axios({
            url:url,
            method:"delete",
            withCredentials:true,
            params:{
                feedId:props.id
            }
        }).then(res => {
            if(res.status == 200){
                alert("Successfully unsaved post")
                location.reload();
            }
            if(res.status == 403){
                alert("Failed to unsave post")
            }
        }).catch(err => console.log(err))
    }
    const navigate = useNavigate()
    useEffect(() => {api();},[])
  return (
    <div className="w-[100%] flex flex-col items-center pr-5 pl-5" key={props.id}>
        {
            onDel && <div id="popup-modal" tabindex="-1" className=" flex bg-gray-900/70 overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 justify-center items-center w-full md:inset-0 h-[calc(100%)] max-h-full">
                        <div className="relative p-4 w-full max-w-md max-h-full">
                            <div className="relative bg-white rounded-lg shadow-sm dark:bg-gray-700">
                                <button type="button" onClick={() => setOnDel(false)} className="cursor-pointer absolute top-3 end-2.5 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center dark:hover:bg-gray-600 dark:hover:text-white" data-modal-hide="popup-modal">
                                    <svg className="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
                                        <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"/>
                                    </svg>
                                    <span className="sr-only">Close modal</span>
                                </button>
                                <div className="p-4 md:p-5 text-center">
                                    <svg className="mx-auto mb-4 text-gray-400 w-12 h-12 dark:text-gray-200" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
                                        <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 11V6m0 8h.01M19 10a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z"/>
                                    </svg>
                                    <h3 className="mb-5 text-lg font-normal text-gray-500 dark:text-gray-400">Are you sure you want to delete this post?</h3>
                                    <button data-modal-hide="popup-modal" onClick={deletePost} type="button" className="cursor-pointer text-white bg-red-600 hover:bg-red-800 focus:ring-4 focus:outline-none focus:ring-red-300 dark:focus:ring-red-800 font-medium rounded-lg text-sm inline-flex items-center px-5 py-2.5 text-center">
                                        Yes, I'm sure
                                    </button>
                                    <button data-modal-hide="popup-modal" type="button" onClick={() => setOnDel(false)} className="cursor-pointer py-2.5 px-5 ms-3 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-4 focus:ring-gray-100 dark:focus:ring-gray-700 dark:bg-gray-800 dark:text-gray-400 dark:border-gray-600 dark:hover:text-white dark:hover:bg-gray-700">No, cancel</button>
                                </div>
                            </div>
                        </div>
                    </div>
        }
      <div className="w-[75%] bg-gray-100  pt-1 pl-4 pb-2 pr-4 flex flex-col gap-3 rounded-md shadow-sm border-gray-300 border-1 dark:focus:ring-gray-700 dark:bg-gray-800  dark:border-gray-600   dark:text-white">
        <div className="flex justify-between items-center">
            <div className="flex gap-3 items-center">
                {/* <span>
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" className="lucide lucide-circle-user-icon lucide-circle-user"><circle cx="12" cy="12" r="10"/><circle cx="12" cy="10" r="3"/><path d="M7 20.662V19a2 2 0 0 1 2-2h6a2 2 0 0 1 2 2v1.662"/></svg>
                </span> */}
                <span className='cursor-pointer' onClick={() => {navigate("/Profile/" + props.account.username)}}>
                    {
                        props.profileImg ? <img src={props.profileImg} className='h-7 w-7 rounded-full'/> : <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" className="lucide lucide-circle-user-icon lucide-circle-user"><circle cx="12" cy="12" r="10"/><circle cx="12" cy="10" r="3"/><path d="M7 20.662V19a2 2 0 0 1 2-2h6a2 2 0 0 1 2 2v1.662"/></svg>
                    }
                </span>
                <div className='flex flex-col'>
                    <h1 className='cursor-pointer hover:underline' onClick={() => {navigate("/Profile/" + props.account.username)}}><b>{props.account.username}</b></h1>
                    <span>{props.created}</span>
                </div>
            </div>
            <div>
                <span className="flex gap-2 rounded-full  hover:bg-gray-200 dark:hover:bg-gray-500 p-0.5 cursor-pointer" onClick={() => (setOption(!option))}>
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" className="lucide lucide-ellipsis-icon lucide-ellipsis"><circle cx="12" cy="12" r="1"/><circle cx="19" cy="12" r="1"/><circle cx="5" cy="12" r="1"/></svg>
                </span>
                {
                    option && <div className="absolute z-10 inline-block w-54 text-sm text-gray-900 duration-300 bg-white border border-gray-200 rounded-lg shadow-xs dark:text-gray-400 dark:border-gray-600 dark:bg-gray-800 [&>button]:cursor-pointer">
                        <button type="button" onClick={() => (setOption(!option))} className="relative inline-flex items-center w-full px-2 py-2 text-sm font-medium border-b border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-2 focus:ring-blue-700 focus:text-blue-700 dark:border-gray-600 dark:hover:bg-gray-600 dark:hover:text-white dark:focus:ring-gray-500 dark:focus:text-white">
                            <svg className="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
                                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"/>
                            </svg>
                            <span className='ml-1'>Close</span>
                        </button>
                        <button type="button" className="relative inline-flex items-center w-full px-2 py-2 text-sm font-medium border-b border-gray-200 rounded-t-lg hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-2 focus:ring-blue-700 focus:text-blue-700 dark:border-gray-600 dark:hover:bg-gray-600 dark:hover:text-white dark:focus:ring-gray-500 dark:focus:text-white">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" className="lucide lucide-pencil-icon lucide-pencil"><path d="M21.174 6.812a1 1 0 0 0-3.986-3.987L3.842 16.174a2 2 0 0 0-.5.83l-1.321 4.352a.5.5 0 0 0 .623.622l4.353-1.32a2 2 0 0 0 .83-.497z"/><path d="m15 5 4 4"/></svg>
                            <span className='ml-1'>Edit</span>
                        </button>
                        <button type="button" onClick={() => setOnDel(true)} className="relative inline-flex items-center w-full px-2 py-2 text-sm font-medium border-b border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-2 focus:ring-blue-700 focus:text-blue-700 dark:border-gray-600 dark:hover:bg-gray-600 dark:hover:text-white dark:focus:ring-gray-500 dark:focus:text-white">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" className="lucide lucide-trash2-icon lucide-trash-2"><path d="M3 6h18"/><path d="M19 6v14c0 1-1 2-2 2H7c-1 0-2-1-2-2V6"/><path d="M8 6V4c0-1 1-2 2-2h4c1 0 2 1 2 2v2"/><line x1="10" x2="10" y1="11" y2="17"/><line x1="14" x2="14" y1="11" y2="17"/></svg>
                            <span className='ml-1'>Delete</span>
                        </button>
                        {
                            props.is_save ? (
                                <button
                                type="button"
                                onClick={unsave}
                                className="group relative inline-flex items-center w-full px-2 py-2 text-sm font-medium border-b border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-2 focus:ring-blue-700 focus:text-blue-700 dark:border-gray-600 dark:hover:bg-gray-600 dark:hover:text-white dark:focus:ring-gray-500 dark:focus:text-white"
                                >
                                <svg xmlns="http://www.w3.org/2000/svg"width="16"height="16"viewBox="0 0 24 24"fill="none"stroke="currentColor"strokeWidth="2"strokeLinecap="round"strokeLinejoin="round"className="lucide lucide-bookmark-icon lucide-bookmark">
                                    <path d="m19 21-7-4-7 4V5a2 2 0 0 1 2-2h10a2 2 0 0 1 2 2v16z" />
                                </svg>

                                {/* Change text on hover */}
                                <span className="ml-1 block group-hover:hidden">Saved</span>
                                <span className="ml-1 hidden group-hover:block">Unsave?</span>
                                </button>
                            ) : (
                                <button
                                type="button"
                                onClick={save}
                                className="relative inline-flex items-center w-full px-2 py-2 text-sm font-medium border-b border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-2 focus:ring-blue-700 focus:text-blue-700 dark:border-gray-600 dark:hover:bg-gray-600 dark:hover:text-white dark:focus:ring-gray-500 dark:focus:text-white"
                                >
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16"viewBox="0 0 24 24"fill="none"stroke="currentColor"strokeWidth="2"strokeLinecap="round"strokeLinejoin="round"className="lucide lucide-bookmark-icon lucide-bookmark">
                                    <path d="m19 21-7-4-7 4V5a2 2 0 0 1 2-2h10a2 2 0 0 1 2 2v16z" />
                                </svg>
                                <span className="ml-1">Save</span>
                                </button>
                            )
                            }
                        <button type="button" className="relative inline-flex items-center w-full px-2 py-2 text-sm font-medium border-b border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-2 focus:ring-blue-700 focus:text-blue-700 dark:border-gray-600 dark:hover:bg-gray-600 dark:hover:text-white dark:focus:ring-gray-500 dark:focus:text-white">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" className="lucide lucide-message-square-warning-icon lucide-message-square-warning"><path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"/><path d="M12 7v2"/><path d="M12 13h.01"/></svg>
                            <span className='ml-1'>Report</span>
                        </button>
                        
                    </div>
                }
            </div>
        </div>
        <div>
            <h1 className='mb-2'>
                {props.context}
            </h1>
            <ImageStucture data={props.imageMetaModels}/>
        </div>
        <div className="flex justify-around">
            <span className="relative cursor-pointer flex gap-1" onClick={like} onMouseEnter={() => {clearTimeout(timeoutRef.current);setLiker(true);}} onMouseLeave={() => {timeoutRef.current = setTimeout(() => setLiker(false), 300);}} >
                {
                    liker && <div className="absolute max-h-45 overflow-y-auto bottom-full left-1/2 -translate-x-1/2 mb-2 z-50 inline-block w-54 text-sm text-gray-900 duration-300 bg-white border border-gray-200 rounded-lg shadow-xs dark:text-gray-400 dark:border-gray-600 dark:bg-gray-800 [&>button]:cursor-pointer">
                        {
                            props.loadedAllLiker.map((item,index) => (
                                <a type="button" className="relative inline-flex items-center w-full px-2 py-2 text-sm font-medium border-b border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-2 focus:ring-blue-700 focus:text-blue-700 dark:border-gray-600 dark:hover:bg-gray-600 dark:hover:text-white dark:focus:ring-gray-500 dark:focus:text-white">
                                    <img className="w-7 h-7 rounded-full" src="https://upload.wikimedia.org/wikipedia/commons/thumb/5/5c/Kanye_West_at_the_2009_Tribeca_Film_Festival_%28crop_2%29.jpg/250px-Kanye_West_at_the_2009_Tribeca_Film_Festival_%28crop_2%29.jpg"/>
                                    <span className='ml-1' onClick={() => {navigate("/Profile/" + props.account.username)}}>{item.account.firstname +" "+ item.account.lastname}</span>
                                </a>
                            ))
                        }
                    </div>
                }
                {
                    props.liker ? likeimg : notlikeimg
                }
                <span>{props.loadedAllLiker.length}</span>
            </span>
            <span className="cursor-pointer flex gap-1" onClick={() => props.updateShowComment(props.index)}>
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" className="lucide lucide-message-circle-icon lucide-message-circle"><path d="M7.9 20A9 9 0 1 0 4 16.1L2 22Z"/></svg>
                {props.comments.length}
            </span>
        </div>
        {
            props.showComment == props.index ? <div>
                <hr />
                {
                    commentData.map((item,index) => (<Comment name={item.account.username} context={item.content} profileImgUser={item.profileImgUser}/>)) 
                }
                <CommentInput id={props.id}/>
            </div>  : null
        }
      </div>
    </div>
  )
}

export default loadedFeed
