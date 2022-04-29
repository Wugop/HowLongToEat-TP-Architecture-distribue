import React, { useEffect, useState } from 'react';
import axios from "axios";

const List = ({ dataResto, setPage }) => {

    const [waitTime, setWaitTime] = useState("-");
    const afficherPage = () => {
        setPage(dataResto);
    };

    useEffect(() => {
        axios({
            method: "GET",
            url: "http://localhost:3000/note/api/v1/noteRessources/waitingTimeNow",
            params: {
                idRestoN: dataResto.idRestaurant
            }
        }).then((res) => {
            if(res.data == -1){
                setWaitTime("Fermé");
            }else{
                setWaitTime(res.data);
            }
        }).catch(err => {
            console.log(err);
        });
    }, []);

    return (
        <div className="list-container" onClick={afficherPage}>
            <div className="infos">
                <div className="nomResto">{dataResto.name}</div>
                <div className="adresseResto">{dataResto.adress}</div>
                <div className="adresseResto">{dataResto.hourly}</div>
            </div>
            <div className="temps">
                <div className="desc">Temps d'attente :</div>
                <div className="min">{waitTime}</div>
            </div>
        </div>
    );
};

export default List;