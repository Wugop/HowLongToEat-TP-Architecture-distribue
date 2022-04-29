import React, { useState, useEffect } from 'react';
import { UserContext } from '../../App';
import Affluence from './Affluence';
import Note from './Note';
import axios from "axios";

const Page = ({ dataResto }) => {
    const context = React.useContext(UserContext);
    const [dataWaitTime, setDataWaitTime] = useState(null);
    const [error, setError] = useState(false);
    const [posted, setPosted] = useState("");

    useEffect(() => {
        axios({
            method: "GET",
            url: "http://localhost:3000/note/api/v1/noteRessources/waitingTime",
            params: {
                idRestoN: dataResto.idRestaurant
            }
        }).then((res) => {
            setDataWaitTime(res.data);
        }).catch(err => {
            console.log(err);
            setError(true);
        });
    }, []);

    return (
        <div className='page'>
            <div className="infosDetails">
                <div className="infosRestos">
                    <div className="nom">{dataResto.name}</div>
                    <div className="desc">{dataResto.description}</div>
                    <div className="hourly">Horaires : {dataResto.hourly}</div>
                    <div className="phone">Téléphone : {dataResto.phone}</div>
                    <div className="city">Ville : {dataResto.city}</div>
                    <div className="adress">Adresse : {dataResto.adress}</div>
                </div>
                <img src="https://www.telerama.fr/sites/tr_master/files/styles/simplecrop1000/public/fauves13_0.jpg?itok=SAX57ak4" />
            </div>
            <div className="affluence">
                <div className="titre">Affluence</div>
                {error && <div className='errorAffluence'>Une erreur est survenue, réessayez plus tard.</div>}
                {!error && <Affluence dataWaitTime={dataWaitTime}/>}
            </div>
            <div className="inputHeures">
                {context.token && !posted && <Note setPosted={setPosted} setError={setError}/>}
                {!context.token && <div className="connect">Connectez-vous pour partager votre expérience dans ce restaurant.</div>}
                {posted && <div className='connect'>{posted}</div>}
            </div>
        </div>

    );
};

export default Page;