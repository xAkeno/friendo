import React, { useEffect, useState } from 'react'
import Spark from '../HomeComponents/spark'
import LoadedFeed from './loadedFeed'
import axios from 'axios'
const feed = (props) => {
  const [feed,setFeed] = useState([])
  const [showComment,setShowComment] = useState(null);
  const [option,setOption] = useState(false);
  const userId = document.cookie.split('; ').find(row => row.startsWith('userId='))?.split('=')[1];
  const updateShowComment = (id) => {
    if (showComment === id) {
      setShowComment(null); // if clicking the same, hide it
    } else {
      setShowComment(id); // else show the clicked one
    }
  };
  // const updateOption = (index) => {
  //   setOptionState((prev) => ({
  //   ...prev,
  //   [index]: !prev[index],
  // }));
  // }
  const api = async() =>{
    const url = "http://localhost:8080/auth/api/v1/feed/friend"

    axios({
      method:'get',
      url: url,
      withCredentials:true
    }).then(res => {
      const data = res.data;
      setFeed(data);
    })
    .catch(err => console.log(err))
  }
  useEffect(() => {api();},[])
  return (
    <div className="w-full flex flex-col justify-center items-center gap-5 mb-5">
      <Spark updateShow={props.updateShow} show={props.show}/>
      {
        Array.isArray(feed) && feed.map((item,index) => (
          <LoadedFeed key={index} id={item.id} is_owner={item._Owner} is_save={item._Save} liker={item.like} imageMetaModels={item.imageMetaModels} context={item.context} created={item.createdAt} visibility={item.visibility} account={item.account} updateShowComment={() => {updateShowComment(index)}} index={index} showComment={showComment} comments={item.comments} option={option} updateOption={() => updateOption(index)} loadedAllLiker={item.likeFeed} profileImg={item.profileImg} profileImgUser={item.comments.profileImgUser}/>
        ))
      }
    </div>
  )
}

export default feed
