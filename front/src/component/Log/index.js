import React from 'react';

const Log = () => {
    return (
        <div>
            <div className="loginText">
                <input type="text" placeholder='Username' id="UsernameInput" />
                <input type="text" placeholder='Password' id="PasswordInput" />
            </div>
        </div>
    );
};

export default Log;