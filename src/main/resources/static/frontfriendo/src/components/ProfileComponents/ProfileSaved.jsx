import axios from 'axios';
import React, { useEffect, useState } from 'react'

const ProfileSaved = (props) => {
  const [show,setShow] = useState(false)
  const [post,setPost] = useState();
  const [currentIndex,setCurrentIndex] = useState(0);
  const [data,setData] = useState();
  const openModal = (index) => {
      setModalStatus(true);
      setCurrentIndex(index);
  }
  const closeModal = () => {
      setShow(false);
  }
  const nextModal = () => {
    const imgLength = post.imageMetaModels.length;
    setCurrentIndex((prev) => ((prev + 1) % imgLength));
  }
  const prevModal = () => {
    const imgLength = post.imageMetaModels.length;
    setCurrentIndex((prev) => ((prev - 1) % imgLength));
  }
  const apiComment = (event) => {
    const url = "http://localhost:8080/api/v1/comment/create";
    const data = {
        content: event.target.context.value,
        created_at: ""
    }
    axios({
        method:'post',
        url: url,
        params:{
          feedId: post.id
        },
        data: data,
        withCredentials:true,
        headers:{
          'Content-Type':'application/json'
        }
    }).then(res => {
        if(res.status == 200){
            console.log('Successfully commented')
            alert("Done")
        }
    }).catch(err => console.log(err))
  }
  console.log(props.data.username)
  const api = () => {
    const url = "http://localhost:8080/api/v1/save/allSave";
    axios({
      url:url,
      method:'get',
      withCredentials:true,
      params:{
        username:props.data.username
      },
      headers:{
        'Content-Type':'application/json'
      }
    }).then(res => {
      if(res.status == 200){
        setData(res.data)
      }
    }).catch(err => console.log(err))
  }
  useEffect(api,[])
  return (
    <div>
      <div className="grid-cols-3 grid gap-2 [&>img]:rounded-sm mb-5">
          {
            Array.isArray(data) && data.length > 0
              ? data.map((item, index) => {
                  const post = item.feed?.[0]; // safely get the first feed item
                  if (!post || !Array.isArray(post.imageMetaModels)) return null;

                  return post.imageMetaModels.slice(0, 1).map((img, i) => (
                    <img
                      key={i}
                      src={img.imageUrl}
                      onClick={() => {
                        setShow(true);
                        setPost(post); // this is the full feed object
                      }}
                      className="h-75 w-xs cursor-pointer"
                    />
                  ));
                })
              : <div className="text-gray-500 italic">No post available.</div>
          }
      </div>
      {
        show && 
        <div id="popup-modal"  className="flex bg-gray-900/75 overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 justify-center items-center w-full md:inset-0 h-[calc(100%)] max-h-full">
            <div className='w-[60%] h-[75%] flex gap-2'>
              <div className='w-full h-full relative'>
                {/* {
                  post.imageMetaModels.slice(0,1).map((itemz,indez) => (<img key={indez} src={itemz.imageUrl} onClick={() => {setShow(true);setPost(item)}}className="h-full w-full rounded-2xl"/>))
                } */}
                <img id="imgModalContent" src={post.imageMetaModels[currentIndex].imageUrl} class="w-full h-full rounded shadow-lg rounded-2xl"/>
                {/* <span>{post.imageMetaModels[currentIndex].imageUrl}</span> */}
                <div className='absolute inset-y-0 left-0 right-0 flex justify-between items-center px-4 [&>button]:cursor-pointer'>
                  <button onClick={prevModal}>
                      <span class="inline-flex items-center justify-center w-10 h-10 rounded-full bg-white/30 dark:bg-gray-800/30 group-hover:bg-white/50 dark:group-hover:bg-gray-800/60 group-focus:ring-4 group-focus:ring-white dark:group-focus:ring-gray-800/70 group-focus:outline-none">
                          <svg class="w-4 h-4 text-white dark:text-gray-800 rtl:rotate-180" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 6 10">
                              <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 1 1 5l4 4"/>
                          </svg>
                          <span class="sr-only">Previous</span>
                      </span>
                  </button>
                  <button onClick={nextModal}>
                      <span class="inline-flex items-center justify-center w-10 h-10 rounded-full bg-white/30 dark:bg-gray-800/30 group-hover:bg-white/50 dark:group-hover:bg-gray-800/60 group-focus:ring-4 group-focus:ring-white dark:group-focus:ring-gray-800/70 group-focus:outline-none">
                          <svg class="w-4 h-4 text-white dark:text-gray-800 rtl:rotate-180" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 6 10">
                              <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 9 4-4-4-4"/>
                          </svg>
                          <span class="sr-only">Next</span>
                      </span>
                  </button>
                </div>
              </div>
              <div className='w-[50%] h-full  rounded-2xl bg-[#FFFFFF] dark:bg-gray-800 flex flex-col'>
                <div className='flex items-center justify-between p-4'>
                  <div className='flex items-center gap-2'>
                    <img className="h-10 w-10 rounded-full" src={post.profileImg}/>
                    <div className='flex flex-col'>
                      <span>{post.account.username}</span>
                      <div className='flex gap-1 items-center'>
                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-clock-icon lucide-clock"><circle cx="12" cy="12" r="10"/><polyline points="12 6 12 12 16 14"/></svg>
                        <span>{post.createdAt}</span>
                      </div> 
                    </div>
                  </div>
                  <div>
                    <span onClick={() => setShow(false)} className='bg-red-50 cursor-pointer'>
                      <svg class="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
                          <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"/>
                      </svg>
                    </span>
                  </div>
                </div>
                <div className='px-5'>
                  <span>{post.context}</span>
                  <hr className='my-3'/>
                </div>
                <div className='flex-1 overflow-y-auto px-3'>
                  {
                    post.comments.map((item,index) => (
                      <div className="flex gap-3 mt-3 mb-3 items-center">
                        <span className='cursor-pointer' onClick={() => {navigate("/Profile/" + props.name)}}>
                            {
                                item.profileImgUser ? <img src={item.profileImgUser} className='h-7 w-7 rounded-full'/> : <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-circle-user-icon lucide-circle-user"><circle cx="12" cy="12" r="10"/><circle cx="12" cy="10" r="3"/><path d="M7 20.662V19a2 2 0 0 1 2-2h6a2 2 0 0 1 2 2v1.662"/></svg>
                            }
                        </span>
                        <div className=" bg-gray-200 dark:bg-gray-600 pt-1 pb-1 pl-2 pr-2 rounded-2xl">
                            <span className='cursor-pointer hover:underline' onClick={() => {navigate("/Profile/" + item.name)}}>
                                {
                                    item.account.username
                                }
                            </span>
                            <h1>
                                {
                                    item.content
                                }
                            </h1>
                        </div>
                    </div>
                    ))
                  } 
                  
                </div>
                <div className=' border-t border-gray-200 dark:border-gray-600'>
                  <form onSubmit={(e) => apiComment(e)}>
                    <label for="chat" class="sr-only">Your message</label>
                      <div class="flex items-center px-2 py-2 rounded-lg bg-gray-50 dark:bg-gray-700">
                          <button type="button" class="inline-flex justify-center  text-gray-500 rounded-lg cursor-pointer hover:text-gray-900 hover:bg-gray-100 dark:text-gray-400 dark:hover:text-white dark:hover:bg-gray-600">
                              {/* <svg class="w-5 h-5" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 18">
                                  <path fill="currentColor" d="M13 5.5a.5.5 0 1 1-1 0 .5.5 0 0 1 1 0ZM7.565 7.423 4.5 14h11.518l-2.516-3.71L11 13 7.565 7.423Z"/>
                                  <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M18 1H2a1 1 0 0 0-1 1v14a1 1 0 0 0 1 1h16a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1Z"/>
                                  <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 5.5a.5.5 0 1 1-1 0 .5.5 0 0 1 1 0ZM7.565 7.423 4.5 14h11.518l-2.516-3.71L11 13 7.565 7.423Z"/>
                              </svg> */}
                              <span class="sr-only">Upload image</span>
                          </button>
                          <textarea id="chat" rows="1" name="context" class="block mx-1 p-2.5 w-full text-sm text-gray-900 bg-white rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-800 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Your message..."></textarea>
                              <button type="submit" class="inline-flex justify-center p-2 text-blue-600 rounded-full cursor-pointer hover:bg-blue-100 dark:text-blue-500 dark:hover:bg-gray-600">
                              <svg class="w-5 h-5 rotate-90 rtl:-rotate-90" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 18 20">
                                  <path d="m17.914 18.594-8-18a1 1 0 0 0-1.828 0l-8 18a1 1 0 0 0 1.157 1.376L8 18.281V9a1 1 0 0 1 2 0v9.281l6.758 1.689a1 1 0 0 0 1.156-1.376Z"/>
                              </svg>
                              <span class="sr-only">Send message</span>
                          </button>
                      </div>
                  </form>
                </div>
              </div>
            </div>
            <div>
            </div>
        </div>
      }
    </div>
  )
}

export default ProfileSaved
