import { useState } from 'react';
import {BrowserRouter,Routes, Route} from 'react-router-dom';
import Index from './pages/index';
import About from './pages/about';
import Faq from './pages/faq';
import Home from './pages/home';
import Friend from './pages/friends'
import Verify from './pages/verify';
import './App.css'

function App() {
  return (
    <div className="flex flex-col min-h-screen bg-[#D4A7A7] dark:bg-gray-950 dark:text-white" id="light">
      <BrowserRouter>
        <Routes>
          <Route index element={<Index/>}/>
          <Route path='/about' element={<About/>}/>
          <Route path='/faq' element={<Faq/>}/>
          <Route path='/Home' element={<Home/>}/>
          <Route path='/Friends' element={<Friend/>}/>
          <Route path='/Verify' element={<Verify/>}/>
        </Routes>
      </BrowserRouter>
    </div>
  )
}

export default App
