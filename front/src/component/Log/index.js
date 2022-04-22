import React, { useState } from 'react';
import axios from 'axios';
import { UserContext } from '../../App';

const Log = ({setToken, setLogger, setUsername}) => {
    const username = React.useContext(UserContext);
    const [password, setPassword] = useState("");

    const [error, setError] = useState(false);

    const handleLog = (e) => {
        e.preventDefault();
        axios({
            method:"GET",
            url:"http://localhost:3001/login",
            params:{
                username: username,
                password: password
            }
        }).then((res)=>{
            setToken(res.data.token);
            setLogger(false);
        }).catch(err => {
            console.log(err);
            setError(true);
        })
    }

    return (
        <form action="" onSubmit={handleLog} className="login-form-container">
            <input type="text" onChange={(e)=>setUsername(e.target.value)} placeholder='Username' id="username" />
            <input type="text" onChange={(e)=>setPassword(e.target.value)} placeholder='Password' id="username" />
            <input type="submit" value="Se connecter" id="join" />
            {error && <div className='errorText'>Nom d'utilisateur ou mot de passe incorrect</div>}
        </form>
    );
};

export default Log;