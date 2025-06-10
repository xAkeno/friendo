import React, { useState } from 'react'

const ProfilePost = (props) => {
  // props.data.feed.imageMetaModels.map((item,index) => {console.log(item)}) 
  // item.imageMetaModels.map((itemz,indez) => (<img key={indez} src={itemz.imageUrl} className="h-75 w-xs"/>))
  const [show,setShow] = useState(false)
  const [post,setPost] = useState();
  const [currentIndex,setCurrentIndex] = useState(0);

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
  return (
    <div>
      <div className="grid-cols-3 grid gap-2 [&>img]:rounded-sm mb-5">
        {
          props.data.feed.map((item,index) => (
            item.imageMetaModels.slice(0,1).map((itemz,indez) => (<img key={indez} src={itemz.imageUrl} onClick={() => {setShow(true);setPost(item)}}className="h-75 w-xs cursor-pointer"/>))
          ))
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
                <img id="imgModalContent" src={post.imageMetaModels[currentIndex].imageUrl} class="w-full h-full rounded shadow-lg"/>
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
              <div className='w-[50%] h-full  rounded-2xl bg-amber-100'>
                  <span onClick={() => setShow(false)}>Closes</span>
              </div>
            </div>
        </div>
      }
    </div>
  )
}

export default ProfilePost
