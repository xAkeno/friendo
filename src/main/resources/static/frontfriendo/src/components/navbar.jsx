import React from 'react'
import Theme from './theme';
const navbar = props => {
  return (
    <header className="flex justify-between pt-8 pr-24 pb-7 pl-24 text-lg font-sans font-medium item max-lg:pl-5 max-lg:pr-5">
        <div>
            <ul className="flex gap-12">
                {props.navItem.slice(0,4).map((item,index) => {
                    return (
                        <li key={index} className="relative font-medium after:content-[''] after:absolute after:left-0 after:-bottom-1 after:w-0 after:h-[2px] after:bg-blue-600 after:transition-all after:duration-300 hover:after:w-full hover:text-blue-600 text-[20px]  max-sm:text-[15px]"><a href={props.navItemLink[index]}>{item}</a></li>
                    );
                })}
            </ul>
        </div>
        <div>
            <ul className="flex gap-12">
    
                {props.navItem.slice(4,5).map((item, index) => {
                        return (
                        <li key={index + 2} onClick={() => {props.updateTurn(0)}} className="cursor-pointer">
                            <a className="relative font-medium after:content-[''] after:absolute after:left-0 after:-bottom-1 after:w-0 after:h-[2px] after:bg-blue-600 after:transition-all after:duration-300 hover:after:w-full hover:text-blue-600 text-[20px] max-sm:text-[15px]">{item}</a>
                        </li>
                    );
                })}
                {props.navItem.slice(5).map((item,index) => {
                    return (
                        <li key={5} >
                            <button onClick={() => {props.updateTurn(1)}} key={index + 2} className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-[20px] px-5 py-2.5 me-2 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800 transform -translate-y-2 cursor-pointer"><a>{item}</a></button>
                        </li>
                    );
                })}
                <Theme/>
            </ul>
        </div>
    </header>
  )
}

export default navbar
