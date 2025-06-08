import React, { useState } from 'react'

const Counter = () => {
    const[count,setCount] = useState(0);
    return (
        <div>
            <h2 className="text-yellow-500">This is a counter : {count}</h2>
            <button onClick={() => setCount(count + 1)}>Incress</button>
            <button onClick={() => setCount(count > 0 ? count - 1 : count)}>Decress</button>
        </div>
    )
}

export default Counter;
