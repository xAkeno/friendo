import axios from 'axios'
import React from 'react'

const createSpark = props => {
  const api = async(event) => {
    const url = "http://localhost:8080/auth/api/v1/feed/create"
    const targets = event.target;
    const data = {
      context: targets.context.value,
      created_at: "",
      visibility: targets.status.value
    }
    axios({
      method:"post",
      url:url,
      withCredentials:true,
      data:data,
      headers:{
        'Content-Type':'application/json'
      }
    }).then(res => {
      if(res.status == 200){
        console.log(res)
      }
      console.log(res.config.url)
    }).catch(err => console.log(err))
    
  }
  return (
    <div className="absolute">
        {
            props.show == 1 ? <form onSubmit={e =>api(e)}>
              <div className="fixed inset-0 z-50 flex justify-center items-center">
                
                <div className="absolute inset-0 bg-black opacity-60"></div>
    
                <div className="relative z-10 w-[30%] h-[40%] bg-blue-500 rounded-2xl p-3 gap-5 flex flex-col text-white dark:bg-blue-950">
                  <div className="flex justify-between items-center">
                    <span></span>
                    <h1 className="text-2xl font-bold">Create a spark</h1>
                    <span onClick={() => props.updateShow(0)} className="cursor-pointer">
                      <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24"
                        viewBox="0 0 24 24" fill="none" stroke="currentColor"
                        strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"
                        className="lucide lucide-x-icon lucide-x">
                        <path d="M18 6 6 18"/><path d="m6 6 12 12"/>
                      </svg>
                    </span>
                  </div>
                  <hr />
                  <div className="flex justify-between">
                    <div className="flex gap-1">
                      <span>
                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-circle-user-icon lucide-circle-user"><circle cx="12" cy="12" r="10"/><circle cx="12" cy="10" r="3"/><path d="M7 20.662V19a2 2 0 0 1 2-2h6a2 2 0 0 1 2 2v1.662"/></svg>
                      </span>
                      <h1>Clark Kent</h1>
                    </div>
                    <div>
                      <select name="status" className="cursor-pointer p-0.5 text-sm text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-4 focus:ring-gray-100 dark:focus:ring-gray-700 dark:bg-gray-800 dark:text-gray-400 dark:border-gray-600 dark:hover:text-white dark:hover:bg-gray-700">
                        <option value="PUBLIC">Public only</option>
                        <option value="FRIEND_ONLY">Friend only</option>
                      </select>
                    </div>
                  </div>
                  <div className="h-[40%]">
                    <textarea
                      name="context"
                      placeholder="What is on your mind?"
                      className="w-full h-full border border-white rounded-[7px] p-2 bg-transparent text-white resize-none"
                    ></textarea>
                  </div>
                  <hr />
                  <div className="flex justify-around">
                    <h1>Add this on your spark?</h1>
                    <span className="flex gap-2">
                      <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-image-icon lucide-image"><rect width="18" height="18" x="3" y="3" rx="2" ry="2"/><circle cx="9" cy="9" r="2"/><path d="m21 15-3.086-3.086a2 2 0 0 0-2.828 0L6 21"/></svg>
                      <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-file-video-icon lucide-file-video"><path d="M15 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V7Z"/><path d="M14 2v4a2 2 0 0 0 2 2h4"/><path d="m10 11 5 3-5 3v-6Z"/></svg>
                    </span>
                  </div>
                  <button type="submit" class="cursor-pointer py-2.5 px-5 me-2 mb-2 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-4 focus:ring-gray-100 dark:focus:ring-gray-700 dark:bg-gray-800 dark:text-gray-400 dark:border-gray-600 dark:hover:text-white dark:hover:bg-gray-700">Create a Spark</button>
                </div>
              </div>
            </form> : null
        }
    </div>
  )
}

export default createSpark
