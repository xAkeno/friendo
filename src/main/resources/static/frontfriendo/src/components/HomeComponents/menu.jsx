import React, { useState } from 'react'
import Theme from '../theme';
import Logo from '../../assets/friendo_logo.png';
import { useNavigate } from "react-router-dom";
const menu = (props) => {
    const navigate = useNavigate()
    
    const link = ["Home","Friends"];
    const imgLink = [
        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-house-icon lucide-house"><path d="M15 21v-8a1 1 0 0 0-1-1h-4a1 1 0 0 0-1 1v8"/><path d="M3 10a2 2 0 0 1 .709-1.528l7-5.999a2 2 0 0 1 2.582 0l7 5.999A2 2 0 0 1 21 10v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"/></svg>,
        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-contact-icon lucide-contact"><path d="M16 2v2"/><path d="M7 22v-2a2 2 0 0 1 2-2h6a2 2 0 0 1 2 2v2"/><path d="M8 2v2"/><circle cx="12" cy="11" r="3"/><rect x="3" y="4" width="18" height="18" rx="2"/></svg>,
        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-book-marked-icon lucide-book-marked"><path d="M10 2v8l3-3 3 3V2"/><path d="M4 19.5v-15A2.5 2.5 0 0 1 6.5 2H19a1 1 0 0 1 1 1v18a1 1 0 0 1-1 1H6.5a1 1 0 0 1 0-5H20"/></svg>,
        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-user-icon lucide-user"><path d="M19 21v-2a4 4 0 0 0-4-4H9a4 4 0 0 0-4 4v2"/><circle cx="12" cy="7" r="4"/></svg>,
        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-circle-ellipsis-icon lucide-circle-ellipsis"><circle cx="12" cy="12" r="10"/><path d="M17 12h.01"/><path d="M12 12h.01"/><path d="M7 12h.01"/></svg>
    ];
    
  return (
        <aside className={`${props.Side ? "w-[22%]" : "w-[30%]"} h-full flex flex-col items-center justify-between max-md:fixed
            max-md:w-[100%] 
            max-md:h-auto  max-md:bottom-0 max-md:left-0 
            max-md:z-50 max-md:flex-row max-md:dark:bg-gray-800`}>
        <nav className="flex w-full max-md:relative max-md:w-full">
            <ul className="flex flex-col justify-center gap-5 w-full pr-10 pt-9 pl-10
            max-md:flex-row max-md:items-center max-md:justify-around max-md:p-2 max-md:h-[60px] max-md:pt-0 max-md:pl-0 max-md:pr-0">
                <li className=' max-md:hidden'>
                    <span><img src={Logo} alt="Friendo Logo" title="Back to home?" className='w-43 cursor-pointer' onClick={() => {navigate("/Home")}}/></span>
                </li>
                
                {link.map((item,index) => {
                    return(
                        <li key={index} onClick={() => {navigate("/" + item)}} className="max-sm:w-full max-md:w-[5%] flex text-[20px] font-medium flex items-center gap-3 w-full  hover:text-blue-500 cursor-pointer pr-2 pt-1 pl-2 pb-1 rounded-[5px]">
                            <span className='hover:bg-gray-200 p-1 rounded-xl'>{imgLink[index]}</span>
                            <span className='max-md:hidden'>{item}</span>
                        </li>                  
                    );
                })}
                <li onClick={() => {navigate("/chat")}} className="max-sm:w-full max-md:w-[5%] flex text-[20px] font-medium flex items-center gap-3 w-full  hover:text-blue-500 cursor-pointer pr-2 pt-1 pl-2 pb-1 rounded-[5px]">
                    <span className='hover:bg-gray-200 p-1 rounded-xl'><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-message-circle-more-icon lucide-message-circle-more"><path d="M7.9 20A9 9 0 1 0 4 16.1L2 22Z"/><path d="M8 12h.01"/><path d="M12 12h.01"/><path d="M16 12h.01"/></svg></span>
                    <span className='max-md:hidden'>Chat</span>
                </li>
                <li onClick={() => {navigate("/Profile/" + props.username)}} className="max-sm:w-full max-md:w-[5%] flex text-[20px] font-medium flex items-center gap-3 w-full hover:text-blue-500 cursor-pointer pr-2 pt-1 pl-2 pb-1 rounded-[5px]">
                    <span className='hover:bg-gray-200 p-1 rounded-xl'><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-user-icon lucide-user"><path d="M19 21v-2a4 4 0 0 0-4-4H9a4 4 0 0 0-4 4v2"/><circle cx="12" cy="7" r="4"/></svg></span>
                    <span className='max-md:hidden'>Profile</span>
                </li>
                <li onClick={() => {navigate("/Trend")}} className="max-sm:w-full max-md:w-[5%] flex text-[20px] font-medium flex items-center gap-3 w-full hover:text-blue-500 cursor-pointer pr-2 pt-1 pl-2 pb-1 rounded-[5px]">
                    <span className='hover:bg-gray-200 p-1 rounded-xl'>
                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-chart-column-decreasing-icon lucide-chart-column-decreasing"><path d="M13 17V9"/><path d="M18 17v-3"/><path d="M3 3v16a2 2 0 0 0 2 2h16"/><path d="M8 17V5"/></svg>
                    </span>
                    <span className='max-md:hidden'>Trending</span>
                </li>
                <li className="hidden max-lg:block max-sm:w-full max-md:w-[5%] flex text-[20px] font-medium flex items-center gap-3 w-full hover:text-blue-500 cursor-pointer pr-2 pt-1 pl-2 pb-1 rounded-[5px]">
                    <Theme/>
                </li>
                <button type="button" class="max-md:hidden text-white bg-blue-700 hover:bg-blue-800 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 cursor-pointer w-full">Spark</button>
            </ul>
        </nav>
    </aside>
  )
}

export default menu
