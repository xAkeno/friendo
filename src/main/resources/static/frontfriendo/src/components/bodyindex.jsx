import React from 'react'
import Login from './login'
import Register from './register'
const countries = [
    "Philippines",
    "United States",
    "Canada",
    "United Kingdom",
    "Australia",
    "Japan",
    "Germany",
    "France",
    "Italy",
    "India"];
const bodyindex = props => {
    
  return (
    <div className="flex justify-center min-h-[calc(100vh-200px)] w-full items-center">
        <div className="flex items-start flex-col justify-center gap-3">
            <h1 className="text-6xl font-bold">
                {props.quote[0]}
            </h1>
            <h1 className="text-[20px]">
                {props.quote[1]}<br/>
                {props.quote[2]}
            </h1>
        </div>
        <div className="p-4">
            <img src="https://ecomgroupteam.com/wp-content/uploads/2022/09/ecommerce.png" className="w-120"/>
        </div>
        <div className="w-[440px] flex flex-col gap-2">
            {
                props.turn != 1 ? <Login/> : <Register country={countries}/>
            }
        </div>
    </div>
  )
}

export default bodyindex
