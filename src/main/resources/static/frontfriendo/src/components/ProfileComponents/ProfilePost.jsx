import React from 'react'

const ProfilePost = (props) => {
  // props.data.feed.imageMetaModels.map((item,index) => {console.log(item)})
  return (
    <div>
      <div className="grid-cols-3 grid gap-2 [&>img]:rounded-sm">
        {
          props.data.feed.map((item,index) => (
            item.imageMetaModels.map((itemz,indez) => (<img key={indez} src={itemz.imageUrl} className="h-75 w-xs"/>))
          ))
        }
      </div>
    </div>
  )
}

export default ProfilePost
