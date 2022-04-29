import React, { useState } from 'react';
import axios from 'axios';
import { UserContext } from '../../App';

const Log = ({ setToken, setLogger, setUsername }) => {
    const username = React.useContext(UserContext);
    const [password, setPassword] = useState("");

    const [error, setError] = useState(false);

    const handleLog = (e) => {
        e.preventDefault();
        axios({
            method: "GET",
            url: "http://localhost:3001/login",
            params:{
                username: username,
                password: password
            }
        }).then((res) => {
            console.log(res.data);
            setToken(res.data);
            setLogger(false);
        }).catch(err => {
            console.log(err);
            setError(true);
        });
    }

    return (
        <div>
            <div className="titleLog1">Se connecter</div>
            <form action="" onSubmit={handleLog} className="login-form-container">
                <input type="text" onChange={(e) => setUsername(e.target.value)} placeholder='Adresse mail' id="username" />
                <input type="text" onChange={(e) => setPassword(e.target.value)} placeholder='Mot de passe' id="username" />
                <input type="submit" value="Se connecter" id="join" />
                {error && <div className='errorText'>Nom d'utilisateur ou mot de passe incorrect</div>}
            </form>

            <div className="titleLog2">Créer un compte</div>
            <form action="" className="login-form-container">
                <input type="text" placeholder='Adresse mail' id="username" />
                <input type="text" placeholder='Nom' id="username" />
                <input type="text" placeholder='Prénom' id="username" />
                <input type="text" placeholder='Mot de passe' id="username" />
                <input type="submit" value="Créer un compte" id="join" />
            </form>
        </div>
    );
};

export default Log;