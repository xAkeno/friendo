import React, { useEffect, useState } from 'react'
import Spark from '../HomeComponents/spark'
import LoadedFeed from './loadedFeed'
import axios from 'axios'
const feed = (props) => {
  const [feed,setFeed] = useState([])
  const [showComment,setShowComment] = useState(null);
  const updateShowComment = (id) => {
    if (showComment === id) {
      setShowComment(null); // if clicking the same, hide it
    } else {
      setShowComment(id); // else show the clicked one
    }
  };

  const api = async() =>{
    const url = "http://localhost:8080/api/v1/feed/public";

    axios({
      method:'get',
      url: url
    }).then(res => {
      const data = res.data;
      setFeed(data);
    })
    .catch(err => console.log(err))
  }
  useEffect(() => {api();},[])
  return (
    <div className="w-full flex flex-col items-center gap-5 mb-5">
      <Spark updateShow={props.updateShow} show={props.show}/>
      {
        feed.map((item,index) => (
          <LoadedFeed id={item.id} context={item.context} created={item.created_At} visibility={item.visibility} account={item.account} updateShowComment={() => {updateShowComment(index)}} index={index} showComment={showComment}/>
        ))
      }
    </div>
  )
}

export default feed
