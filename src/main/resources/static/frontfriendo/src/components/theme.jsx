import React from 'react'

const theme = () => {
    const [dark, setDark] = React.useState(false);

    const darkModeHandler = () => {
        setDark(!dark);
        document.documentElement.classList.toggle("dark");
    }
  return (
    <div>    
        <button type="button" onClick={darkModeHandler} class="cursor-pointer hs-dark-mode-active:hidden block hs-dark-mode font-medium text-gray-800 rounded-full bg-gray-200 hover:bg-gray-300 focus:outline-hidden  dark:text-neutral-200 dark:bg-neutral-800 dark:hover:bg-neutral-700 " data-hs-theme-click-value="dark">
            <span class="group inline-flex shrink-0 justify-center items-center size-9">
                {
                    !dark ? <svg class="shrink-0 size-4" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <path d="M12 3a6 6 0 0 0 9 9 9 9 0 1 1-9-9Z"></path>
                        </svg>:
                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-sun-icon lucide-sun"><circle cx="12" cy="12" r="4"/><path d="M12 2v2"/><path d="M12 20v2"/><path d="m4.93 4.93 1.41 1.41"/><path d="m17.66 17.66 1.41 1.41"/><path d="M2 12h2"/><path d="M20 12h2"/><path d="m6.34 17.66-1.41 1.41"/><path d="m19.07 4.93-1.41 1.41"/></svg>
                }
            </span>
        </button>
   </div>
  )
}

export default theme
