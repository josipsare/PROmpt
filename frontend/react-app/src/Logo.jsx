import React from 'react';
import myImage from './assets/logo.png';

const Logo = () => {
  return (
    <div className='logoContainer'> {/* Apply a CSS class for styling */}
      <img src={myImage} alt="My Image" className="logoImage" /> {/* Apply a CSS class for styling */}
    </div>
  );
};

export default Logo;
