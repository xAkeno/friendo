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
  const [loading,setLoading] = useState(false)
  const [apiCount,setApiCount] = useState(false);

  const updateApiCount = (val) => {
    setApiCount(val)
  }

  const api = async() =>{
    if(loading || apiCount) return;
    const url = "http://localhost:8080/auth/api/v1/feed/friend"

    const viewedIds = feed.map((item) => item.id);
    setLoading(true)
    axios({
      method:'get',
      url: url,
      withCredentials:true,
      params:{
        viewed:viewedIds
      },
      paramsSerializer: (params) => {
        return params.viewed.map(v => `viewed=${encodeURIComponent(v)}`).join('&');
      }
    }).then(res => {
      const data = res.data;
      if (res.data.length === 0) {
        setApiCount(true); // no more to load
      } else {
        setFeed((prev) => [...prev, ...res.data]);
      }
    })
    .catch(err => console.log(err))
    .finally(setLoading(false))
  }
  useEffect(() => {
    const handleScroll = () => {
      const scrollTop = window.scrollY;
      const windowHeight = window.innerHeight;
      const fullHeight = document.documentElement.scrollHeight;

      const scrollPercentage = (scrollTop + windowHeight) / fullHeight;

      if (scrollPercentage >= 0.99) {
        setApiCount(true)
        api();
      }
    };
    window.addEventListener('scroll', handleScroll);
    return () => window.removeEventListener('scroll', handleScroll);
  },[feed, apiCount, loading])

  useEffect(() => {api();},[]);
  return (
    <div className="w-full flex flex-col justify-center items-center gap-5 mb-5 max-md:w-[100%] max-[575px]:w-[100%] max-md:px-4 max-[575px]:pl-0 max-md:pb-[50px]">
      <Spark updateShow={props.updateShow} show={props.show}/>
      {
        Array.isArray(feed) && feed.map((item,index) => (
          <LoadedFeed updateApiCount={updateApiCount} key={index} id={item.id} is_owner={item._Owner} is_save={item._Save} liker={item.like} imageMetaModels={item.imageMetaModels} context={item.context} created={item.createdAt} visibility={item.visibility} account={item.account} updateShowComment={() => {updateShowComment(index)}} index={index} showComment={showComment} comments={item.comments} option={option} updateOption={() => updateOption(index)} loadedAllLiker={item.likeFeed} profileImg={item.profileImg} profileImgUser={item.comments.profileImgUser}/>
        ))
      }
      {loading && "Loading"}
    </div>
  )
}

export default feed
