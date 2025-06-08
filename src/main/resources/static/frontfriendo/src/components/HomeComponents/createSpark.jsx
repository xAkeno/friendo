import axios from 'axios'
import React, { useState } from 'react'
import { useRef } from 'react'

const createSpark = props => {
  const api = async(event) => {
    const url = "http://localhost:8080/auth/api/v1/feed/create"
    const targets = event.target;
    const data = {
      context: targets.context.value,
      created_at: "",
      visibility: targets.status.value
    }
    const file = fileInputRef.current.files;
    const formData = new FormData();
    const jsonBlob = new Blob([JSON.stringify(data)], { type: "application/json" });
    formData.append("body", jsonBlob);
    for (let i = 0; i < file.length; i++) {
      console.log(file[i])
      formData.append("image", file[i]);
    }

    try{
      axios.post(url,formData,{
        withCredentials:true,
      }).then(res => {
          if(res.status == 200){
            alert("Successfully created a spark")
          }
      }).catch(err => console.log(err))
    }catch(err){
      console.error(err)
    }
    
  }
  const [preview,setPreview] = useState([]);
  const fileInputRef = useRef(null);
  const maxImg = 10;
  const handleFileChange = (event) =>{
    const files = Array.from(event.target.files);

    if(files.length > maxImg){
      alert("Maximum of 10");
      fileInputRef.current.value = "";
      setPreview([])
      return;
    }

    const promises = files.slice(0,maxImg).map(file =>{
      return new Promise((resolve) =>{
        const reader = new FileReader();
        reader.onload = (event) => {
          resolve(event.target.result);
        }
        reader.readAsDataURL(file);
      });
    });
    Promise.all(promises).then(setPreview);
  }
  return (
    <div className="absolute">
        {
            props.show == 1 ? <form onSubmit={e =>api(e)} encType="multipart/form-data">
              <div className="fixed inset-0 z-50 flex justify-center items-center">
                
                <div className="absolute inset-0 bg-black opacity-60"></div>
    
                <div className="relative z-10 w-[30%] h-auto bg-blue-500 rounded-xl p-3 gap-5 flex flex-col text-white dark:bg-blue-900">
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
                      className="w-full h-full border border-white rounded-[7px] p-2 bg-transparent text-white resize-none cursor-pointer"
                    ></textarea>
                  </div>
                  <hr />
                  <div className="flex justify-around">
                    <h1>Spice up your Spark with a video or image?</h1>
                    <input onChange={(e) => handleFileChange(e)} ref={fileInputRef} id="dropzone-file" type="file" name="image[]" class="hidden" multiple />
                    <label htmlFor="dropzone-file" className='cursor-pointer flex'>
                      <svg xmlns="http://www.w3.org/2000/svg" width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-image-icon lucide-image"><rect width="18" height="18" x="3" y="3" rx="2" ry="2"/><circle cx="9" cy="9" r="2"/><path d="m21 15-3.086-3.086a2 2 0 0 0-2.828 0L6 21"/></svg>
                      <svg xmlns="http://www.w3.org/2000/svg" width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-file-video-icon lucide-file-video"><path d="M15 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V7Z"/><path d="M14 2v4a2 2 0 0 0 2 2h4"/><path d="m10 11 5 3-5 3v-6Z"/></svg>
                    </label>
                  </div>
                  <div class="flex items-center justify-center w-full">
                    {preview.map((src, index) => (
                      <div
                        key={index}
                        className="w-[100px] h-[100px] bg-cover bg-center rounded border border-gray-300"
                        style={{ backgroundImage: `url(${src})` }}
                      />
                    ))}
                      {/* <label for="dropzone-file" class="flex flex-col items-center justify-center w-full h-64 border-2 border-gray-300 border-dashed rounded-lg cursor-pointer bg-gray-50 dark:hover:bg-gray-800 dark:bg-gray-800 hover:bg-gray-100 dark:border-gray-600 dark:hover:border-gray-500 dark:hover:bg-gray-600">
                          <div class="flex justify-center items-center">
                              <div class="grid grid-cols-5 justify-center content-center pt-5 pb-6" id="dropzone-preview">
                                  {preview.length == 0 && (
                                    <div class=" col-span-5 flex flex-col items-center p-4 rounded">
                                      <svg class="w-8 h-8 mb-4 text-gray-500 dark:text-gray-400" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 16">
                                          <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 13h3a3 3 0 0 0 0-6h-.025A5.56 5.56 0 0 0 16 6.5 5.5 5.5 0 0 0 5.207 5.021C5.137 5.017 5.071 5 5 5a4 4 0 0 0 0 8h2.167M10 15V6m0 0L8 8m2-2 2 2"/>
                                      </svg>
                                      <p class="mb-2 text-sm text-gray-500 dark:text-gray-400">
                                          <span class="font-semibold">Click to upload</span> or drag and drop
                                      </p>
                                      <p class="text-xs text-gray-500 dark:text-gray-400">SVG, PNG, JPG or GIF (MAX. 800x400px)</p>
                                  </div> 
                                  )}
                                  {preview.map((src, index) => (
                                    <div
                                      key={index}
                                      className="w-[100px] h-[100px] bg-cover bg-center rounded border border-gray-300"
                                      style={{ backgroundImage: `url(${src})` }}
                                    />
                                  ))} 
                              </div>
                          </div>
                          <input onChange={(e) => handleFileChange(e)} ref={fileInputRef} id="dropzone-file" type="file" name="image[]" class="hidden" multiple />
                      </label>      */}
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
