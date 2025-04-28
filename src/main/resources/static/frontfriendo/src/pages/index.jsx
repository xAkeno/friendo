import React from 'react'
import { useState } from 'react'
import Navbar from '../components/navbar'
import Bodyindex from '../components/bodyindex'
import '../App.css'


const index = () => {
    const navItem = ["Friendo","About","Contact","FAQ's","Sign in","Register"];
    const navItemLink = ["A","B","C","D","E"];
    const indexQuote = ["Friendo","Chat. Share. Vibe. Repeat. Friendo is where real connections","live and memories are made."];
    const[turn,setTurn] = useState(0);

    const updateTurn = (newValue) =>{
        setTurn(newValue);
    }
    console.log(turn);
    return (
        <div className="flex flex-col min-h-screen">
            <Navbar navItem={navItem} navItemLink={navItemLink} updateTurn={updateTurn}/>
            <Bodyindex quote = {indexQuote} turn={turn}/>
        </div>
    )
}

export default index
