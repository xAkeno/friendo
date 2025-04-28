import React from 'react'

const comment = (props) => {
  return (
    <div className="flex gap-3 mt-3 mb-3 items-center">
        <span>
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-circle-user-icon lucide-circle-user"><circle cx="12" cy="12" r="10"/><circle cx="12" cy="10" r="3"/><path d="M7 20.662V19a2 2 0 0 1 2-2h6a2 2 0 0 1 2 2v1.662"/></svg>
        </span>
        <div className=" bg-gray-200 dark:bg-gray-600 pt-1 pb-1 pl-2 pr-2 rounded-2xl">
            <span>
                {
                    props.name
                }
            </span>
            <h1>
                {
                    props.context
                }
            </h1>
        </div>
    </div>
  )
}

export default comment
