import React, { useEffect, useState } from 'react'
import LoadedTrend from './loadedTrend'
import ButtonGroup from './buttonGroup';
import Search from './search';
import LoadedFriend from './loadedFriend';
import axios from 'axios';
const trend = () => {
  const [tre,setTre] = useState([]);
  const [choice,setChoice] = useState(0);
  const [word,setWord] = useState(0);
  const updateWord = (value) => {
      setWord(value);
  }
  const updateChoice = (value) => {
    setChoice(value);
  }
  const api = async() => {
    const url = "http://localhost:8080/api/v1/trending/trend";

    axios({
      method:'get',
      url: url,
    }).then(res => {
      if(res.status == 200){
        setTre(res.data)
      }else console.log("Something must have happen")
    })
    .catch(err => console.log(err))
  }
  useEffect(() => {api();},[])
  return (
    <div className="w-[35%] p-5 ">   
      <ButtonGroup updateChoice={updateChoice} updateWord={updateWord} word={word}/>
      <Search choice={choice}/>

      <div className="rounded-md border-gray-500 bg-gray-50 p-2 border-1 dark:bg-gray-800 dark:border-1 dark:border-gray-600 mb-7 ">
        <h1 className="text-[20px]">{word == 0 ? "Trends for you" : "Your Friends"}</h1>
          {
            choice == 0 ? tre.map((item,index) => (<LoadedTrend key={item.title} title={item.title} active={item.active} volume={item.volume} date={item.date} category={item.category} />)) : <LoadedFriend/>
          }
      </div>
    </div>
  )
}

export default trend
